package com.catxu.hibernate.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.catxu.hibernate.util.HibernateUtil;

public class TestCRUD {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Test
	public void testAdd() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			
			User u = new User();
			u.setBirth(sdf.parse("2016-07-19"));
			u.setNickname("大庞鸡");
			u.setPassword("132");
			u.setUsername("是gay");
			session.save(u);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
//				session.close();
				session.getTransaction().rollback();
			}
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testLoad() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			User u = (User) session.load(User.class, 14);
			System.out.println(u);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testUpdate() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			
			User u = (User) session.load(User.class, 4);
			u.setNickname("狗子你变了");
			session.update(u);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDelete() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			
			User u = new User();
			u.setId(4);
			session.delete(u);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testList() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			List<User> users = session.createQuery("from User").list();
			for (User user : users) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testPage() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			List<User> users = session.createQuery("from User")
						.setFirstResult(0).setMaxResults(2).list();
			for (User user : users) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
