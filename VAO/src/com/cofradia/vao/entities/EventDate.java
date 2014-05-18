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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
