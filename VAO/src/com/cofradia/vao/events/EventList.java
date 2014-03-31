package com.cofradia.vao.events;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cofradia.vao.EventListTask;
import com.cofradia.vao.R;
import com.cofradia.vao.listeners.EventListEndlessScrollListener;

import de.greenrobot.daovao.event.DaoMaster;
import de.greenrobot.daovao.event.DaoSession;
import de.greenrobot.daovao.event.Event;
import de.greenrobot.daovao.event.EventDao;

public class EventList extends Activity {

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private EventDao eventDao;
	private Event event;
	private String event_name = null;
	private String event_description = null;
	private String event_place_name = null;
	private Integer event_category = null;
	private String event_category_description = null;
	private Float event_place_latitude = null;
	private Float event_place_longitude = null;
	private String event_start_date = null;
	private String event_start_time = null;
	private String event_end_date = null;
	private String event_end_time = null;
	private List<Event> lstEvent;
	private String strlstEvent;

	private void setEventSession(Context context) {
		de.greenrobot.daovao.event.DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
				context, "vao-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		eventDao = daoSession.getEventDao();
		event = new Event();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setEventSession(this);
		setContentView(R.layout.activity_event_list);
		ListView searchResult = (ListView) findViewById(R.id.listEventSearchResult);
		// TODO: modify to use Event

		//add endless scroll listener
		searchResult.setOnScrollListener(new EventListEndlessScrollListener(this));
		lstEvent = event.getAllEvents(eventDao);
		
		// This is the array adapter, it takes the context of the activity as a
		// first parameter, the type of list view as a second parameter and your
		// array as a third parameter.
		
		ArrayAdapter<Event> arrayAdapter = new ArrayAdapter<Event>(this,
				android.R.layout.simple_list_item_1, lstEvent);

		searchResult.setAdapter(arrayAdapter);

		searchResult.setClickable(true);
		searchResult
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO: this is not ok, it is not a good idea to open a
						// new activity each time we select an event, we should
						// Re use an pre created one
						Intent eventDetails = new Intent(EventList.this,
								EventDetail.class);
						Event evento = (Event) parent
								.getItemAtPosition(position);
						Bundle params = new Bundle();

						eventDetails.putExtra("event_id", evento.getId());
						startActivity(eventDetails);
						/*
						 * Object o = searchResult.getItemAtPosition(position);
						 * String str=(String)o;//As you are using Default
						 * String Adapter
						 * Toast.makeText(getApplicationContext(),
						 * str,Toast.LENGTH_SHORT).show();
						 */
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.event_list, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.event_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("Action bar", " Listening to item: " + item.getTitle());
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.itemAddEvent:
			openCreateEvent();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// TODO: modify this method to open CreateEvent window
	public void openCreateEvent() {
		Intent intent = new Intent(EventList.this, EventCreation.class);
		startActivity(intent);
	}


}
