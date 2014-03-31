package com.cofradia.vao;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.cofradia.vao.tasks.UrlJsonAsyncTask;

import de.greenrobot.daovao.event.DaoMaster;
import de.greenrobot.daovao.event.Event;
import de.greenrobot.daovao.event.EventDao;
public class EventListTask extends UrlJsonAsyncTask{
	
	private final static String EVENT_API_ENDPOINT_URL = "http://vao-ws.herokuapp.com/v1/events.json";
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private de.greenrobot.daovao.event.DaoSession daoSession;
	private de.greenrobot.daovao.event.EventDao eventDao;
	
	String event_name;
	String event_description;
	String event_place_name;
	String event_start_date;
	String event_start_time;
	String event_end_date;
	String event_end_time;
	Float event_place_latitude;
	Float event_place_longitude;
	Integer event_category;
	String event_privacy;
	SharedPreferences mPreferences;
	
	int count;

	public EventListTask(Context context) {
		super(context);
		db_init(context);
		this.mPreferences = context.getSharedPreferences("CurrentUser", android.content.Context.MODE_PRIVATE);
		// TODO Auto-generated constructor stub
	}
	
    private void db_init(Context context) {
		de.greenrobot.daovao.event.DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "vao-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		eventDao = daoSession.getEventDao();
	}

	public void doEventList(int i){
    	this.setMessageLoading("Perate un toque, estamos cargando los últimos eventos...");
    	this.execute(EVENT_API_ENDPOINT_URL);
        //TODO: modify returning value to handle exceptions or fail cases
    }
	
    @Override
    protected JSONObject doInBackground(String... urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urls[0]);
        JSONObject holder = new JSONObject();
        JSONObject eventObj = new JSONObject();
        String response = null;
        JSONObject json = new JSONObject();

        try {
            try {
                // setup the returned values in case
                // something goes wrong
                json.put("success", false);
                json.put("info", "Something went wrong. Retry!");
                
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = client.execute(get, responseHandler);
                json = new JSONObject(response);

            } catch (HttpResponseException e) {
                e.printStackTrace();
                Log.e("ClientProtocol", "" + e);
                json.put("info", "Fields are invalid. Retry!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IO", "" + e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "" + e);
        }

        return json;
    }
    
    

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if (json.getBoolean("success")) {
                // launch the HomeActivity and close this one
            	
            	//CREACION DE LOS EVENTOS EN BD LOCAL: cuando hay internet chifar la bd local y chancar con la q acabo de obtener
            	            	
            	eventDao.deleteAll();
            	JSONArray jsonArray = json.getJSONArray("events");
        		JSONObject jsonObject;
        		
            	for(int i=0; i<=jsonArray.length(); i++){
            		jsonObject = (JSONObject) jsonArray.get(i);
        			int event_category = jsonObject.getInt("id_category");
        			Long event_id = jsonObject.getLong("id");
        			String event_name = jsonObject.getString("name");
        			String event_description = jsonObject.getString("description");
        			String event_privacy = jsonObject.getString("privacy_type");
        			Integer event_likes = 0;
        			Float event_rating = (float) 0.0;
        			String event_mood = "";
        			Integer event_place_id = jsonObject.getInt("place_id");
        			
        			Event jsonEvent = new Event(event_id, event_name, event_description, event_likes, event_rating, event_mood, null, null, event_category, event_privacy,event_place_id);
        			eventDao.insert(jsonEvent); 
        			jsonEvent=null;
            	}
            	
        	    Toast.makeText(context, "Evento listado exitosamente", Toast.LENGTH_LONG).show();
        	    
        	    
            }
        } catch (Exception e) {
        	//si no hay internet no hace nada
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }


	public List<Event> getAllEvents(EventDao eventDao_) {
		List<Event> lstEvents = eventDao_.loadAll();
		return lstEvents;
	}


}
