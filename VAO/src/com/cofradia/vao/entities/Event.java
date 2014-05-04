package com.cofradia.vao.entities;

import android.content.Context;

import com.cofradia.vao.Book;
import com.orm.SugarRecord;

public class Event  extends SugarRecord<Event> {
	  Long id;
	  String name;
	  String description;
	  Integer likes;
	  Double rating;
	  String mood;
	  String imageUrl;
	  Long fbEventId;
	  Category category;
	  String privacy;
	  Place place;
	  User host;
	  

	  public Event(Context ctx){
	    super(ctx);
	  }

	  public Event(Context ctx, Long id, String name){
	    super(ctx);
	    this.id = id;
	    this.name = name;
	  }
}
