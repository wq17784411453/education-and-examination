package com.onlinetest.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.onlinetest.dao.TeacherDao;
import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Teacher;


public class TeacherDaoImpl extends HibernateDaoSupport implements TeacherDao{

	@Override
	public Teacher getTeacherById(Teacher teacher) {
		String hql= "from Teacher t where t.teacherId=?";
		List list = this.getHibernateTemplate().find(hql, teacher.getTeacherId());
		if(list!=null && list.size()>0){
			return (Teacher) list.get(0);
		}
		return null;
	}

	@Override
	public Teacher updateTeacher(Teacher updateTeacher) {
		Teacher newTeacher = null;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			newTeacher = (Teacher) this.getHibernateTemplate().merge(updateTeacher);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newTeacher;
	}

	@Override
	public boolean addTeacher(Teacher teacher) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().save(teacher);
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
	public PageBean<Teacher> findTeacherByPage(int pageCode, int pageSize) {
		PageBean<Teacher> pb = new PageBean<Teacher>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		List teacherList = null;
		try {
			String sql = "SELECT count(*) FROM Teacher";
			List list = this.getSession().createQuery(sql).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			//不支持limit分页
			String hql= "from Teacher";
			//分页查询
			teacherList = doSplitPage(hql,pageCode,pageSize);
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		if(teacherList!=null && teacherList.size()>0){
			pb.setBeanList(teacherList);
			return pb;
		}
		return null;
	}

	@Override
	public PageBean<Teacher> queryTeacher(Teacher teacher, int pageCode,
			int pageSize) {
		PageBean<Teacher> pb = new PageBean<Teacher>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb_sql = new StringBuilder();
		String sql = "SELECT count(*) FROM Teacher t where 1=1 ";
		String hql= "from Teacher t where 1=1 ";
		sb.append(hql);
		sb_sql.append(sql);
		if(!"".equals(teacher.getTeacherId().trim())){
			sb.append(" and t.teacherId like '%" + teacher.getTeacherId() +"%'");
			sb_sql.append(" and t.teacherId like '%" + teacher.getTeacherId() +"%'");
		}
		if(!"".equals(teacher.getTeacherName().trim())){
			sb.append(" and t.teacherName like '%" + teacher.getTeacherName() +"%'");
			sb_sql.append(" and t.teacherName  like '%" + teacher.getTeacherName() +"%'");
		}
		try{
			
			List list = this.getSession().createQuery(sb_sql.toString()).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			
			List<Teacher> teacherList = doSplitPage(sb.toString(),pageCode,pageSize);
			if(teacherList!=null && teacherList.size()>0){
				pb.setBeanList(teacherList);
				return pb;
			}
		}catch (Throwable e1){
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return null;
	}

	@Override
	public boolean deleteTeacher(Teacher teacher) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().delete(teacher);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public Teacher updateTeacherInfo(Teacher teacher) {
		Teacher newTeacher = null;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			newTeacher = (Teacher) this.getHibernateTemplate().merge(teacher);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newTeacher;
	}

	
}
