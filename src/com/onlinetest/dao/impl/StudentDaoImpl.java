package com.onlinetest.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.onlinetest.dao.StudentDao;
import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Student;
import com.onlinetest.service.StudentService;


public class StudentDaoImpl extends HibernateDaoSupport implements StudentDao{

	@Override
	public Student getStudentById(Student student) {
		String hql= "from Student s where s.studentId=?";
		List list = this.getHibernateTemplate().find(hql, student.getStudentId());
		if(list!=null && list.size()>0){
			return (Student) list.get(0);
		}
		return null;
	}

	@Override
	public Student updateStudent(Student updateStudent) {
		Student newStudent = null;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			newStudent = (Student) this.getHibernateTemplate().merge(updateStudent);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newStudent;
	}

	@Override
	public boolean addStudent(Student student) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().save(student);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	/**
     * 
     * @param hql传入的hql语句
     * @param pageCode当前�?
     * @param pageSize每页显示大小
     * @return
     */
    public List doSplitPage(final String hql,final int pageCode,final int pageSize){
        //调用模板的execute方法，参数是实现了HibernateCallback接口的匿名类�?
        return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            //重写其doInHibernate方法返回�?个object对象�?
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                //创建query对象
                Query query=session.createQuery(hql);
                //返回其执行了分布方法的list
                return query.setFirstResult((pageCode-1)*pageSize).setMaxResults(pageSize).list();
                 
            }
             
        });
         
    }

	
	
	@Override
	public PageBean<Student> findStudentByPage(int pageCode, int pageSize) {
		PageBean<Student> pb = new PageBean<Student>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		List studentList = null;
		try {
			String sql = "SELECT count(*) FROM Student";
			List list = this.getSession().createQuery(sql).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			//不支持limit分页
			String hql= "from Student";
			//分页查询
			studentList = doSplitPage(hql,pageCode,pageSize);
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		if(studentList!=null && studentList.size()>0){
			pb.setBeanList(studentList);
			return pb;
		}
		return null;
	}

	@Override
	public PageBean<Student> queryStudent(Student student, int pageCode,
			int pageSize) {
		PageBean<Student> pb = new PageBean<Student>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb_sql = new StringBuilder();
		String sql = "SELECT count(*) FROM Student s where 1=1 ";
		String hql= "from Student s where 1=1 ";
		sb.append(hql);
		sb_sql.append(sql);
		if(!"".equals(student.getStudentId().trim())){
			sb.append(" and s.studentId like '%" + student.getStudentId() +"%'");
			sb_sql.append(" and s.studentId like '%" + student.getStudentId() +"%'");
		}
		if(!"".equals(student.getStudentName().trim())){
			sb.append(" and s.studentName like '%" + student.getStudentName() +"%'");
			sb_sql.append(" and s.studentName  like '%" + student.getStudentName() +"%'");
		}
		try{
			
			List list = this.getSession().createQuery(sb_sql.toString()).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			
			List<Student> studentList = doSplitPage(sb.toString(),pageCode,pageSize);
			if(studentList!=null && studentList.size()>0){
				pb.setBeanList(studentList);
				return pb;
			}
		}catch (Throwable e1){
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return null;
	}

	@Override
	public boolean deleteStudent(Student student) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().delete(student);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public Student updateStudentInfo(Student student) {
		Student newStudent = null;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			newStudent = (Student) this.getHibernateTemplate().merge(student);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newStudent;
	}

}
