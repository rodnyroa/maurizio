package com.maurizio.cice.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.maurizio.cice.R;
import com.maurizio.cice.adapter.PinListAdapter;
import com.maurizio.cice.handlerrequest.HandlerRequestHttp;
import com.maurizio.cice.model.Pin;
import com.maurizio.cice.model.Response;


public class TaskThread extends AsyncTask<String, Void, String> {
	
	private ListView listPinView;
	private List<Pin> items;
	private Context context;
	private HandlerRequestHttp handler;
	private List<NameValuePair> nameValuePairs;
	private Response response;
	
	public TaskThread(Context context, ListView listPinView) {
		this.context = context;
		this.listPinView = listPinView;
	}
	
	@Override
	protected String doInBackground(String... arg0) {

		Log.d("maupin", "GET PINS");

		handler = new HandlerRequestHttp();
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("token", arg0[0]));
		
		Log.d("maupin", "AFTER PARAMS");
		
		return handler.makeServiceCall("http://maudev.com:8080/MauPin-User-Pins/pins.do", 2, nameValuePairs);
	}

	@Override
	protected void onPostExecute(String jsonStr) {
		// TODO Auto-generated method stub

		try {
			//pd.dismiss();

			if (jsonStr != null) {

				Gson gson = new Gson();

				response = gson.fromJson(jsonStr, Response.class);
				if (response == null) {
					return;
				}

				Log.d("response: ", "> " + response.getResponse());

			}

			// Sets the data behind this ListView
			listPinView.setAdapter(new PinListAdapter(context,
					response.getPins()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPostExecute(jsonStr);
	}
}