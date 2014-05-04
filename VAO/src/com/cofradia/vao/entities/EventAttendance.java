package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class EventAttendance extends SugarRecord<EventAttendance>{
	
	Long id;
	Event event;
	User attendee;
	
	public EventAttendance(Context ctx) {
		super(ctx);
	}

}
