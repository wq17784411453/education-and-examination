package com.onlinetest.dao;

import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.Score;
import com.onlinetest.domain.Student;
import com.onlinetest.domain.Subject;

public interface ScoreDao {

	boolean addScore(Score score);

	Score getScore(Student student, Subject subject);

	PageBean<Score> findScoreByPage(int pageCode, int pageSize);

	PageBean<Score> queryMyScore(Subject subject, int pageCode, int pageSize);

	PageBean<Score> findMyScoreByPage(Student student, int pageCode,
			int pageSize);

	PageBean<Score> queryScore(Subject subject, Student student, int pageCode,
			int pageSize);
}
