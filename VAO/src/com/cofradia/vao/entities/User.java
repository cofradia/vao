package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class User extends SugarRecord<User>{

	Long id;
	String name;
	String lastname;
	String mail;
	String fbToken;
    private String vaoToken;
    private Boolean activeSession;
	


	public User(Context context) {
		super(context);
	}

}
