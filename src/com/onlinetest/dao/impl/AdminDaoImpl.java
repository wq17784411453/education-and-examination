package com.onlinetest.dao.impl;

import java.sql.SQLException;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.onlinetest.dao.AdminDao;
import com.onlinetest.domain.Admin;



public class AdminDaoImpl extends HibernateDaoSupport implements AdminDao{

	@Override
	public Admin getAdminByUserName(Admin admin) {
		String hql= "from Admin a where a.username=?";
		List list = this.getHibernateTemplate().find(hql, admin.getUsername());
		if(list!=null && list.size()>0){
			return (Admin) list.get(0);
		}
		return null;
	}

	
	@Override
	public Admin updateAdminInfo(Admin admin) {
		Admin newAdmin = null;
		try{
			this.getHibernateTemplate().clear();
			//灏嗕紶鍏ョ殑detached(鍒嗙鐨�)鐘舵�佺殑瀵硅薄鐨勫睘鎬у鍒跺埌鎸佷箙鍖栧璞′腑锛屽苟杩斿洖璇ユ寔涔呭寲瀵硅薄
			newAdmin = (Admin) this.getHibernateTemplate().merge(admin);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return newAdmin;
	}




	@Override
	public boolean addAdmin(Admin admin) {
		boolean b = true;
		try{
			this.getHibernateTemplate().clear();
			this.getHibernateTemplate().save(admin);
			this.getHibernateTemplate().flush();
		}catch (Throwable e1) {
			b = false;
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		return b;
	}


	@Override
	public Admin getAdminById(Admin admin) {
		String hql= "from Admin a where a.aid=?";
		List list = this.getHibernateTemplate().find(hql, admin.getAdminId());
		if(list!=null && list.size()>0){
			return (Admin) list.get(0);
		}
		return null;
	}



}
