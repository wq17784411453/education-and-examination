package com.onlinetest.dao;

import java.util.List;

import com.onlinetest.domain.Admin;


public interface AdminDao {

	public Admin getAdminByUserName(Admin admin);

	public Admin updateAdminInfo(Admin admin);


	public boolean addAdmin(Admin admin);

	public Admin getAdminById(Admin admin);

}
