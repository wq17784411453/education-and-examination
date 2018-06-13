package com.onlinetest.service;

import java.util.List;

import com.onlinetest.domain.Admin;



public interface AdminService {
	/**
	 * 指定管理员用户名得到指定管理�?
	 * @param admin
	 * @return
	 */
	public Admin getAdminByUserName(Admin admin);

	/**
	 * 修改管理员个人资�?
	 * @param admin 传入修改的对�?
	 * @return 修改后的对象
	 */
	public Admin updateAdminInfo(Admin admin);


	/**
	 * 添加管理�?
	 * @param admin 传入添加的管理员
	 * @return	是否添加成功
	 */
	public boolean addAdmin(Admin admin);

	/**
	 * 指定管理员id得到指定管理�?
	 * @param admin
	 * @return
	 */
	public Admin getAdminById(Admin admin);
	
	
}
