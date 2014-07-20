package com.maurizio.cice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.maurizio.cice.handlerrequest.HandlerRequestHttp;
import com.maurizio.cice.model.Response;
import com.maurizio.cice.task.TaskThread;

public class MyPinsFragment extends Fragment {
	private Response response;
	//private EditText pin;
	//private Button btnHacerPin;
	//ProgressDialog pd;
	private ListView listPinView;
	
	
	public MyPinsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		pd = new ProgressDialog(getActivity());
		pd.setTitle("Processing..");
		pd.setMessage("Please wait..");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
*/
		View rootView = inflater.inflate(R.layout.fragment_my_pins, container,
				false);
		
		
		/*
		 * LO VEMOS LUEGO
		 * 
		 
		pin= (EditText) rootView.findViewById(R.id.etxtPin);
		btnHacerPin= (Button) rootView.findViewById(R.id.btnHacerPin);
		
		btnHacerPin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hacerPin();
			}
		});
		
		/*
		 * INVOCAMOS AL TASK
		 * */
		
        listPinView = (ListView) rootView
        		.findViewById(R.id.lstPin);
        
        TaskThread tsk = new TaskThread(rootView.getContext(),listPinView);
        String token = getPreferencesByKey("token");

        tsk.execute(token);
		

		return rootView;
	}
	
	
	private void savePreferences(String key, String value) {
	 	SharedPreferences sharedPreferences = this.getActivity()
	 	 .getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
	 	Editor editor = sharedPreferences.edit();
	 	editor.putString(key, value);
	 	editor.commit();
	 }

	 private void clearSharedPreferenes() {
	 	SharedPreferences sharedPreferences = this.getActivity()
	 	 .getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
	 	Editor editor = sharedPreferences.edit();
	 	editor.clear();
	 	editor.commit();
	 	this.getActivity().finish();
	 	startActivity(this.getActivity().getIntent());

	 }

	 private String getPreferencesByKey(String key) {
	 	SharedPreferences sharedPreferences = this.getActivity()
	 	 .getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
	 	return sharedPreferences.getString(key, null);
	 }
	
	
	
/*	
	
	private void hacerPin(){
		if(pin.getText().toString().length()==0){
			pin.setError(getString(R.string.error_ingresa_tu_pin));
			pin.requestFocus();
			return;
		}
		String[] data = { pin.getText().toString(), response.getToken() };
		new MakePin().execute(data);
	}
	*/
	 	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
/*	public class MakePin extends AsyncTask<String, Void, Void> {
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

			// A�ADIR PARAMETROS
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

				Gson gson = new Gson();

				response = gson.fromJson(jsonStr, Response.class);
				if (response == null) {
					return;
				}

				Log.d("response: ", "> " + response.getResponse());

				String msg = null;

				if (response.getResponse().contentEquals("OK")) {
					msg = getString(R.string.user_login_success);
				} else {
					if (response.getMessageCode().equals("000009")) {
						msg = getString(R.string.email_not_exists);
					}
				}
				if (msg != null) {
					Log.d("error: ", "> " + msg);
//					Toast.makeText(getApplicationContext(),
//							msg,
//							Toast.LENGTH_LONG).show();
				}
				// if(jsonStr.contains("OK")){
				// //Log.d("error: ", "> " +
				// getString(R.string.error_email_already_exist));
//				 Intent i = new Intent(LoginActivity.this,MainActivity.class);
//				 i.putExtra("response", response);
//				 startActivity(i);
//				 finish();
				// }
			}
		}

		@Override
		protected void onCancelled() {

			pd.dismiss();
		}
	}*/
}


