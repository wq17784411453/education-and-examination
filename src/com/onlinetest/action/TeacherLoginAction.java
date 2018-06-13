package com.onlinetest.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;



import com.onlinetest.domain.Student;
import com.onlinetest.domain.Teacher;
import com.onlinetest.service.TeacherService;
import com.onlinetest.utils.Md5Utils;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class TeacherLoginAction extends ActionSupport {

	private TeacherService teacherService;
	
	


	


	/**
	 * @param teacherService the teacherService to set
	 */
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}


	private String teacherId;
	private String password;


	


	/**
	 * @param teacherId the teacherId to set
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	/**
	 * Ajax异步请求获得登录许可
	 * @return 返回登录状�??
	 */
	public String login(){
		Teacher teacher = new  Teacher();
		teacher.setTeacherId(teacherId);
		//teacher.setPassword(Md5Utils.md5(password));
		teacher.setPassword(password);
		Teacher newTeacher = teacherService.getTeacherById(teacher);
		int login = 1;
		if(newTeacher==null){
			//用户名不存在
			login = -1;
		}else if(!newTeacher.getPassword().equals(teacher.getPassword())){
			//密码不正�?
			login = -2;
		}else{
			//存储入session
			ServletActionContext.getContext().getSession().put("teacher", newTeacher);
		}
		 HttpServletResponse response = ServletActionContext.getResponse();
		 try {	
			response.getWriter().print(login);		
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * �?出登�?
	 */
	public String logout(){
		ServletActionContext.getContext().getSession().remove("teacher");
		return "logout";
	}
	
	
	

	
}
