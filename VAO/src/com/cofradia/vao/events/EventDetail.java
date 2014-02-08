package com.cofradia.vao.events;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.cofradia.vao.R;

public class EventDetail extends Activity {

	final Context context = this;
	private Switch swtEventConfirm;
	private CheckBox chkEventFacebookAttendance;
	public Event event=new Event();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);

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
/*		chkEventFacebookAttendance
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							Toast.makeText(context, "fb!", Toast.LENGTH_LONG)
									.show();
						} else {

						}
					}
				});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}

}
