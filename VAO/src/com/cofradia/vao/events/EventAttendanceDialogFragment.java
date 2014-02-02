package com.cofradia.vao.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.cofradia.vao.R;

public class EventAttendanceDialogFragment extends DialogFragment{
	
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        AlertDialog.Builder EventDialogBuilder = new AlertDialog.Builder(getActivity());
	        LayoutInflater EventAttendanceInflater = getActivity().getLayoutInflater();
	        EventDialogBuilder.setView(EventAttendanceInflater.inflate(R.layout.dialog_event_attendance,null));

	        //null should be your on click listener
	        EventDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	Toast.makeText(getActivity(), 
	            			"holi", Toast.LENGTH_LONG).show();
	            	
	            }
	        });

	        EventDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });


	        return EventDialogBuilder.create();
	    }

}
