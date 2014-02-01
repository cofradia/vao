package com.cofradia.vao.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.cofradia.vao.R;

public class EventDetail extends Activity {

	final Context context = this;
	private Switch swtEventConfirm;

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
						// TODO Auto-generated method stub

						final Dialog dlgAttendance = new Dialog(context);
						dlgAttendance
								.setContentView(R.layout.dialog_event_attendance);
						dlgAttendance.setTitle("Confirmar asistencia");

						// set the custom dialog components - text, image and
						// button
						// TextView text = (TextView)
						// dlgAttendance.findViewById(R.id.txtEventAttendanceConfirmText);
						// CheckBox chkFacebookAttendance = (CheckBox)
						// dlgAttendance.findViewById(R.id.chkEventFacebookAttendance);
						Button btnConfirmAttendance = (Button) dlgAttendance
								.findViewById(R.id.btnEventConfirmAttendace);
						Button btnCancelConfirm = (Button) dlgAttendance
								.findViewById(R.id.btnEventCancelAttendance);

						// if button is clicked, close the custom dialog
						btnCancelConfirm
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										dlgAttendance.dismiss();
									}
								});

						// if button is clicked, close the custom dialog
						btnConfirmAttendance
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										System.out.print("confirm");
										dlgAttendance.dismiss();
									}
								});
						
						if (isChecked) {
							dlgAttendance.show();
						}
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}

}
