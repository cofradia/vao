package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class Category extends SugarRecord<Category> {

	Long id;
	String description;
	
	public Category(Context ctx) {
		super(ctx);
	}

}
