package com.cofradia.vao;

import com.cofradia.vao.adapters.EventCategoriesAdapter;

import de.greenrobot.daovao.event.Evento;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EventCreationDetails extends Activity {
	
	private String event_name = null;
	private String event_description = null;
	private String event_place = null;
	private Integer event_category = null;
	private String event_start_date = null;
	private String event_start_time = null;
	private String event_end_date = null;
	private String event_end_time = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_creation_details);
		// Show the Up button in the action bar.
		get_event_parameters();
		fill_spinner();
		setupActionBar();
	}
	
	public void get_event_parameters(){
		event_name =getIntent().getExtras().getString("event_name");
		event_description =getIntent().getExtras().getString("event_description");
		event_start_date =getIntent().getExtras().getString("event_from_date");
		event_start_time =getIntent().getExtras().getString("event_from_time");
		event_end_date =getIntent().getExtras().getString("event_to_date");
		event_end_time =getIntent().getExtras().getString("event_to_time");
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_creation_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void fill_spinner(){
		Spinner spinner = (Spinner) findViewById(R.id.spnEventCategory);
		
        final EventCategoriesAdapter items[] = new EventCategoriesAdapter[3];
        
        Resources r = getResources();
        String [] keys = r.getStringArray(R.array.event_categories_keys);
        int [] values = r.getIntArray(R.array.event_categories_values);
        
        items[0] = new EventCategoriesAdapter(keys[0],values[0]);
        items[1] = new EventCategoriesAdapter(keys[1],values[1]);
        items[2] = new EventCategoriesAdapter(keys[2],values[2]);
        ArrayAdapter<EventCategoriesAdapter> categoriesAdapter = 
            new ArrayAdapter<EventCategoriesAdapter>( 
                this,
                android.R.layout.simple_spinner_item,
                items );
		
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(categoriesAdapter);
		
		spinner.setOnItemSelectedListener(
	            new AdapterView.OnItemSelectedListener() {
	                public void onNothingSelected(AdapterView<?> parent) {
	                }

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						
						event_category = items[position].getValue();
						Log.d("category selected", event_category.toString());
						// TODO Auto-generated method stub
						
					}
	            }
	        );
	}

}
