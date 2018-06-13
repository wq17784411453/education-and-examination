package com.onlinetest.service;

import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Student;

public interface StudentService {

	Student getStudentById(Student student);

	Student updateStudent(Student updateStudent);

	boolean addStudent(Student student);

	PageBean<Student> findStudentByPage(int pageCode, int pageSize);

	PageBean<Student> queryStudent(Student student, int pageCode, int pageSize);

	boolean deleteStudent(Student student);

	Student updateStudentInfo(Student student);

}
