package com.vladstoick.gotocinema;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vladstoick.utility.CinemaRestClient;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends SherlockActivity {
    private static final String EXTRA_EMAIL = "com.vladstoick.gotocinema.login";
    private String mEmail;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences settings = getSharedPreferences("appPref", Context.MODE_PRIVATE);
        String apiKey = settings.getString("api_acces","0");
        String user_id = settings.getString("user_id","0");
        if(!apiKey.equals("0") && !user_id.equals("0"))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        // Set up the login form.
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setText(mEmail);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == R.id.login || id == EditorInfo.IME_NULL) {
                            attemptLogin();
                            return true;
                        }
                        return false;
                    }
                });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptLogin();
                    }
                });
    }
    void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mEmail = mEmailView.getText().toString();
        String mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getResources().getString(R.string.wrong_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.wrong_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.wrong_username));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            RequestParams params = new RequestParams();
            params.put("username", mEmail);
            params.put("password", mPassword);
            CinemaRestClient.post("user/login", params, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(Throwable e, String response) {
                    e.printStackTrace();
                }

                @Override
                public void onSuccess(String response) {
                    JSONObject jObject = null;
                    boolean loginStatus = false;
                    try {
                        jObject = new JSONObject(response);
                        loginStatus = jObject.getBoolean("loggedIn");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (loginStatus) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        SharedPreferences sharedPref = getSharedPreferences("appPref", Context.MODE_PRIVATE);
//			    		SharedPreferences sharedPref = (getApplicationContext()).getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        String key = null;
                        String userid = null;
                        try {
                            key = jObject.getString("key");
                            userid = jObject.getString("user_id");
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        editor.putString("api_acces", key);
                        editor.putString("user_id", userid);
                        editor.commit();
                        startActivity(intent);
                        finish();

                    } else {
                        String error = null;
                        try {
                            error = jObject.getString("error");
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        if (error.equals("email"))
                            mEmailView.setError(getString(R.string.wrong_username));

                        mPasswordView.setError(getString(R.string.wrong_password));
                    }
                    showProgress(false);
                }
            });

        }

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}