package com.cofradia.vao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.cofradia.vao.events.EventList;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

/**
 * @author Usuario
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String ADMINUSER = "admin@vao.com";
    public final static String ADMINPWD = "secret123";
    
    private static final String TAG = "MainActivity";
    
    private SharedPreferences mPreferences;
    private String fb_auth_token = null;
    private String emailText;
    private String passwordText;
    private LoginButton loginButton;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
//    private FacebookAPI fbApi = new FacebookAPI();
    private Session  fbSession ;
    
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
    }    

    private GraphUser user;
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "user_status", "email"));
        boolean userLoggedIn = user_logged_in(savedInstanceState);
        if (!userLoggedIn) {
        	setContentView(R.layout.main);
        	setViewListeners();
        }

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
            	
            	Session.OpenRequest request = new Session.OpenRequest(this);
//            	request.setPermissions(Arrays.asList("public_profile", "user_birthday", "user_status", "email"));
            	request.setCallback(statusCallback);
                session.openForRead(request);
            }
            updateView();
        }
//        fbSession = fbApi.setupFBSession(savedInstanceState, this);
        // Check whether user is already logged in and redirects to next View
    }
    
    private void setViewListeners(){
//        loginButton.setReadPermissions(Arrays.asList("public_profile"));
//        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "user_status", "email"));
////        loginButton.setPublishPermissions(Arrays.asList("create_event"));
//
//        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
//            @Override
//            public void onUserInfoFetched(GraphUser user) {
//            	
//                MainActivity.this.user = user;
//                if (user!=null){
//                	
//                	Log.d("FBLogin", "usuario existente! "+user);
//                	Log.d("FBLogin", " id usuario: "+ user.getId());
//                	Log.d("FBLogin", " userName usuario: "+ user.getUsername());
//                	Log.d("FBLogin", " name usuario: "+ user.getName());
//                	Object email = user.asMap().get("email");
//                	Log.d("FBLogin", "email: " + email);
//                	Log.d("VAO Login", "doing regular login for fb user");
//                	//TODO: AUTH FB SERVER MISSING 
//
//                	_doRegularLogin(ADMINUSER, ADMINPWD);
//                }
//                else{
//                	Log.d("FBLogin", "usuario nulo :( : " );
//                    //Toast.makeText(MainActivity.this, "No se pudo reealizar el loggeo con FB.", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }
    
    private boolean user_logged_in(Bundle savedInstanceState) {
    	mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        String first = mPreferences.getString("AuthToken", null);
        if((first != null)){
        	
            Intent i = new Intent(this, EventList.class);
             startActivity(i);
             finish();
        }
    	return false;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    public void doRegularLogin(View view){
    	EditText userEmailField = (EditText) findViewById(R.id.txtUsuario);
        EditText userPasswordField = (EditText) findViewById(R.id.txtPassword);
        emailText = ADMINUSER;
        passwordText= ADMINPWD;
//        emailText = userEmailField.getText().toString();
//        passwordText = userPasswordField.getText().toString();

        if (emailText.length() == 0 || passwordText.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                Toast.LENGTH_LONG).show();
            return;
        } else {

        	_doRegularLogin(emailText, passwordText, null);
        }
    }
    
    private void _doRegularLogin(String user_name, String password, String fb_auth_token){
	 LoginTask loginTask = new LoginTask(user_name, password, mPreferences , fb_auth_token, MainActivity.this);
	 loginTask.doLogin();
	    	
	 //TODO: after "dologin" call
	 //currentUser.setActiveSession(true);
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
    	loginButton = (LoginButton) findViewById(R.id.login_button);
//    	loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "user_status", "email"));
        Session session = Session.getActiveSession();
        if (!session.isOpened()) {
        	loginButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
        else{
        	
          if (!session.isOpened() && !session.isClosed()) {
              session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
          } else {
        	  Log.d("FBLogin", "usuario existente! "+ session);
        	  _doRegularLogin(ADMINUSER, ADMINPWD, session.getAccessToken()+"");
//              Session.openActiveSession(this, true, statusCallback);
          }
        	
//          MainActivity.this.user = user;
//          if (user!=null){
//          	
//          	Log.d("FBLogin", "usuario existente! "+user);
//          	Log.d("FBLogin", " id usuario: "+ user.getId());
//          	Log.d("FBLogin", " userName usuario: "+ user.getUsername());
//          	Log.d("FBLogin", " name usuario: "+ user.getName());
//          	Object email = user.asMap().get("email");
//          	Log.d("FBLogin", "email: " + email);
//          	Log.d("VAO Login", "doing regular login for fb user");
//          	//TODO: AUTH FB SERVER MISSING 
//
//          	_doRegularLogin(ADMINUSER, ADMINPWD);
//          }
//          else{
//          	Log.d("FBLogin", "usuario nulo :( : " );
//              //Toast.makeText(MainActivity.this, "No se pudo reealizar el loggeo con FB.", Toast.LENGTH_LONG).show();
//
//          }
        	
        }
        	
    }
    
    private void onClickLogin() {
//      Session session = Session.getActiveSession();
//      if (!session.isOpened() && !session.isClosed()) {
//          session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
//      } else {
//          Session.openActiveSession(this, true, statusCallback);
//      }
  	
  	Session session = Session.getActiveSession();
  	if (session == null) {
  	    Session.openActiveSession(this, true, statusCallback);
  	} else if (!session.isOpened()) {
    	Session.OpenRequest request = new Session.OpenRequest(this);
//    	request.setPermissions(Arrays.asList("public_profile", "user_birthday", "user_status", "email"));
    	request.setCallback(statusCallback);
        session.openForRead(request);  	}
  }

}