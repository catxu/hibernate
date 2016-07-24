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

# JDBC SQL 关联查询

两条 sql 语句：

public User load(int id) {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	List<Address> as = new ArrayList<Address>();
	Address a = null;
	User u = null;

	try {
		con = DBUtil.getConnection();
		String sql = "select * from t_user where id=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while(rs.next()) {
			u = new User();
			u.setId(rs.getInt("id"));
		}
		sql = "select * from t_address where user_id=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while(rs.next()) {
			a = new Address();
			a.setId(rs.getInt("id"));
			a.setPhone(rs.getString("Phone"));
			as.add(a);
		}
		u.setAddress(as);
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
	}
	return u;
}

一条 sql 语句：

String sql = "select *,t2.id as 'a_id' from t_user t1 
left join t_address t2 on(t1.id=t2.user_id) 
where t1.id=?"

然后在 while 循环中，

while(rs.next()) {
			if(u == null) {	// 只有第一次查询需要为 u 赋值
				u = new User();
				u.setId(rs.getInt("user_id"));
			}
			a = new Address();
			a.setId(rs.getInt("a_id"));
			a.setPhone(rs.getString("Phone"));
			as.add(a);
		}