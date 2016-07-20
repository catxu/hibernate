package com.catxu.hibernate.user;

import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.junit.Test;

import com.catxu.hibernate.util.HibernateUtil;

public class TestState {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Test
	public void testTransient() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBirth(sdf.parse("1997-03-24"));
			u.setUsername("刘球球");
			u.setNickname("敲开心小姐");
			u.setPassword("2333");
			//以上 u 为transient状态，表示没有被 session 管理且数据库中没有
			session.save(u);	//执行了save方法被session所管理且数据库中已经存在。
			//此时为persistent状态
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testPersistent01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBirth(sdf.parse("1997-03-24"));
			u.setUsername("刘球球");
			u.setNickname("敲开心小姐");
			u.setPassword("2333");
			//以上 u 为transient状态，表示没有被 session 管理且数据库中没有
			session.save(u);	//执行了save方法被session所管理且数据库中已经存在。
			
			//此时为persistent状态，已经被 session 所管理，当发生提交时，会把session中的对象
			//和目前的对象进行比较，如果两个对象的值不一致就会继续发出相应的sql语句
			u.setNickname("玛格丽特小姐");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testPersistent02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBirth(sdf.parse("1997-03-24"));
			u.setUsername("刘球球");
			u.setNickname("敲嗨森小姐");
			u.setPassword("2333");
			session.save(u);
			u.setPassword("2333333333");
			//下面这个save 无意义
			session.save(u);
			u.setNickname("玛格丽特大舌头小姐");
			session.update(u);	//无意义
			u.setBirth(sdf.parse("1996-07-04"));
			session.update(u);	//无意义
			//只会在提交前将当前对象和缓存中的对象引用进行比较，有差异才会执行相应的操作 
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testPersistent03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = (User)session.load(User.class, 20);
			u.setUsername("Truble");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(20);
			u.setUsername("Truble");
			session.save(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(20);
			u.setUsername("Truble");
			session.update(u);
			u.setBirth(sdf.parse("1999-12-12"));
			u.setNickname("lqq");
			u.setUsername("truble");
			session.update(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(20);
			u.setUsername("Truble");
			session.update(u);
			u.setBirth(sdf.parse("1999-12-12"));
			u.setNickname("lqq");
			u.setUsername("truble");
			u.setId(231);	//异常：
			session.update(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(20);
			u.setUsername("xTruble");
			session.delete(u);	//deatch -> transient
			u.setUsername("uuu");

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(21);
			u.setUsername("xTruble");
			session.saveOrUpdate(u); //如果 u 为瞬时状态就执行save sql，如果u为游离态就执行update
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	@Test
	public void testDetached05() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u1 = (User)session.load(User.class, 21);
			System.out.println(u1.getId());
			User u2 = new User();
//			u2.setId(11);
			u2.setId(21);
			u2.setPassword("23");
//			session.saveOrUpdate(u2); //此时 u2 将会变成持久化状态  在同一session的缓存中不能保存
			//两个同样的对象，抛出异常 解决：merge
			session.merge(u2);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null)
				session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
