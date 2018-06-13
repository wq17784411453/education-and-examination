package com.onlinetest.dao;

import com.onlinetest.domain.Answer;
import com.onlinetest.domain.Choice;
import com.onlinetest.domain.Judge;
import com.onlinetest.domain.Student;
import com.onlinetest.domain.Subject;

public interface OnLineTestDao {


	boolean addAnswer(Answer an);

	Answer getChoiceAnswer(Student student, Subject subject, Choice choice);

	Answer getJudgeAnswer(Student student, Subject subject, Judge judge);

}
