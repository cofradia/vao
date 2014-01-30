package com.cofradia.vao.daogenerator.gen;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class VaoDaoGenerator {
	public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "de.greenrobot.daovao");
        
        //Add elements to schema
        addUsuario(schema);
        new DaoGenerator().generateAll(schema, "../DaoVao/src-gen");
    }
	
	 private static void addUsuario(Schema schema) {
	        Entity usuario = schema.addEntity("Usuario");
	        usuario.addIdProperty();
	        usuario.addStringProperty("text").notNull();
	        usuario.addStringProperty("nombre");
	        usuario.addDateProperty("otrapropiedad");
	    }


}
