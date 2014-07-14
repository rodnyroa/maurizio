package com.maurizio.cice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.maurizio.cice.handlerrequest.HandlerRequestHttp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnFocusChangeListener;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RegisterActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	

	// Values for email and password at the time of the login attempt.
	private String mEmail, mFullName, mUserName;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView, mFullNameView, mUserNameView;
	private View mRegisterFormView;
	private View mRegisterStatusView;
	private TextView mRegisterStatusMessageView;
	private ImageView imageUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		//imageUser = (ImageView) findViewById(R.id.imageUser);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email_register);
		mEmailView.setText(mEmail);
		mEmailView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.i("", "hola: "+mEmailView.getText().toString());
				if(mEmailView.getText().toString().length()>0){
					String[] data = { mEmailView.getText().toString()};
					new CheckEmail().execute(data);
				}
				
			}
			
		});

		mFullNameView = (EditText) findViewById(R.id.fullName);
		mUserNameView = (EditText) findViewById(R.id.userName);

		mPasswordView = (EditText) findViewById(R.id.password_register);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptRegister();
							return true;
						}
						return false;
					}
				});

		mRegisterFormView = findViewById(R.id.register_form);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);

		findViewById(R.id.add_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegister();
					}
				});

		/*imageUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("", "click img");
				Intent intent = new Intent();
				// call android default gallery
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_PICK);
				startActivityForResult(
						Intent.createChooser(intent, "Complete action using"),
						REQUEST_IMAGE_CAPTURE);
			}
		});*/
	}

	String imageFilePath;

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_IMAGE_CAPTURE
				&& resultCode == this.RESULT_OK) {
			// gallery
			Uri _uri = data.getData();
			// launchCropImage(_uri);
			// if (_uri != null) {
			// return;
			// }
			// User had pick an image.

			Cursor cursor = this
					.getContentResolver()
					.query(_uri,
							new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
							null, null, null);
			cursor.moveToFirst();

			// Link to the image final
			imageFilePath = cursor.getString(0);
			cursor.close();
			Log.i("", "imageFilePath:" + imageFilePath);

			Bitmap imageBitmap = BitmapFactory.decodeFile(imageFilePath);
			imageUser.setImageBitmap(imageBitmap);

		}
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptRegister() {
		

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mFullNameView.setError(null);
		mUserNameView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mFullName = mFullNameView.getText().toString();
		mUserName = mUserNameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid full name
		if (TextUtils.isEmpty(mFullName)) {
			mFullNameView.setError(getString(R.string.error_field_required));
			focusView = mFullNameView;
			cancel = true;
		} else

		// Check for a valid full name
		if (TextUtils.isEmpty(mUserName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		} else

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		} else

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mRegisterStatusMessageView
					.setText(R.string.register_progress);
			showProgress(true);
//			mAuthTask = new UserLoginTask();
//			mAuthTask.execute((Void) null);
			String[] data = {mFullName,mUserName, mEmail,mPassword};
			new UserRegisterTask().execute(data);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterStatusView
									.setVisibility(show ? View.VISIBLE
											: View.GONE);
						}
					});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserRegisterTask extends AsyncTask<String, Void, Void> {
		String jsonStr=null;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//showProgress(true);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			HandlerRequestHttp sh = new HandlerRequestHttp();

			String url = getResources().getString(R.urls.url_base)
					+ getResources().getString(R.urls.url_check_email);
			Log.d("", "url check email:" + url);
			String full_name = params[0];
			String user_name = params[1];
			String email = params[2];
			String password = params[3];

			// AÑADIR PARAMETROS
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("full_name", full_name));
			data.add(new BasicNameValuePair("user_name", user_name));
			data.add(new BasicNameValuePair("email", email));
			data.add(new BasicNameValuePair("password", password));

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(url,
					HandlerRequestHttp.POST, data);

			Log.d("Response: ", "> " + jsonStr);
			
			return null;
		}

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			showProgress(false);
			if (jsonStr != null) {
				if(jsonStr.contains("OK")){
					//Log.d("error: ", "> " + getString(R.string.error_email_already_exist));
					Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
					i.putExtra("register", "OK");
					startActivity(i);
					finish();
				}
			}
		}
		
		@Override
		protected void onCancelled() {
			
			showProgress(false);
		}
	}
	
	
	private class CheckEmail extends AsyncTask<String, Void, Void> {
		
		String jsonStr = null;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//showProgress(true);
		}

		@Override
		protected Void doInBackground(String... params) {
			HandlerRequestHttp sh = new HandlerRequestHttp();

			String url = getResources().getString(R.urls.url_base)
					+ getResources().getString(R.urls.url_check_email);
			Log.d("", "url check email:" + url);
			String txtEmail = params[0];

			// AÑADIR PARAMETROS
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("email", txtEmail));

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(url,
					HandlerRequestHttp.POST, data);

			Log.d("Response: ", "> " + jsonStr);
			
			return null;
		}

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			//showProgress(false);
			if (jsonStr != null) {
				if(jsonStr.contains("KO")){
//					Toast.makeText(getApplicationContext(), "Este email ya se en",
//							Toast.LENGTH_SHORT).show();
					Log.d("error: ", "> " + getString(R.string.error_email_already_exist));
					mEmailView.setText("");
					mEmailView.setError(getString(R.string.error_email_already_exist));
					mEmailView.requestFocus();
				}
			}
		}
	}
}
