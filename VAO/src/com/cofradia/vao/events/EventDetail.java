package com.cofradia.vao.events;

import DataAccess.DAEvent;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import com.cofradia.vao.R;

public class EventDetail extends Activity {

	final Context context = this;
	private Switch swtEventConfirm;
	private CheckBox chkEventFacebookAttendance;
	public DAEvent event;
	
	public EventDetail() {
		super();
	}
	
	public EventDetail(int idEvent) {
		super();
		this.event =new DAEvent(idEvent);		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		
		//set comment data
		TextView txtEventComment = (TextView) findViewById(R.id.txtEventComment);
        txtEventComment.setText("Este es un comentario super hardcodeado xD!");
		
		
		

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

	public void setChkEventFacebookAttendance(CheckBox chkEventFacebookAttendance) {
		this.chkEventFacebookAttendance = chkEventFacebookAttendance;
	}

	public Context getContext() {
		return context;
	}
	
	

}
