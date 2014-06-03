package com.cofradia.vao.events;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cofradia.vao.EventListTask;
import com.cofradia.vao.R;
import com.cofradia.vao.adapters.EventListAdapter;
import com.cofradia.vao.entities.Event;
import com.cofradia.vao.entities.Place;

public class EventList extends Activity implements OnItemClickListener {

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
	private List<Place> lstPlace;
	private ListView listEventView;
	private String strlstEvent;
	private boolean first_time = true;

	String[] event_names;
	TypedArray event_images;
	String[] event_places;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		// add endless scroll listener
		lstEvent = Event.listAll(Event.class);// obtengo una lista de todos los
												// eventos de la bd
		lstPlace = Place.listAll(Place.class);// obtengo una lista de todos los
												// eventos de la bd

		// This is the array adapter, it takes the context of the activity as a
		// first parameter, the type of list view as a second parameter and your
		// array as a third parameter.

		if (lstEvent.size() == 0) {
			EventListTask eventListTask = new EventListTask(this);
			eventListTask.doEventList(1, true);
		}
		lstEvent = Event.listAll(Event.class);
		lstPlace = Place.listAll(Place.class); 

		listEventView = (ListView) findViewById(R.id.listEventListView);
		EventListAdapter eventListAdapter = new EventListAdapter(this, lstEvent);
		listEventView.setAdapter(eventListAdapter);
		listEventView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent eventDetails = new Intent(EventList.this, EventDetail.class);
		Event evento = (Event) parent.getItemAtPosition(position);

		eventDetails.putExtra("event_id", evento.getId());
		startActivity(eventDetails);

	}

}
