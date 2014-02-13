package com.cofradia.vao.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.cofradia.vao.EventCreationDetails;
import com.cofradia.vao.MainActivity;
import com.cofradia.vao.R;
import com.cofradia.vao.R.layout;
import com.cofradia.vao.R.menu;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EventCreation extends Activity {
	
	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	private ImageButton mainImageEvent;
	private EditText eventNameEditText;
	private EditText eventDescriptionEditText;
	private EditText eventPlaceEditText;
//	private Event event = null;
	private String event_name = null;
	private String event_description = null;
	private String event_place = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_creation);
		mainImageEvent = (ImageButton) findViewById(R.id.imgBtnEventMainImage);
		eventNameEditText = (EditText) findViewById(R.id.edtTxEventName);
		eventDescriptionEditText = (EditText) findViewById(R.id.edtTxEventDescription);
		eventPlaceEditText = (EditText) findViewById(R.id.edtTxEventPlace);
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

					// bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
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
	
	public void savePartialEvent(View view){
		if (valid_fields()){
			event_name = eventNameEditText.getText().toString();
			event_description = eventDescriptionEditText.getText().toString();
			event_place = eventPlaceEditText.getText().toString();
			
			Intent intent = new Intent(this, EventCreationDetails.class);
			intent.putExtra("event_name", event_name);
			intent.putExtra("event_description", event_description);
			intent.putExtra("event_place", event_place);
            startActivity(intent);
			
		}
	}
	
	public boolean valid_fields(){
	    if (eventNameEditText.length() == 0 || eventDescriptionEditText.length() == 0 || eventPlaceEditText.length()==0) {
	        // input fields are empty
	    	Toast.makeText(this, "Please complete all the fields",
	            Toast.LENGTH_LONG).show();
	        return false;
	    } else {
	    	return true;
	    }
	}

}
