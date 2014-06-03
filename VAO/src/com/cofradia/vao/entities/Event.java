package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class Event extends SugarRecord<Event> {
	private Long id;
	private String name;
	private String description;
	private Integer likes;
	private Double rating;
	private String mood;
	private String imageUrl;
	private Long fbEventId;
	private String privacy;
	//Relationships
	private Category category;
	private Place place;
	private User host;

	public Event(Context ctx) {
		super(ctx);
	}

	public Event(Context ctx, Long id, String name) {
		super(ctx);
		this.id = id;
		this.name = name;
	}

	public Event(Context arg0, Long id, String name, String description,
			Integer likes, Double rating, String mood, String imageUrl,
			Long fbEventId, Category category, String privacy, Place place,
			User host) {
		super(arg0);
		this.id = id;
		this.name = name;
		this.description = description;
		this.likes = likes;
		this.rating = rating;
		this.mood = mood;
		this.imageUrl = imageUrl;
		this.fbEventId = fbEventId;
		this.category = category;
		this.privacy = privacy;
		this.place = place;
		this.host = host;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getFbEventId() {
		return fbEventId;
	}

	public void setFbEventId(Long fbEventId) {
		this.fbEventId = fbEventId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return id + ": " + name + " - " + description;
	}

}
