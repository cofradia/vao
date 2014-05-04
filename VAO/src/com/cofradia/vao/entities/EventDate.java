package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class EventDate  extends SugarRecord<EventDate>{

	Long id;
	String startDate;
	String endDate;
	Event event;
	
	public EventDate(Context ctx) {
		super(ctx);
	}

}
