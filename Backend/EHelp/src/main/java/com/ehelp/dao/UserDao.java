package com.ehelp.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.ehelp.entity.User;
import com.ehelp.util.DBSessionUtil;

@Repository
public class UserDao {

	// 用以注册时检查该手机号是否已被注册
	public boolean checkUser(String phone) {
		Session session = DBSessionUtil.getSession();
		// 查询语句
		Query query = session.createQuery(" from User u where u.phone=:phone");
		// 设定查询语句中变量的值
		query.setParameter("phone", phone);
		// 查询结果
		User u = (User) query.uniqueResult();
		// 事务提交并关闭
		DBSessionUtil.closeSession(session);
		if (u == null) {
			return true;
		}
		return false;
	}

	// 用以登录时检查数据库中是否存在该用户
	public boolean checkUser2(User user) {
		Session session = DBSessionUtil.getSession();
		// 查询语句
		Query query = session.createQuery(" from User u where u.username=:username and u.password=:password");
		// 设定查询语句中变量的值
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		// 查询结果
		User u = (User) query.uniqueResult();
		// 事务提交并关闭
		DBSessionUtil.closeSession(session);
		if (u == null) {
			return false;
		}
		return true;
	}

	// 添加用户
	public boolean addUser(String username, String password, String phone, String avatar, double longitude, double latitude) {
		Session session = DBSessionUtil.getSession();
		session.save(new User(username, password, phone, avatar, longitude, latitude));
		DBSessionUtil.closeSession(session);
		return true;
	}

	public static void main(String[] args) {
		UserDao dao = new UserDao();
		System.out.println(dao.checkUser("1881925"));
		dao.addUser("Gordan", "123456", "1881925", "avatar", 12, 12);
		System.out.println(dao.checkUser("1881925"));
	}

}
