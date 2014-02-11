package DataAccess;

import com.cofradia.vao.events.EventComment;

public class DAEvent {
	public EventComment eventComments;
	public int created;
	public int updated;
	public int id;
	public String nombre;
	public String descripcion;
	public String mood;
	public String imagen;
	public String fb_evento_id;
	public int categoria_id;
	public int privacidad_id;
	public int asistencia_evento_id;
    public int fecha_evento_id;
	public int likes;
	public float rating;

	public DAEvent() {
		this.eventComments = new EventComment(1);
		// TODO Auto-generated constructor stub
	}

	public DAEvent(int id) {
		this.nombre = "Nombre del evento de id:" + id;
		this.descripcion = "Nombre del evento de id:" + id;
		this.mood = "Evento " + id + "Me siento graciosamente bien :D";
		this.fb_evento_id = null;
		this.categoria_id = 0;
		this.privacidad_id = 0;
		this.eventComments = new EventComment(1);
		// TODO Auto-generated constructor stub
	}

	public EventComment getComment() {
		return eventComments;
	}

	public EventComment getLastComment(int eventId) {
		return eventComments;
	}

	public void setComment(EventComment comment) {
		this.eventComments = comment;
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

	public int getId_categoria() {
		return categoria_id;
	}

	public void setId_categoria(int id_categoria) {
		this.categoria_id = id_categoria;
	}

	public int getId_privacidad() {
		return privacidad_id;
	}

	public void setId_privacidad(int id_privacidad) {
		this.privacidad_id = id_privacidad;
	}

	public int getId_asistencia_evento() {
		return asistencia_evento_id;
	}

	public void setId_asistencia_evento(int id_asistencia_evento) {
		this.asistencia_evento_id = id_asistencia_evento;
	}

	public int getId_fecha_evento() {
		return fecha_evento_id;
	}

	public void setId_fecha_evento(int id_fecha_evento) {
		this.fecha_evento_id = id_fecha_evento;
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
