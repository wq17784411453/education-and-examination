package com.onlinetest.service;

import java.util.List;

import com.onlinetest.domain.Course;
import com.onlinetest.domain.PageBean;

public interface CourseService {

	PageBean<Course> findCourseByPage(int pageCode, int pageSize);

	boolean addCourse(Course course);

	Course getCourseByName(Course course);

	Course getCourseById(Course course);

	Course updateCourse(Course updateCourse);

	boolean deleteCourse(Course course);

	PageBean<Course> queryCourse(Course course, int pageCode, int pageSize);

	List<Course> getAllCourses();

}
