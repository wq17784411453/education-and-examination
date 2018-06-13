package com.onlinetest.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;



import com.onlinetest.domain.Admin;
import com.onlinetest.service.AdminService;
import com.onlinetest.utils.Md5Utils;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class AdminLoginAction extends ActionSupport {

	private AdminService adminService;
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}


	private String username;
	private String password;


	
	public void setUsername(String username) {
		this.username = username;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}




	/**
	 * Ajax异步请求获得登录许可
	 * @return 返回登录状�??
	 */
	public String login(){
		//管理�?
		Admin admin = new  Admin();
		admin.setUsername(username);
		//admin.setPassword(Md5Utils.md5(password));
		admin.setPassword(password);
		Admin newAdmin = adminService.getAdminByUserName(admin);
		int login = 1;
		if(newAdmin==null){
			//用户名不存在
			login = -1;
		}else if(!newAdmin.getPassword().equals(admin.getPassword())){
			//密码不正�?
			login = -2;
		}else{
			//存储入session
			ServletActionContext.getContext().getSession().put("admin", newAdmin);
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
		ServletActionContext.getContext().getSession().remove("admin");
		return "logout";
	}
	
	
	

	
}
