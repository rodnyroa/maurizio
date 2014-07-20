package com.maurizio.cice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.maurizio.cice.MakePinActivity.MakePin;
import com.maurizio.cice.adapter.FollowingPinsAdapter;
import com.maurizio.cice.handlerrequest.HandlerRequestHttp;
import com.maurizio.cice.model.Response;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ListView;

public class HomeFragment extends Fragment {

	private ListView lvFollowingPins;

	private Response response;
	ProgressDialog pd;
	View rootView;

	private String token;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		pd = new ProgressDialog(getActivity());
		pd.setTitle("Processing..");
		pd.setMessage("Please wait..");
		pd.setCancelable(false);
		pd.setIndeterminate(true);

		token = getPreferencesByKey("token");
		Log.d("token home:", "" + token);

		rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		lvFollowingPins = (ListView) rootView.findViewById(R.id.lvFollowingPins);

		String[] data = { token };
		new FollowingPin().execute(data);

		return rootView;
	}

	public class FollowingPin extends AsyncTask<String, Void, Void> {
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
					+ getResources().getString(R.urls.url_following_pin);
			Log.d("", "url_following_pin:" + url);
			String token = params[0];

			// AÑADIR PARAMETROS
			List<NameValuePair> data = new ArrayList<NameValuePair>();
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
				
				if(response.getResponse().equalsIgnoreCase("OK")){
					Log.i("", "peticion OK ");
					if(response.getPins()!=null){
						Log.i("", "peticion con pin: "+response.getPins().size());
						FollowingPinsAdapter adapter = new FollowingPinsAdapter(rootView.getContext(), response.getPins());
						lvFollowingPins.setAdapter(adapter);
					}else{
						Fragment fragment = new SinDatosFragment();

						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.frame_container, fragment).commit();
					}
					
					savePreferences("token", response.getToken());
				}

				// Intent intent = getIntent();
				// finish();
				// startActivity(intent);
			}
		}

		@Override
		protected void onCancelled() {

			pd.dismiss();
		}
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
}
