package com.cofradia.vao;

import com.cofradia.vao.adapters.EventCategoriesAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.greenrobot.daovao.User;
import de.greenrobot.daovao.event.Event;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.IntentSender;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EventCreationDetails extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private String event_name = null;
	private String event_description = null;
	private String event_place_name = null;
	private Integer event_category = null;
	private Float event_place_latitude = null;
	private Float event_place_longitude= null;
	private String event_start_date = null;
	private String event_start_time = null;
	private String event_end_date = null;
	private String event_end_time = null;
	private LocationClient mLocationClient;
    private Location mCurrentLocation;
    private GoogleMap map;
    private Spinner eventCategorySpinner;
    private EditText eventPlaceNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_creation_details);
		// Show the Up button in the action bar.
		get_event_parameters();
		fill_spinner();
		setupActionBar();
	}
	
	 @Override
	    protected void onResume() {
	        super.onResume();
	        setUpMapIfNeeded();
	        setUpLocationClientIfNeeded();
	        mLocationClient.connect();
	    }
	
	private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            Toast.makeText(getApplicationContext(), "Waiting for location",
                    Toast.LENGTH_SHORT).show();
            mLocationClient = new LocationClient(getApplicationContext(), this, this);
        }
		
	}

	private void setUpMapIfNeeded() {
	      // Do a null check to confirm that we have not already instantiated the
        // map.
        if (map == null) {
        	
            map = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.event_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (map == null) {
                Toast.makeText(this, "Google maps not available",
                        Toast.LENGTH_LONG).show();
            }
        }
		
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

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available
             */
            Log.e("Home", Integer.toString(connectionResult.getErrorCode()));
        }
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		mCurrentLocation = mLocationClient.getLastLocation();
        if (mCurrentLocation != null) {
            Toast.makeText(getApplicationContext(), "Found!",
                    Toast.LENGTH_SHORT).show();
            centerInLoc();
        }
		
	}

	private void centerInLoc() {
		LatLng myLaLn = new LatLng(mCurrentLocation.getLatitude(),
                mCurrentLocation.getLongitude());
		
		event_place_latitude = (float) myLaLn.latitude;
		event_place_longitude = (float) myLaLn.longitude;
		
        CameraPosition camPos = new CameraPosition.Builder().target(myLaLn)
                .zoom(15).bearing(45).tilt(70).build();

        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);

        MarkerOptions markerOpts = new MarkerOptions().position(myLaLn).title(
                "Lugar del evento").draggable(true);
        
        map.addMarker(markerOpts);
        map.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMarkerDragEnd(Marker marker) {
				// TODO Auto-generated method stub
				event_place_latitude = (float) marker.getPosition().latitude;
				event_place_latitude = (float) marker.getPosition().longitude;
				Log.d("new position", "LAT: "+event_place_latitude+"//LONG: "+event_place_longitude);
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}
	
	public void saveTotalEvent(){
        if (valid_fields()) {
            // input fields are empty
            
        }
	}
	
	public boolean valid_fields(){
	    if (eventPlaceNameEditText.length() == 0 || event_place_latitude==null || event_place_longitude==null) {
	        // input fields are empty
	    	Toast.makeText(this, R.string.validation_fields_complete,
	            Toast.LENGTH_LONG).show();
	        return false;
	    } else {
	    	return true;
	    }
	}

    
    private void sendEvent(User currentUser){
    
    	EventTask eventTask = new EventTask(event_name, event_description, event_place_name, event_start_date, event_end_date, event_start_time, event_end_time, event_place_latitude, event_place_longitude, event_category, getApplicationContext());
    	eventTask.doEventCreation();
	    	
	 //TODO: after "dologin" call
	 //currentUser.setActiveSession(true);
   }
	
	

}
