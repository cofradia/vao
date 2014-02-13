package com.cofradia.vao.daogenerator.gen;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class VaoDaoGenerator {
	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "de.greenrobot.daovao");
		Schema event = new Schema(1, "de.greenrobot.daovao.event");
		Schema user = new Schema(1, "de.greenrobot.daovao.user");

		// Add elements to schema
		addUser(schema);
		addEvent(event);

		DaoGenerator DaoGen = new DaoGenerator();
		DaoGen.generateAll(schema, "../VAO/src-gen");
		DaoGen.generateAll(event, "../VAO/src-gen");
		DaoGen.generateAll(user, "../VAO/src-gen");

	}

	private static void addUser(Schema schema) {
		Entity usuario = schema.addEntity("User");
		usuario.addIdProperty();
		usuario.addStringProperty("user").notNull();
		usuario.addStringProperty("userId").notNull();
		usuario.addStringProperty("password");
		usuario.addStringProperty("fbToken");
		usuario.addStringProperty("vaoToken");
		usuario.addBooleanProperty("activeSession");	
	}

	private static void addEvent(Schema schema) {
		Entity event = schema.addEntity("Evento");
		event.addIdProperty();
		event.addStringProperty("nombre").notNull();
		event.addStringProperty("descripcion");
		event.addIntProperty("likes");
		event.addFloatProperty("rating");
		event.addStringProperty("mood");
		event.addStringProperty("imagen");
		event.addStringProperty("fb_evento_id").notNull();
		event.addStringProperty("id_categoria").notNull();
		event.addStringProperty("id_lugar").notNull();
		event.addStringProperty("id_privacidad").notNull();
	}

}
