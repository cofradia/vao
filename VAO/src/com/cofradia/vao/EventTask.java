package com.cofradia.vao;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.cofradia.vao.events.EventCreation;
import com.cofradia.vao.events.EventList;
import com.cofradia.vao.tasks.UrlJsonAsyncTask;
import com.cofradia.vao.util.DateFormatter;

import de.greenrobot.daovao.user.*;
import de.greenrobot.daovao.event.*;
import de.greenrobot.daovao.event.DaoMaster;
public class EventTask extends UrlJsonAsyncTask{
	
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

	public EventTask(String eventName, String eventDescription, String eventPlaceName, 
						String eventStartDate, String eventEndDate,
						String eventStartTime, String eventEndTime,
						Float eventPlaceLatitude, Float eventPlaceLongitude,
						Integer eventCategory, String eventPrivacy, Context context) {
		super(context);
		db_init(context);
		this.event_name = eventName;
		this.event_description = eventDescription;
		this.event_place_name = eventPlaceName;
		this.event_start_date = eventStartDate;
		this.event_start_time = eventStartTime;
		this.event_end_date = eventEndDate;
		this.event_end_time = eventEndTime;
		this.event_place_latitude = eventPlaceLatitude;
		this.event_place_longitude = eventPlaceLongitude;
		this.event_category = eventCategory;
		this.event_privacy = eventPrivacy;
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

	public void doEventCreation(){
    	this.setMessageLoading("Creating Event...");
    	this.execute(EVENT_API_ENDPOINT_URL);
        //TODO: modify returning value to handle exceptions or fail cases
    }
	
    @Override
    protected JSONObject doInBackground(String... urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urls[0]);
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
                // add the user email and password to
                // the params
                
                eventObj.put("event_name", event_name);
                eventObj.put("event_description", event_description);
                eventObj.put("event_category", event_category);
                eventObj.put("event_place_name", event_place_name);
                eventObj.put("event_place_latitude", event_place_latitude);
                eventObj.put("event_place_longitude", event_place_longitude);
                eventObj.put("event_privacy", event_privacy);
                eventObj.put("event_start_date", DateFormatter.getTimeStamp(event_start_date, event_start_time));
                eventObj.put("event_end_date", DateFormatter.getTimeStamp(event_end_date, event_end_time));
                holder.put("event", eventObj);
                holder.put("auth_token", mPreferences.getString("AuthToken", null));
                StringEntity se = new StringEntity(holder.toString());
                post.setEntity(se);

                // setup the request headers
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Authorization", "Token token=" + mPreferences.getString("AuthToken", ""));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = client.execute(post, responseHandler);
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
            	addEvent(json);
                Intent intent = new Intent(((EventCreationDetails)context)	, EventList.class);
        	    ((EventCreationDetails)context).startActivity(intent);
        	    //Guardar: fecha evento, evento, usuario
        	    Toast.makeText(context, "Evento creado exitosamente", Toast.LENGTH_LONG).show();
        	    
        	    
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }

	private void addEvent(JSONObject json) {
		Log.d("JASON", json.toString());
		try {
			JSONObject event_object = json.getJSONObject("event_obj");
			int event_category = event_object.getInt("id_category");
			Long event_id = event_object.getLong("id");
			String event_name = event_object.getString("name");
			String event_description = event_object.getString("description");
			String event_privacy = event_object.getString("privacy_type");
			Integer event_likes = 0;
			Float event_rating = (float) 0.0;
			String event_mood = "";
			Integer event_place_id = event_object.getInt("place_id");
			
			Event event = new Event(event_id, event_name, event_description, event_likes, event_rating, event_mood, null, null, event_category, event_privacy,event_place_id);
			eventDao.insert(event); //CORREGIR INSERCION EN BD LOCAL
			Log.d("Evento en BD local", "Inserted new note, ID: " + event.getId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
