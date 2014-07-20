package com.maurizio.cice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.maurizio.cice.handlerrequest.HandlerRequestHttp;
import com.maurizio.cice.model.Response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MakePinActivity extends Activity {

	private Response response;
	private EditText pin;
	private Button btnHacerPin;
	ProgressDialog pd;

	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_pin);

		token = getPreferencesByKey("token");

		pd = new ProgressDialog(this);
		pd.setTitle("Processing..");
		pd.setMessage("Please wait..");
		pd.setCancelable(false);
		pd.setIndeterminate(true);

		pin = (EditText) findViewById(R.id.etxtPin);
		btnHacerPin = (Button) findViewById(R.id.btnHacerPin);

		btnHacerPin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hacerPin();
			}
		});
	}

	private void hacerPin() {
		if (pin.getText().toString().length() == 0) {
			pin.setError(getString(R.string.error_ingresa_tu_pin));
			pin.requestFocus();
			return;
		}
		String[] data = { pin.getText().toString(), token };
		new MakePin().execute(data);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class MakePin extends AsyncTask<String, Void, Void> {
		String jsonStr = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			HandlerRequestHttp sh = new HandlerRequestHttp();

			String url = getResources().getString(R.urls.url_base)
					+ getResources().getString(R.urls.url_make_pin);
			Log.d("", "url_make_pin:" + url);
			String pin = params[0];
			String token = params[1];

			// AÑADIR PARAMETROS
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("pin", pin));
			data.add(new BasicNameValuePair("token", token));

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(url, HandlerRequestHttp.POST, data);

			Log.d("Response: ", "> " + jsonStr);

			return null;
		}

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();

			if (jsonStr != null) {
				hideSoftKeyboard();

				Gson gson = new Gson();

				response = gson.fromJson(jsonStr, Response.class);
				if (response == null) {
					return;
				}

				Log.d("response: ", "> " + response.getResponse());

				String msg = null;

				if (response.getResponse().contentEquals("OK")) {
					msg = getString(R.string.user_login_success);
					savePreferences("token", response.getToken());
				} else {
					if (response.getMessageCode().equals("000009")) {
						msg = getString(R.string.email_not_exists);
					}
				}
				if (msg != null) {
					Log.d("error: ", "> " + msg);
					// Toast.makeText(getApplicationContext(),
					// msg,
					// Toast.LENGTH_LONG).show();
				}

				//Intent intent = getIntent();
				Intent returnIntent = new Intent();
				//returnIntent.putExtra("response",response.getResponse());
				setResult(RESULT_OK,returnIntent);
				finish();
				//startActivity(intent);
			}
		}

		@Override
		protected void onCancelled() {

			pd.dismiss();
		}
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"MyPreferences", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void clearSharedPreferenes() {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"MyPreferences", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		this.finish();
		startActivity(this.getIntent());

	}

	private String getPreferencesByKey(String key) {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"MyPreferences", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, null);
	}
	
	public void hideSoftKeyboard() {
	    if(getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    }
	}

}
