package com.cofradia.vao.entities;

import android.content.Context;

import com.orm.SugarRecord;

public class Place extends SugarRecord<Place> {
	private Long id;
	private String name;
	private Double latitude;
	private Double longitude;

	public Long getId() {
		return id;
	}

	public Place(Context arg0, Long id, String name, Double latitude,
			Double longitude) {
		super(arg0);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Place(Context context) {
		super(context);
	}

}
