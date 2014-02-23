package com.cofradia.vao.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

import com.cofradia.vao.EventCreationDetails;
import com.cofradia.vao.MainActivity;
import com.cofradia.vao.R;
import com.cofradia.vao.R.layout;
import com.cofradia.vao.R.menu;

import de.greenrobot.daovao.event.Evento;

import DataAccess.DAEvent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class EventCreation extends Activity {
	
	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	private ImageButton mainImageEvent;
	private EditText eventNameEditText;
	private EditText eventDescriptionEditText;
	private Button eventFromDateBtn;
	private Button eventToDateBtn;
	private Button eventFromTimeBtn;
	private Button eventToTimeBtn;
//	private Event event = null;
	private String event_name = null;
	private String event_description = null;
	private String event_from_date = null;
	private String event_from_time = null;
	private String event_to_date = null;
	private String event_to_time = null;
	private Button current_button=null;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_creation);
		setElements();
		setButtonEvents();
		
		Calendar cal = Calendar.getInstance();
		
		/** Get the current date */
        int pYear = cal.get(Calendar.YEAR);
        int pMonth = cal.get(Calendar.MONTH);
        int pDay = cal.get(Calendar.DAY_OF_MONTH);
        
        /** Get the current time */
        int pHour= cal.get(Calendar.HOUR_OF_DAY);
        int pMinute = cal.get(Calendar.MINUTE);
 
        /** Display the current date in the TextView */
        updateDate(eventFromDateBtn, pDay, pMonth, pYear);
        updateDate(eventToDateBtn, pDay, pMonth, pYear);
        updateTime(eventFromTimeBtn, pHour, pMinute);
        updateTime(eventToTimeBtn, pHour, pMinute);
		
	}
	
	private void updateTime(View pDisplayTime, int pHour, int pMinute) {
		// 

        ((Button) pDisplayTime).setText(
                new StringBuilder()
                        .append(pHour).append(":")
                        .append(pMinute));
		
	}

	public void setElements(){
		mainImageEvent = (ImageButton) findViewById(R.id.imgBtnEventMainImage);
		eventNameEditText = (EditText) findViewById(R.id.edtTxEventName);
		eventDescriptionEditText = (EditText) findViewById(R.id.edtTxEventDescription);
		eventFromDateBtn = (Button) findViewById(R.id.btnFromDate);
		eventToDateBtn = (Button) findViewById(R.id.btnToDate);
		eventFromTimeBtn = (Button) findViewById(R.id.btnFromTime);
		eventToTimeBtn = (Button) findViewById(R.id.btnToTime);
	}
	
	private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    
                    updateDate(current_button, dayOfMonth, monthOfYear, year);
                }
            };
            
    /** Updates the date in the Button */
    private void updateDate(View pDisplayDate,Integer pDay,Integer pMonth,Integer pYear) { 	
        ((Button) pDisplayDate).setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(String.format("%02d",pDay)).append("/")
                    .append(String.format("%02d",(pMonth + 1))).append("/")
                    .append(String.format("%04d",pYear)));
    }
    
    /** Create a new dialog for date picker */
    @Override
    protected Dialog onCreateDialog(int id) {
    	Calendar cal = Calendar.getInstance();
        int pYear = cal.get(Calendar.YEAR);
        int pMonth = cal.get(Calendar.MONTH);
        int pDay = cal.get(Calendar.DAY_OF_MONTH);
        int pHour = cal.get(Calendar.HOUR_OF_DAY);
        int pMinute = cal.get(Calendar.MINUTE);;
        
        switch (id) {
        case DATE_DIALOG_ID:
        return new DatePickerDialog(this, 
                        pDateSetListener,
                        pYear, pMonth, pDay);
        case TIME_DIALOG_ID:
        return new TimePickerDialog(this,
        				new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               		 updateTime(current_button, selectedHour, selectedMinute);
            }
        },pHour,pMinute, true);//Yes 24 hour time       
        }
        return null;
        
    }
	
	public void setButtonEvents(){
		eventFromDateBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
            	current_button = (Button) v;
                showDialog(DATE_DIALOG_ID);
            }
        });
		eventToDateBtn.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				current_button = (Button) v;
				showDialog(DATE_DIALOG_ID);				
			}
		});
		
		eventFromTimeBtn.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	        	current_button = (Button) v;
	        	showDialog(TIME_DIALOG_ID);
	        }
	    });
		
		eventToTimeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	        	current_button = (Button) v;
	        	showDialog(TIME_DIALOG_ID);				
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_creation, menu);
		return true;
	}
	
	public void openGalleryOrCameraDialog(View view){
		final CharSequence[] items = { "Take Photo", "Choose from Library",
        "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, REQUEST_CAMERA);
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
        builder.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);

					bm = Bitmap.createScaledBitmap(bm, mainImageEvent.getWidth(), mainImageEvent.getHeight(), true);
					mainImageEvent.setImageBitmap(bm);

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == SELECT_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				mainImageEvent.setImageBitmap(bm);
			}
		}
	}
	
	public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	public void savePartialEvent(View view) throws ParseException{
		if (valid_fields()){
			if (valid_dates()){
				event_name = eventNameEditText.getText().toString();
				event_description = eventDescriptionEditText.getText().toString();
				event_from_date = eventFromDateBtn.getText().toString();
				event_from_time = eventFromTimeBtn.getText().toString();
				event_to_date = eventToDateBtn.getText().toString();
				event_to_time = eventToTimeBtn.getText().toString();
				
				
				Intent intent = new Intent(this, EventCreationDetails.class);
				intent.putExtra("event_name", event_name);
				intent.putExtra("event_description", event_description);
				intent.putExtra("event_from_date", event_from_date);
				intent.putExtra("event_to_time", event_to_date);
				intent.putExtra("event_from_time", event_from_time);
				intent.putExtra("event_to_time", event_to_time);
				startActivity(intent);
			}else{
				
			}
			
		}
	}
	
	public boolean valid_fields(){
	    if (eventNameEditText.length() == 0 || eventDescriptionEditText.length() == 0 ||
	    		eventFromDateBtn.getText().length() == 0 || eventFromTimeBtn.getText().length()==0 ||
	    		eventToDateBtn.getText().length() == 0 || eventToTimeBtn.getText().length()==0) {
	        // input fields are empty
	    	Toast.makeText(this, R.string.validation_fields_complete,
	            Toast.LENGTH_LONG).show();
	        return false;
	    } else {
	    	return true;
	    }
	}
	
	public boolean valid_dates() throws ParseException{
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dfTime = new SimpleDateFormat("hh:mm");
		if (dfDate.parse(eventFromDateBtn.getText().toString()).before(dfDate.parse(eventToDateBtn.getText().toString())))
			return true;
		else if (dfDate.parse(eventFromDateBtn.getText().toString()).equals(dfDate.parse(eventToDateBtn.getText().toString()))){
			if (dfTime.parse(eventFromTimeBtn.getText().toString()).before(dfTime.parse(eventToTimeBtn.getText().toString())))
				return true;
			else{
				Toast.makeText(this, R.string.validation_correct_dates,
			            Toast.LENGTH_LONG).show();
				return false;				
			}
		}else{
			Toast.makeText(this, R.string.validation_correct_dates,
		            Toast.LENGTH_LONG).show();	
			return false;	
		}
	}

}
