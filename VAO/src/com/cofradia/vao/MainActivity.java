package com.cofradia.vao;

import com.cofradia.vao.events.EventDetail;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.facebook.*;
import com.facebook.model.*;
import android.widget.TextView;
import android.content.Intent;

/**
 * @author Usuario
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    public void openDetails(View view){
        Intent intent = new Intent(this, EventDetail.class);
        startActivity(intent);

    }

//    //FB Login
    public void doLoginFB(View view){
        // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

          // callback when session changes state
          @Override
          public void call(Session session, SessionState state, Exception exception) {

          }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}