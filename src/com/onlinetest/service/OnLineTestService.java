package com.onlinetest.service;

import java.util.List;

import com.onlinetest.domain.ResultScore;
import com.onlinetest.domain.Student;
import com.onlinetest.domain.Subject;

public interface OnLineTestService {

	boolean onLineTest(Student student, Subject subject, String answer);

	ResultScore getResultScore(Student student, Subject subject);
}
