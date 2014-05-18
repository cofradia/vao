package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class EventAttendance extends SugarRecord<EventAttendance>{
	
	private Long id;
	private Event event;
	private User attendee;
	
	public EventAttendance(Context ctx) {
		super(ctx);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getAttendee() {
		return attendee;
	}

	public void setAttendee(User attendee) {
		this.attendee = attendee;
	}

}
