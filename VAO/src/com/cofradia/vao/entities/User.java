package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class User extends SugarRecord<User> {

	private Long id;

	private String name;
	private String lastname;
	private String mail;
	private String fbToken;
	private String vaoToken;
	private Boolean activeSession;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public User(Context context, String name, String lastname, String mail) {
		super(context);
		this.name = name;
		this.lastname = lastname;
		this.mail = mail;

	}

	public User(Context context) {
		super(context);
	}

}
