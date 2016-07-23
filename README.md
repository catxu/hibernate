# Hibernate
面向对象的数据持久化框架

Hibernate 的三种状态：
transient(瞬时状态)，persistent(持久化状态)以及detached(离线状态)

JBoss for STS:

http://tools.jboss.org/downloads/jbosstools/mars/4.3.1.Final.html#zips


# 列出 t_user 表中所有不重复的 username

SELECT distinct username from t_user;	

SELECT * from t_user  WHERE id=2 
UNION [ALL] # 允许重复

SELECT * from t_user WHERE id BETWEEN 3 AND 4

SELECT *
FROM t_user b
WHERE id NOT IN (SELECT * FROM t_user WHERE id BETWEEN 3 AND 4) #Operand should contain 1 column(s)

SELECT *
FROM t_user b
WHERE id NOT IN (SELECT id FROM t_user WHERE id BETWEEN 3 AND 4)

DELETE FROM t_user WHERE id=2 OR id BETWEEN 5 AND 13

# SQL statements

drop database IF EXISTS catxu_myblog;
create database catxu_myblog;
GRANT ALL ON catxu_myblog.* TO 'catxu'@'localhost' IDENTIFIED BY '123456';
use catxu_myblog;
create table t_user{
	id int(11) primary key auto_increment,
	username varchar(30),
	password varchar(30),
	email varchar(40),
	type int(5)
};

create table t_comment{
	id int(11) primary key auto_increment,
	post_time datetime,
	title varchar(40),
	content text,
	user_id int(11),
	CONSTRAINT FOREIGN KEY(user_id) REFERENCES t_user(id)
};