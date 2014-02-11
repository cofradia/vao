package com.cofradia.vao;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.cofradia.vao.events.EventDetail;
import com.cofradia.vao.events.EventList;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import tasks.*;
import com.cofradia.vao.*;
import com.facebook.*;
import com.facebook.model.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

/**
 * @author Usuario
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
    private SharedPreferences mPreferences;
    private String emailText;
    private String passwordText;
    //TODO: use GreenDao user class
    private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
    private Button btnLoginFB;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        btnLoginFB = (Button)findViewById(R.id.btnLoginFB);
      //TODO: move this methos to a Session Manager
        setupSession(savedInstanceState);
        
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    private void setupSession(Bundle savedInstanceState) {
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }

        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

 
    public void doRegularLogin(View view){
    	EditText userEmailField = (EditText) findViewById(R.id.txtUsuario);
        emailText = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.txtPassword);
        passwordText = userPasswordField.getText().toString();

        if (emailText.length() == 0 || passwordText.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                Toast.LENGTH_LONG).show();
            return;
        } else {
            LoginTask loginTask = new LoginTask(emailText,passwordText, mPreferences , MainActivity.this);
            loginTask.doLogin(); 
        }
    }
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
	
	@Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
	
    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
        	Log.d("FB Login: ", "Session is already opened --  KATHERINE WI WI");
        	
        	//TODO: delete session.closeAndClearTokenInformation, just for test purpose
    	    //session.closeAndClearTokenInformation();
    	    
    	    //Redirect to eventList window
        	//TODO: add server side && greenDao (create user, login server side)
        	//Add user fbtoken
            LoginTask loginTask = new LoginTask("user1@example.com","secret123", mPreferences , MainActivity.this);
            loginTask.doLogin(); 
        } else {
            btnLoginFB.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickFBLogin(); }
            });
        }
    }

    private void onClickFBLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
        	Log.d("ONCLICKLOGIN: " ,"nor closed nor opened session");
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        	
        	Log.d("FBLogin: ", "token: " + session.getAccessToken());
            updateView();
           
        }
    }

}