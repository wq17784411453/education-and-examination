package com.onlinetest.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.onlinetest.dao.SubjectDao;
import com.onlinetest.domain.Choice;
import com.onlinetest.domain.Judge;
import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Subject;

public class SubjectDaoImpl extends HibernateDaoSupport implements SubjectDao{

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
	public PageBean<Subject> findSubjectByPage(int pageCode, int pageSize) {
		PageBean<Subject> pb = new PageBean<Subject>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		List subjectList = null;
		try {
			String sql = "SELECT count(*) FROM Subject";
			List list = this.getSession().createQuery(sql).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			//不支持limit分页
			String hql= "from Subject";
			//分页查询
			subjectList = doSplitPage(hql,pageCode,pageSize);
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		if(subjectList!=null && subjectList.size()>0){
			pb.setBeanList(subjectList);
			return pb;
		}
		return null;
	}

	@Override
	public Subject getSubjectByName(Subject subject) {
		String hql= "from Subject s where s.subjectName=?";
		List list = this.getHibernateTemplate().find(hql, subject.getSubjectName());
		if(list!=null && list.size()>0){
			return (Subject) list.get(0);
		}
		return null;
	}

	@Override
	public boolean addSubject(Subject subject) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().save(subject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public Subject getSubjectById(Subject subject) {
		String hql= "from Subject s where s.subjectId=?";
		List list = this.getHibernateTemplate().find(hql, subject.getSubjectId());
		if(list!=null && list.size()>0){
			return (Subject) list.get(0);
		}
		return null;
	}

	@Override
	public Subject updateSubject(Subject updateSubject) {
		Subject newSubject = null;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			newSubject = (Subject) this.getHibernateTemplate().merge(updateSubject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newSubject;
	}

	@Override
	public boolean deleteSubject(Subject subject) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().delete(subject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public PageBean<Subject> querySubject(Subject subject, int pageCode,
			int pageSize) {
		PageBean<Subject> pb = new PageBean<Subject>();	//pageBean对象，用于分�?
		//根据传入的pageCode当前页码和pageSize页面记录数来设置pb对象
		pb.setPageCode(pageCode);//设置当前页码
		pb.setPageSize(pageSize);//设置页面记录�?
		
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb_sql = new StringBuilder();
		String sql = "SELECT count(*) FROM Subject s where 1=1 ";
		String hql= "from Subject s where 1=1 ";
		sb.append(hql);
		sb_sql.append(sql);
		if(subject.getCourse().getCourseId()!=-1){
			sb.append(" and s.course.courseId = "+subject.getCourse().getCourseId());
			sb_sql.append(" and s.course.courseId ="+subject.getCourse().getCourseId());
		}
		if(!"".equals(subject.getSubjectName().trim())){
			sb.append(" and s.subjectName like '%" + subject.getSubjectName() +"%'");
			sb_sql.append(" and s.subjectName  like '%" + subject.getSubjectName() +"%'");
		}
		try{
			
			List list = this.getSession().createQuery(sb_sql.toString()).list();
			int totalRecord = Integer.parseInt(list.get(0).toString()); //得到总记录数
			pb.setTotalRecord(totalRecord);	//设置总记录数
			this.getSession().close();
			
			
			List<Subject> subjectList = doSplitPage(sb.toString(),pageCode,pageSize);
			if(subjectList!=null && subjectList.size()>0){
				pb.setBeanList(subjectList);
				return pb;
			}
		}catch (Throwable e1){
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return null;
	}

	@Override
	public boolean setChoiceNum(Subject subject) {
		Subject updateSubject = getSubjectById(subject);
		updateSubject.setChoiceNum(updateSubject.getChoices().size());
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			this.getHibernateTemplate().merge(updateSubject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public boolean setJudgeNum(Subject subject) {
		Subject updateSubject = getSubjectById(subject);
		updateSubject.setJudgeNum(updateSubject.getJudges().size());
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			this.getHibernateTemplate().merge(updateSubject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public boolean setAllScore(Subject subject) {
		Subject updateSubject = getSubjectById(subject);
		updateSubject.setAllScore(updateSubject.getChoiceScore()*updateSubject.getChoices().size()+updateSubject.getJudgeScore()*updateSubject.getJudges().size());
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			//将传入的detached(分离�?)状�?�的对象的属性复制到持久化对象中，并返回该持久化对象
			this.getHibernateTemplate().merge(updateSubject);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}


}
