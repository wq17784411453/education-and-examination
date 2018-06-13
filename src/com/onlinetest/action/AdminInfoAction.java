package com.onlinetest.action;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.onlinetest.domain.Admin;
import com.onlinetest.service.AdminService;
import com.onlinetest.utils.Md5Utils;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AdminInfoAction extends ActionSupport{

	private AdminService  adminService;

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
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
	public String adminPwd(){
		Admin admin = (Admin) ServletActionContext.getContext().getSession().get("admin");
		int state = -1;//原密码错�?
		//取出原密码进行比�?
		if(admin.getPassword().equals(Md5Utils.md5(oldPwd))){
			if(newPwd.equals(confirmPwd)){
				state = 1;//修改成功
				admin.setPassword(Md5Utils.md5(newPwd));
				admin = adminService.updateAdminInfo(admin);
				//重新存入session
				ServletActionContext.getContext().getSession().put("admin", admin);
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
