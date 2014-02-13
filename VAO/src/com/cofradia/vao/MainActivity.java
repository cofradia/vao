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
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import com.cofradia.vao.tasks.*;

import com.cofradia.vao.*;
import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;

import de.greenrobot.daovao.DaoMaster;
import de.greenrobot.daovao.DaoMaster.DevOpenHelper;
import de.greenrobot.daovao.DaoSession;
import de.greenrobot.daovao.User;
import de.greenrobot.daovao.UserDao;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Usuario
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
    private SharedPreferences mPreferences;
    private String emailText;
    private String passwordText;
    
    private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
    private LoginButton loginButton;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    //GreenDao
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private User currentUser;
    private GraphUser user;
    private UiLifecycleHelper uiHelper;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        
        //Initializing Device BD
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "vao-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
        currentUser = new User();
        
        //TODO: move this methods to a Session Manager
        setupFBSession(savedInstanceState);
        
        //Test
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
                if (user!=null){
                	
                	Log.d("FBLogin", "usuario existente! ");
                	Log.d("FBLogin", " id usuario: "+ user.getId());
                	Log.d("FBLogin", " userName usuario: "+ user.getUsername());
                	Log.d("FBLogin", " name usuario: "+ user.getName());
                	Log.d("FBLogin", "email? " + user.asMap().get("email"));
                }
                else
                	Log.d("FBLogin", "usuario nulo :(");
             //   updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                //handlePendingAction();
            }
        });
        
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        Log.d("on SessionStateChange", "session changed");

        if (session.isOpened()) {
	      	Log.d("FB Login: ", "Session is already opened, redirecting to eventList");
	      	//TODO: delete session.closeAndClearTokenInformation, just for test purpose
	  	    //session.closeAndClearTokenInformation();
	  	    currentUser.setFbToken(session.getAccessToken());
	      	currentUser.setUser("user1@example.com");
	      	currentUser.setPassword("secret123");
	      	
	      	LoginTask loginTask = new LoginTask(currentUser.getUser(),currentUser.getPassword(), mPreferences , MainActivity.this);
	          if (loginTask.doLogin()) {    	
			        //TODO: after "dologin" call
	          	currentUser.setActiveSession(true);
	          	//userDao.insert(currentUser);
	          }else{
	          	//TODO: show fb  login error
	          }
	      }
    }
    
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

    private void setupFBSession(Bundle savedInstanceState) {
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, callback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(callback));
            }
        }
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
        	currentUser.setUser("user1@example.com");
        	currentUser.setPassword("secret123");
            LoginTask loginTask = new LoginTask(currentUser.getUser(),currentUser.getPassword(), mPreferences , MainActivity.this);
            if (loginTask.doLogin()) {
            	
            	//TODO: after "dologin" call
            	currentUser.setActiveSession(true);
            	//userDao.insert(currentUser);
            }else{
            	//TODO: show fb regular login error
            }
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
      uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);

    }
	
	@Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(callback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(callback);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

}