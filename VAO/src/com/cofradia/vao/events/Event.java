package com.cofradia.vao.events;

public class Event {
	public EventComment comment;
	public int created;
	public int updated;
	public int id;
	public String nombre;
	public String descripcion;
	public String mood;
	public String imagen;
	public String fb_evento_id;
	public String id_categoria;
	public String id_lugar;
	public String id_privacidad;
	public int likes;
	public float rating;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventComment getComment() {
		return comment;
	}

	public void setComment(EventComment comment) {
		this.comment = comment;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getFb_evento_id() {
		return fb_evento_id;
	}

	public void setFb_evento_id(String fb_evento_id) {
		this.fb_evento_id = fb_evento_id;
	}

	public String getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(String id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getId_lugar() {
		return id_lugar;
	}

	public void setId_lugar(String id_lugar) {
		this.id_lugar = id_lugar;
	}

	public String getId_privacidad() {
		return id_privacidad;
	}

	public void setId_privacidad(String id_privacidad) {
		this.id_privacidad = id_privacidad;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
