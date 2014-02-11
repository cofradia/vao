//package com.cofradia.vao;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.facebook.LoggingBehavior;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.Settings;
//
//
//public class LoginFacebook extends Activity {
//	
//    private TextView textInstructionsOrLink;
//    private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
//    private Button buttonLoginLogout;
//    private Session.StatusCallback statusCallback = new SessionStatusCallback();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//setContentView(R.layout.activity_login_facebook);
//        //buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout);
//        //textInstructionsOrLink = (TextView)findViewById(R.id.instructionsOrLink);
//
////        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
////
////        Session session = Session.getActiveSession();
////        if (session == null) {
////            if (savedInstanceState != null) {
////                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
////            }
////            if (session == null) {
////                session = new Session(this);
////            }
////            Session.setActiveSession(session);
////            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
////                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
////            }
////        }
////
////        updateView();
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.login_facebook, menu);
//		return true;
//	}
//	
//	@Override
//    public void onStart() {
//        super.onStart();
//        Session.getActiveSession().addCallback(statusCallback);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Session.getActiveSession().removeCallback(statusCallback);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Session session = Session.getActiveSession();
//        Session.saveSession(session, outState);
//    }
//
//	
//    private void updateView() {
//        Session session = Session.getActiveSession();
//        if (session.isOpened()) {
//            textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
//           // buttonLoginLogout.setText(R.string.logout);
//            buttonLoginLogout.setOnClickListener(new OnClickListener() {
//                public void onClick(View view) { onClickLogout(); }
//            });
//        } else {
//            textInstructionsOrLink.setText(R.string.instructions);
//            buttonLoginLogout.setText(R.string.login);
//            buttonLoginLogout.setOnClickListener(new OnClickListener() {
//                public void onClick(View view) { onClickLogin(); }
//            });
//        }
//    }
//
//    private void onClickLogin() {
//        Session session = Session.getActiveSession();
//        if (!session.isOpened() && !session.isClosed()) {
//            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
//        } else {
//            Session.openActiveSession(this, true, statusCallback);
//        }
//    }
//
//    private void onClickLogout() {
//        Session session = Session.getActiveSession();
//        if (!session.isClosed()) {
//            session.closeAndClearTokenInformation();
//        }
//    }
//
//    private class SessionStatusCallback implements Session.StatusCallback {
//        @Override
//        public void call(Session session, SessionState state, Exception exception) {
//            updateView();
//            //alguito:
//        }
//    }
//
//}
