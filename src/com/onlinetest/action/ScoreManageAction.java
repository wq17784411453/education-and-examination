package com.onlinetest.action;

import org.apache.struts2.ServletActionContext;

import com.onlinetest.domain.Course;
import com.onlinetest.domain.PageBean;
import com.onlinetest.domain.ResultScore;
import com.onlinetest.domain.Score;
import com.onlinetest.domain.Student;
import com.onlinetest.domain.Subject;
import com.onlinetest.service.OnLineTestService;
import com.onlinetest.service.ScoreService;
import com.onlinetest.service.SubjectService;
import com.opensymphony.xwork2.ActionSupport;

public class ScoreManageAction extends ActionSupport{

	private ScoreService scoreService;

	private SubjectService subjectService;
	
	private OnLineTestService onLineTestService;
	
	
	private int pageCode;
	
	/**
	 * @return the pageCode
	 */
	public int getPageCode() {
		return pageCode;
	}

	private int courseId;
	private String subjectName;
	private String studentId;
	private String studentName;
	
	
	

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	/**
	 * @param pageCode the pageCode to set
	 */
	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}


	/**
	 * @param onLineTestService the onLineTestService to set
	 */
	public void setOnLineTestService(OnLineTestService onLineTestService) {
		this.onLineTestService = onLineTestService;
	}


	/**
	 * @param subjectService the subjectService to set
	 */
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}


	/**
	 * @param scoreService the scoreService to set
	 */
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}
	
	
	
	private int subjectId;
	
	
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}


	/**
	 * 得到考试的成绩的详情
	 * @return
	 */
	public String getTestScore(){
		//http://localhost:8080/OnLineTest/student/scoreManageAction_getTestScore.action?subjectId=1
		Subject subject = new Subject();
		subject.setSubjectId(subjectId);
		Student student = new Student();
		student.setStudentId(studentId);
		//得到成绩
		Score score = scoreService.getScore(student, subject);
		ResultScore resultScore = onLineTestService.getResultScore(student, subject);
		resultScore.setScore(score);
		//存入request域中
		ServletActionContext.getRequest().setAttribute("resultScore", resultScore);
		return  "success";
	}
	
	
	public String findScoreByPage(){
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
		int pageSize = 5;
		
		PageBean<Score> pb = scoreService.findScoreByPage(pageCode,pageSize);
		if(pb!=null){
			pb.setUrl("findScoreByPage.action?");
		}
		//存入request域中
		ServletActionContext.getRequest().setAttribute("pb", pb);
		return  "scores";
	}
	
	
	public String findMyScoreByPage(){
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
		int pageSize = 5;
		Student student = (Student) ServletActionContext.getContext().getSession().get("student");
		PageBean<Score> pb = scoreService.findMyScoreByPage(student,pageCode,pageSize);
		if(pb!=null){
			pb.setUrl("findScoreByPage.action?");
		}
		//存入request域中
		ServletActionContext.getRequest().setAttribute("pb", pb);
		return  "myscores";
	}
	
	
	public String queryScore(){
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
		int pageSize = 5;
		PageBean<Score> pb = null;
		if(courseId==-1 && "".equals(subjectName.trim()) && "".equals(studentName.trim()) && "".equals(studentId.trim())){
			pb = scoreService.findScoreByPage(pageCode,pageSize);
		}else{
			Subject subject = new Subject();
			subject.setSubjectName(subjectName);
			Course course = new Course();
			course.setCourseId(courseId);
			subject.setCourse(course);
			Student student = new Student();
			student.setStudentId(studentId);
			student.setStudentName(studentName);
			pb = scoreService.queryScore(subject,student,pageCode,pageSize);
			
		} 
		if(pb!=null){
			pb.setUrl("queryScore.action?courseId="+courseId+"&subjectName="+subjectName+"&studentId="+studentId+"&studentName="+studentName+"&");
		}

		ServletActionContext.getRequest().setAttribute("pb", pb);
		return "scores";
	}
	
	public String queryMyScore(){
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
		int pageSize = 5;
		PageBean<Score> pb = null;
		if(courseId==-1 && "".equals(subjectName.trim())){
			pb = scoreService.findScoreByPage(pageCode,pageSize);
		}else{
			Subject subject = new Subject();
			subject.setSubjectName(subjectName);
			Course course = new Course();
			course.setCourseId(courseId);
			subject.setCourse(course);
			pb = scoreService.queryMyScore(subject,pageCode,pageSize);
			
		} 
		if(pb!=null){
			pb.setUrl("queryMyScore.action?courseId="+courseId+"&subjectName="+subjectName+"&");
		}

		ServletActionContext.getRequest().setAttribute("pb", pb);
		return "myscores";
	}
}
