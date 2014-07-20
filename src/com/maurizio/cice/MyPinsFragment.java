package com.maurizio.cice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;

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

	ProgressDialog pd;
	
	private String token;

	//private EditText pin;
	//private Button btnHacerPin;
//	ProgressDialog pd;
	private ListView listPinView;
	

	
	public MyPinsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		token=getPreferencesByKey("token");


		pd = new ProgressDialog(getActivity());
		pd.setTitle("Processing..");
		pd.setMessage("Please wait..");
		pd.setCancelable(false);
		pd.setIndeterminate(true);

		View rootView = inflater.inflate(R.layout.fragment_my_pins, container,
				false);

		
		/*
		 * INVOCAMOS AL TASK
		 * */
		
        listPinView = (ListView) rootView
        		.findViewById(R.id.lstPin);
        
        TaskThread tsk = new TaskThread(rootView.getContext(),listPinView);
        String token = getPreferencesByKey("token");
        tsk.setProgressD(pd);
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
	
	


}


