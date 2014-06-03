package com.cofradia.vao;

import java.io.IOException;

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

import com.cofradia.vao.entities.Category;
import com.cofradia.vao.entities.Event;
import com.cofradia.vao.entities.Place;
import com.cofradia.vao.entities.User;
import com.cofradia.vao.tasks.UrlJsonAsyncTask;

public class EventListTask extends UrlJsonAsyncTask {

	private final static String EVENT_API_ENDPOINT_URL = "http://vao-ws.herokuapp.com/v1/events.json";
	private SQLiteDatabase dbEvent;
	private SQLiteDatabase dbPlace;

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
	Context context;
	int count;

	public EventListTask(Context context) {
		super(context);
		this.context = context;
		this.mPreferences = context.getSharedPreferences("CurrentUser",
				android.content.Context.MODE_PRIVATE);
		// TODO Auto-generated constructor stub
	}

	public void doEventList(int i, boolean first_time) {
		if (first_time) {
			this.setMessageLoading("Perate un toque, estamos cargando los últimos eventos...");
			this.execute(EVENT_API_ENDPOINT_URL);
		}// TODO: modify returning value to handle exceptions or fail cases
	}

	@Override
	protected JSONObject doInBackground(String... urls) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(urls[0]);
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

				// CREACION DE LOS EVENTOS EN BD LOCAL: cuando hay internet
				// chifar la bd local y chancar con la q acabo de obtener
				Place.deleteAll(Place.class);
				JSONArray jsonArrayEvents = json.getJSONArray("events");
				Event.deleteAll(Event.class);
				JSONArray jsonArrayPlaces = json.getJSONArray("places");

				JSONObject jsonObject;
				JSONObject placeObject;

				for (int i = 0; i < jsonArrayPlaces.length(); i++) {
					placeObject = (JSONObject) jsonArrayPlaces.get(i);
					Double place_latitude = (!placeObject.isNull("latitude") ? placeObject
							.getDouble("latitude") : 0);
					Double place_longitude = (!placeObject.isNull("longitude") ? placeObject
							.getDouble("longitude") : 0);
					Place place = new Place(this.context,
							placeObject.getLong("id"),
							placeObject.getString("name"), place_latitude,
							place_longitude);
					place.save();
					place = null;
				}

				for (int i = 0; i < jsonArrayEvents.length(); i++) {
					jsonObject = (JSONObject) jsonArrayEvents.get(i);
					Long event_id = jsonObject.getLong("id");
					String event_name = jsonObject.getString("name");
					String event_description = jsonObject
							.getString("description");
					String event_privacy = jsonObject.getString("privacy_type");
					Integer event_likes = 0;
					Double event_rating = (Double) 0.0;
					String event_mood = "";

					Place event_place = Place.findById(Place.class,
							jsonObject.getLong("place_id"));

					User event_user = new User(this.context);
					Category event_category = new Category(this.context,
							jsonObject.getLong("id_category"), "");
					event_category.save();

					Event jsonEvent = new Event(this.context, event_id,
							event_name, event_description, event_likes,
							event_rating, event_mood, null, null,
							event_category, event_privacy, event_place,
							event_user);
					jsonEvent.save();
					jsonEvent = null;
				}

				jsonObject = null;

				Toast.makeText(context, "Evento listado exitosamente",
						Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
			// si no hay internet no hace nada
			// Toast.makeText(context, "CATCH: " + e.getMessage(),
			// Toast.LENGTH_LONG).show();
			Log.d("onPostExecute CATCH: ", "dunno D:");
			// Log.d("onPostExecute CATCH: ", e.getMessage());
		} finally {
			super.onPostExecute(json);
		}
	}

}
