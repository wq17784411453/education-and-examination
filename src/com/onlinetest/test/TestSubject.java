package com.onlinetest.test;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Test;

import com.onlinetest.domain.Choice;
import com.onlinetest.domain.Subject;

public class TestSubject extends BaseSpring{

	@Test
	public void testGetSubject(){
		SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Subject subject = (Subject) session.get(Subject.class, 1);
		System.out.println(subject.getChoices().size());
		Set<Choice> choices = subject.getChoices();
		for(Choice choice : choices){
			System.out.println(choice.getAnswer());
		}
		session.close();
	}
}
