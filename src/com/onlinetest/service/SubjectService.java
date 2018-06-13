package com.onlinetest.service;

import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Subject;
import com.onlinetest.domain.Teacher;

public interface SubjectService {

	PageBean<Subject> findSubjectByPage(int pageCode, int pageSize);

	Subject getSubjectByName(Subject subject);

	boolean addSubject(Subject subject);

	Subject getSubjectById(Subject subject);

	Subject updateSubject(Subject updateSubject);

	boolean deleteSubject(Subject subject);

	PageBean<Subject> querySubject(Subject subject, int pageCode, int pageSize);

}
