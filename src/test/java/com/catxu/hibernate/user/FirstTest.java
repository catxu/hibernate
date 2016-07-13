package com.catxu.hibernate.user;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class FirstTest {

	@Test
	public void test01() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties()).build();
//		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		
		Session session = factory.openSession();
		session.beginTransaction();
		User u = new User();
		u.setNickname("阿黄");
		u.setPassword("123");
		u.setUsername("狗哥");
		u.setBirth(new Date());
		session.save(u);
		session.getTransaction().commit();
	}

}
