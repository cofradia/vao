package com.cofradia.vao;

import java.io.IOException;
import java.util.Arrays;


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


import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;

import de.greenrobot.daovao.DaoMaster;
import de.greenrobot.daovao.DaoSession;
import de.greenrobot.daovao.User;
import de.greenrobot.daovao.UserDao;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Usuario
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String ADMINUSER = "admin@vao.com";
    public final static String ADMINPWD = "secret123";
    
    private SharedPreferences mPreferences;
    private String emailText;
    private String passwordText;
    private LoginButton loginButton;
    private FacebookAPI fbApi = new FacebookAPI();
    private Session  fbSession ;
    
    //GreenDao
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private User currentUser = new User();
    private GraphUser user;
    private UiLifecycleHelper uiHelper;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        fbSession = fbApi.setupFBSession(savedInstanceState, this);
        // Check whether user is already logged in and redirects to next View
        boolean userLoggedIn = user_logged_in(savedInstanceState);
        if (!userLoggedIn) {
        	setContentView(R.layout.main);
        	setViewListeners();
        }
    }
    
    private void setViewListeners(){
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("user_birthday", "user_status", "email"));

        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
                if (user!=null){
                	
                	Log.d("FBLogin", "usuario existente! ");
                	Log.d("FBLogin", " id usuario: "+ user.getId());
                	Log.d("FBLogin", " userName usuario: "+ user.getUsername());
                	Log.d("FBLogin", " name usuario: "+ user.getName());
                	Object email = user.asMap().get("email");
                	Log.d("FBLogin", "email: " + email);
                	Log.d("VAO Login", "doing regular login for fb user");
                	//TODO: AUTH FB SERVER MISSING 
                	currentUser.setUser(ADMINUSER);
                	currentUser.setPassword(ADMINPWD);
                	_doRegularLogin(currentUser);
                }
                else{
                	Log.d("FBLogin", "usuario nulo :(");
                    Toast.makeText(MainActivity.this, "No se pudo reealizar el loggeo con FB.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    
    private boolean user_logged_in(Bundle savedInstanceState) {
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
        emailText = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.txtPassword);
        passwordText = userPasswordField.getText().toString();

        if (emailText.length() == 0 || passwordText.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                Toast.LENGTH_LONG).show();
            return;
        } else {
        	currentUser.setUser(emailText);
        	currentUser.setPassword(passwordText);
        	_doRegularLogin(currentUser);
        }
    }
    
    private void _doRegularLogin(User currentUser){
	 LoginTask loginTask = new LoginTask(currentUser.getUser(),currentUser.getPassword(), mPreferences , MainActivity.this);
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
     //   Session.getActiveSession().addCallback(callback);
    }

    @Override
    public void onStop() {
        super.onStop();
       // Session.getActiveSession().removeCallback(callback);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

}