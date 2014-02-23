package com.cofradia.vao;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

public class FacebookAPI {

    // List of additional write permissions being requested: add events also
    private static final List<String> PERMISSIONS = Arrays.asList("publish_stream, publish_actions, email");

    // Request code for reauthorization requests. 
    private static final int REAUTH_ACTIVITY_CODE = 100; 

    // Flag to represent if we are waiting for extended permissions
    private boolean pendingAnnounce = false; 
    private MainActivity mainActivity;
	Session fbSession ;
	private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
	
    public FacebookAPI(){
		Session.getActiveSession();
	}; 
	
	public Session setupFBSession(Bundle savedInstanceState,
			MainActivity mainActivity) {
		 Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		 	this.mainActivity = mainActivity;
	        this.fbSession = Session.getActiveSession();
	        if (fbSession == null) {
	            if (savedInstanceState != null) {
	            	fbSession = Session.restoreSession(mainActivity, null, callback, savedInstanceState);
	            }
	            if (fbSession == null) {
	            	fbSession = new Session(mainActivity);
	            }
	            Session.setActiveSession(fbSession);
	            if (fbSession.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	            	
	            	fbSession.openForRead(new Session.OpenRequest(mainActivity).setCallback(callback));
	            }
	        }
	        return fbSession;
	};
	 
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        Log.d("on SessionStateChange", "session changed");

        if (session!=null && session.isOpened()) {
	      	Log.d("FB Login: ", "Session is already opened, redirecting to eventList");
	      	
	      	if (state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
	      	// Manage fb's session update
		      	Log.d("FB UPDATE TOKEN: ", "Session actualizada...");

	      		// Session updated with new permissions
	            // so try publishing once more.
	            //tokenUpdated();
	        } else {
		      	Log.d("FB UPDATE TOKEN: ", "Session NO actualizada...Pending request email");
		      	//handleAnnounce();
	           
	        }
	      } else {
	    	  
	    	  Log.d("session nula? "," session nula closed: " + session.isClosed() +" state: " + session.getState());
	      }
        
    }
	
	//TODO: must receive extra permission's list
    public void getExtendedPermissions() {  
        pendingAnnounce = false;
        Session session = Session.getActiveSession();
        Log.d("FB HANDLEANNOUNCE", "on handle announce");
        if (session == null || !session.isOpened()) {
        	
            return;
        }

        List<String> permissions = session.getPermissions();
        if (!permissions.containsAll(PERMISSIONS)) {
        	Log.d("FB PERMISSIONS", "NO CONTIENE LOS PERMISOS EXTENDIDOS");
            pendingAnnounce = true; // Mark that we are currently waiting for confirmation of publish permissions 
            session.addCallback(callback);
            requestPublishPermissions(this.mainActivity, session, PERMISSIONS, REAUTH_ACTIVITY_CODE);
            return;
        }
        Log.d("FB PERMISSIONS", "YA CONTIENE LOS PERMISOS EXTENDIDOS");
        // TODO: Publish the post. You would need to implement this method to actually post something
//	        publishMessage();
    }
	   
    void requestPublishPermissions(Activity activity, Session session, List<String> permissions,  
        int requestCode) {
    	Log.d("REQUES PUBLISH", "REQUEST PUBLISH INIT");
    	  if (session != null) {
    	    	Log.d("REQUES PUBLISH", "SESSION != NULL");

    	        Session.NewPermissionsRequest reauthRequest = new Session.NewPermissionsRequest(activity, permissions)
    	        .setRequestCode(requestCode);
    	        
    	        session.requestNewPublishPermissions(reauthRequest);
    	    }
      	Log.d("REQUES PUBLISH", "REQUEST PUBLISH FIN");
    }
}
