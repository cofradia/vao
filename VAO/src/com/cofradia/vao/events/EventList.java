package com.cofradia.vao.events;

import com.cofradia.vao.R;
import com.cofradia.vao.R.layout;
import com.cofradia.vao.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EventList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

}
