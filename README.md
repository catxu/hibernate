# hibernate
面向对象的数据持久化框架

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