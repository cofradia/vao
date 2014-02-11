package com.cofradia.vao.events;

import java.util.ArrayList;

import com.cofradia.vao.MainActivity;
import com.cofradia.vao.R;
import com.cofradia.vao.R.layout;
import com.cofradia.vao.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
		ListView searchResult = (ListView)findViewById(R.id.listEventSearchResult);
		
		//TODO: modify to use Event
		// Instanciating an array list (you don't need to do this, 
        // you already have yours).
        ArrayList<String> your_array_list = new ArrayList<String>();
        your_array_list.add("Mi primer evento");
        your_array_list.add("Mi segundo evento");

        // This is the array adapter, it takes the context of the activity as a 
        // first parameter, the type of list view as a second parameter and your 
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                your_array_list );

        searchResult.setAdapter(arrayAdapter); 
        
        searchResult.setClickable(true);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//TODO: this is not ok, it is not a good idea to open a new activity each time we select an event, we should Re use an pre created one
				Intent eventDetails = new Intent (EventList.this, EventDetail.class);
				startActivity(eventDetails);
				/*Object o = searchResult.getItemAtPosition(position);
                String str=(String)o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();*/
			}
        });
		return true;
	}

}
