package com.cofradia.vao.events;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.cofradia.vao.R;
import com.cofradia.vao.entities.Event;
import com.cofradia.vao.entities.Place;

public class EventDetail extends Activity {

	final Context context = this;
	// green dao
	private SQLiteDatabase dbEvent;
	private SQLiteDatabase dbPlace;

	private Event event;
	private Place place;

	// Basic Info
	private ImageView imgEvent;
	private TextView txtEventTitle;
	private TextView txtEventTime;
	private TextView txtEventPlace;
	private ImageButton imgEventFav;
	private ImageView imgEventAlgo;
	private ImageView imgIsFbEvent;
	private TextView txtEventDescriptionLabel;
	private TextView txtEventDescription;

	// Mood
	private TextView txtEventMoodLabel;
	private TextView txtEventMood;

	// FB Info
	private Switch swtEventConfirm;
	private CheckBox chkEventFacebookAttendance;
	private TextView txtEventAttendanceConfirmText;

	// Comments
	private TextView txtEventComment;
	private TextView txtEventCommentLabel;
	private ImageView imgEventCommentUserImage;

	public EventDetail() {
		super();
	}

	public EventDetail(int idEvent) {
		super();
	}

	private void setEventSession(Context context) {
		event = new Event(context);
		place = new Place(context);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		setEventSession(this);

		// get event data
		event = event.findById(Event.class, this.getIntent().getExtras()
				.getLong("event_id"));
		// set event title
		this.txtEventTitle = (TextView) findViewById(R.id.txtEventTitle);
		this.txtEventTitle.setText(event.getName());

		// set event description
		this.txtEventDescription = (TextView) findViewById(R.id.txtEventDescription);
		this.txtEventDescription.setText(event.getDescription());

		// set event place
		this.txtEventPlace = (TextView) findViewById(R.id.txtEventPlace);

		place = place.findById(Place.class, event.getPlace().getId());

		this.txtEventPlace.setText(place.getName());

		// set event time
		this.txtEventTime = (TextView) findViewById(R.id.txtEventTime);

		// set comment data
		this.txtEventComment = (TextView) findViewById(R.id.txtEventComment);
		this.txtEventComment
				.setText("Este es un comentario super hardcodeado xD!");

		swtEventConfirm = (Switch) findViewById(R.id.swtEvent);

		// Add a switch-confirm listener
		swtEventConfirm
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							// TODO Auto-generated method stub
							EventAttendanceDialogFragment attendanceDialogFragment = new EventAttendanceDialogFragment();
							attendanceDialogFragment.show(getFragmentManager(),
									"EventAttendanceDialog");
							if (attendanceDialogFragment.getDialog() != null)
								attendanceDialogFragment.getDialog()
										.setCanceledOnTouchOutside(true);
						} else {

						}

					}

				});

		chkEventFacebookAttendance = (CheckBox) findViewById(R.id.chkEventFacebookAttendance);

		// Add a checkbox listener
		/*
		 * chkEventFacebookAttendance .setOnCheckedChangeListener(new
		 * OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { // TODO Auto-generated method stub if
		 * (isChecked) { Toast.makeText(context, "fb!", Toast.LENGTH_LONG)
		 * .show(); } else {
		 * 
		 * } } });
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}

	public Switch getSwtEventConfirm() {
		return swtEventConfirm;
	}

	public void setSwtEventConfirm(Switch swtEventConfirm) {
		this.swtEventConfirm = swtEventConfirm;
	}

	public CheckBox getChkEventFacebookAttendance() {
		return chkEventFacebookAttendance;
	}

	public void setChkEventFacebookAttendance(
			CheckBox chkEventFacebookAttendance) {
		this.chkEventFacebookAttendance = chkEventFacebookAttendance;
	}

	public Context getContext() {
		return context;
	}

}
