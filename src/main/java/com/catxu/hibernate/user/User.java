package com.catxu.hibernate.user;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class User 
{
	private int id;
	private String username;
	private String password;
	private String nickname;
	private Date birth;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
