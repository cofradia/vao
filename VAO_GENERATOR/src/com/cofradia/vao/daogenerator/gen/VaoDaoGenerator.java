package com.cofradia.vao.daogenerator.gen;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class VaoDaoGenerator {
	public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "de.greenrobot.daovao");
        
       // addNote(schema);
       // addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../DaoVao/src-gen");
    }

}
