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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.cofradia.vao.events.EventCreation;
import com.cofradia.vao.events.EventList;
import com.cofradia.vao.tasks.UrlJsonAsyncTask;

public class EventTask extends UrlJsonAsyncTask{
	
	String event_name;
	String event_description;
	String event_place_name;
	String event_start_date;
	String event_start_time;
	String event_end_date;
	String event_end_time;
	SharedPreferences mPreferences;

	public EventTask(String eventName, String eventDescription, String eventPlaceName, Context context) {
		super(context);
		this.event_name = eventName;
		this.event_description = eventDescription;
		this.event_place_name = eventPlaceName;
		// TODO Auto-generated constructor stub
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
                eventObj.put("event_category", event_description);
                eventObj.put("event_place_name", event_place_name);
                eventObj.put("event_place_latitude", -12.0453);
                eventObj.put("event_place_longitude", -77.0311);
                eventObj.put("event_start_date", event_start_date);
                eventObj.put("event_end_date", event_end_date);
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
                json.put("info", "Email and/or password are invalid. Retry!");
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
                Intent intent = new Intent(((EventCreation)context)	, EventList.class);
        	    ((EventCreation)context).startActivity(intent);
        	    //Guardar: fecha evento, evento, usuario
        	    
        	    
            }
            Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        	Log.e("RESULT!!!", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }

}
