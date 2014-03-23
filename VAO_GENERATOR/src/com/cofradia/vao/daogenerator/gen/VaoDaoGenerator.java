package com.cofradia.vao.daogenerator.gen;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class VaoDaoGenerator {
	public static void main(String[] args) throws Exception {
		Schema event = new Schema(1, "de.greenrobot.daovao.event");
		Schema user = new Schema(1, "de.greenrobot.daovao.user");

		//new option
		// Add elements to schema
		addUser(user);
		addEvent(event);

		DaoGenerator DaoGen = new DaoGenerator();
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
		Entity event = schema.addEntity("Event");
		event.addIdProperty();
		event.addStringProperty("name").notNull();
		event.addStringProperty("description").notNull();
		event.addIntProperty("likes");
		event.addFloatProperty("rating");
		event.addStringProperty("mood");
		event.addStringProperty("image");
		event.addStringProperty("fbEventId");
		event.addIntProperty("categoryId").notNull();
		event.addStringProperty("privacyId").notNull();
		event.addIntProperty("placeId").notNull();
	}

}
