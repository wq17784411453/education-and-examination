package com.onlinetest.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.onlinetest.dao.OnLineTestDao;
import com.onlinetest.domain.Answer;
import com.onlinetest.domain.Choice;
import com.onlinetest.domain.Course;
import com.onlinetest.domain.Judge;
import com.onlinetest.domain.Student;
import com.onlinetest.domain.Subject;
import com.onlinetest.service.OnLineTestService;

public class OnLineTestDaoImpl extends HibernateDaoSupport implements OnLineTestDao{

	@Override
	public boolean addAnswer(Answer answer) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().save(answer);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}

	@Override
	public Answer getChoiceAnswer(Student student, Subject subject,
			Choice choice) {
		String hql= "from Answer a where a.student.studentId=? and a.subject.subjectId=? and a.question=? and a.questionType=1";
		List list = this.getHibernateTemplate().find(hql, new Object[]{student.getStudentId(),subject.getSubjectId(),choice.getChoiceId()});
		if(list!=null && list.size()>0){
			return (Answer) list.get(0);
		}
		return null;
	}

	@Override
	public Answer getJudgeAnswer(Student student, Subject subject, Judge judge) {
		String hql= "from Answer a where a.student.studentId=? and a.subject.subjectId=? and a.question=? and a.questionType=2";
		List list = this.getHibernateTemplate().find(hql, new Object[]{student.getStudentId(),subject.getSubjectId(),judge.getJudgeId()});
		if(list!=null && list.size()>0){
			return (Answer) list.get(0);
		}
		return null;
	}



}
