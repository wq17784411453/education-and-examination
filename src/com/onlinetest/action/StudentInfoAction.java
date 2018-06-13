package com.onlinetest.action;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.onlinetest.domain.Student;
import com.onlinetest.service.StudentService;
import com.onlinetest.utils.Md5Utils;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class StudentInfoAction extends ActionSupport{

	private StudentService  studentService;

	
	
	/**
	 * @param studentService the studentService to set
	 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}









	private String oldPwd;
	private String newPwd;
	private String confirmPwd;
	
	
	
	





	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}




	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}




	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}








	
	/**
	 * 管理员密码修�?
	 * @return
	 */
	public String studentPwd(){
		Student student = (Student) ServletActionContext.getContext().getSession().get("student");
		int state = -1;//原密码错�?
		//取出原密码进行比�?
		if(student.getPassword().equals(Md5Utils.md5(oldPwd))){
			if(newPwd.equals(confirmPwd)){
				state = 1;//修改成功
				student.setPassword(Md5Utils.md5(newPwd));
				student = studentService.updateStudentInfo(student);
				//重新存入session
				ServletActionContext.getContext().getSession().put("student", student);
			}else{
				state = 0;//确认密码不一�?
			}
		}
		try {
			ServletActionContext.getResponse().getWriter().print(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}
