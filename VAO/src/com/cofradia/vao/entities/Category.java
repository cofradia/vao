package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class Category extends SugarRecord<Category> {

	private Long id;
	private String description;

	public Category(Context arg0, Long id, String description) {
		super(arg0);
		this.id = id;
		this.description = description;
	}

	public Category(Context ctx) {
		super(ctx);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
