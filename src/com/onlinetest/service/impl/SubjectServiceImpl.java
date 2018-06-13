package com.onlinetest.service.impl;

import com.onlinetest.dao.SubjectDao;
import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Subject;
import com.onlinetest.domain.Teacher;
import com.onlinetest.service.SubjectService;

public class SubjectServiceImpl implements SubjectService{

	private SubjectDao subjectDao;

	/**
	 * @param subjectDao the subjectDao to set
	 */
	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	public PageBean<Subject> findSubjectByPage(int pageCode, int pageSize) {
		// TODO Auto-generated method stub
		return subjectDao.findSubjectByPage(pageCode,pageSize);
	}

	@Override
	public Subject getSubjectByName(Subject subject) {
		// TODO Auto-generated method stub
		return subjectDao.getSubjectByName(subject);
	}

	@Override
	public boolean addSubject(Subject subject) {
		// TODO Auto-generated method stub
		return subjectDao.addSubject(subject);
	}

	@Override
	public Subject getSubjectById(Subject subject) {
		// TODO Auto-generated method stub
		return subjectDao.getSubjectById(subject);
	}

	@Override
	public Subject updateSubject(Subject updateSubject) {
		// TODO Auto-generated method stub
		Subject subject = subjectDao.updateSubject(updateSubject);
		//同时�?要修改�?�分
		subjectDao.setAllScore(subject);
		return subject;
	}

	@Override
	public boolean deleteSubject(Subject subject) {
		// TODO Auto-generated method stub
		return subjectDao.deleteSubject(subject);
	}

	@Override
	public PageBean<Subject> querySubject(Subject subject, int pageCode,
			int pageSize) {
		// TODO Auto-generated method stub
		return subjectDao.querySubject(subject,pageCode,pageSize);
	}
	
	
}
