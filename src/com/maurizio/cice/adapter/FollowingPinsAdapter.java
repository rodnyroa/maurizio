package com.maurizio.cice.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.maurizio.cice.R;
import com.maurizio.cice.HomeFragment.FollowingPin;
import com.maurizio.cice.handlerrequest.HandlerRequestHttp;
import com.maurizio.cice.model.Pin;
import com.maurizio.cice.model.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class FollowingPinsAdapter  extends BaseAdapter{
	Response response;
	Context contexto;
	List<Pin> pins;
	public FollowingPinsAdapter(Context contexto,List<Pin> pins) {
		super();
		this.contexto=contexto;
		this.pins=pins;
	}
	
	/*static view holder class*/
	static class ViewHolder {
		TextView tvNombreUsuario;
		TextView tvPin;
		TextView tvFecha;
		ImageView imgFavorito;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pins.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pins.get(position);	
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return pins.indexOf(pins.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater)
				contexto.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_following_pins, null);
			holder = new ViewHolder();
			holder.tvFecha=(TextView) convertView.findViewById(R.id.tvFecha);
			holder.tvNombreUsuario=(TextView) convertView.findViewById(R.id.tvNombreUsuario);
			holder.tvPin=(TextView) convertView.findViewById(R.id.tvPin);
			holder.imgFavorito=(ImageView) convertView.findViewById(R.id.imgFavorito);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Pin pin = (Pin) getItem(position);
		
		holder.tvFecha.setText(pin.getDate());
		holder.tvNombreUsuario.setText(pin.getUser().getUserName());
		holder.tvPin.setText(pin.getPin());
		
		holder.imgFavorito.setTag(pin);
		holder.imgFavorito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("", "clicking img fav!!!");
				final Pin pin = (Pin) v.getTag();
				Log.i("", "IdSuser:"+pin.getUser().getUserId());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						contexto);
				
				// set title
				alertDialogBuilder.setTitle(contexto.getResources().getString(R.string.follow_question));
				
				alertDialogBuilder
				.setMessage(contexto.getResources().getString(R.string.follow_msg))
				.setCancelable(false)
				.setPositiveButton(contexto.getResources().getString(R.string.si),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//MainActivity.this.finish();
						String token=getPreferencesByKey("token");
						String idUser = pin.getUser().getUserId();
						String[] data = { token, idUser};
						
						Log.i("", "token:"+token);
						Log.i("", "IdSuser:"+idUser);
						
						new FollowIt().execute(data);
					}
				  })
				  .setNegativeButton(contexto.getResources().getString(R.string.cancel),new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
		});
		
		return convertView;
	}
	
	private String getPreferencesByKey(String key) {
		SharedPreferences sharedPreferences = this.contexto
				.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, null);
	}
	
	public class FollowIt extends AsyncTask<String, Void, Void> {
		String jsonStr = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			HandlerRequestHttp sh = new HandlerRequestHttp();

			String url = contexto.getResources().getString(R.urls.url_base)
					+ contexto.getResources().getString(R.urls.url_follow_it);
			Log.d("", "url_follow_it:" + url);
			String token = params[0];
			String idUser=params[1];

			// AÑADIR PARAMETROS
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("token", token));
			data.add(new BasicNameValuePair("public_user_id", idUser));

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(url, HandlerRequestHttp.POST, data);

			Log.d("Response: ", "> " + jsonStr);

			return null;
		}

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			pd.dismiss();

			if (jsonStr != null) {

				Gson gson = new Gson();

				response = gson.fromJson(jsonStr, Response.class);
				if (response == null) {
					return;
				}

				Log.d("response: ", "> " + response.getResponse());

				String msg = null;

				if (response.getToken()!=null) {
					savePreferences("token", response.getToken());
				}
				
				
				if(response.getResponse().equalsIgnoreCase("OK")){
					Log.i("", "peticion OK ");
					Toast.makeText(contexto, "OK", Toast.LENGTH_LONG).show();
					
					savePreferences("token", response.getToken());
				}else{
					if (response.getMessageCode().equals("000014")) {
						msg = contexto.getString(R.string.error_follow_exists);
					}
				}
				
				if (msg != null) {
					Log.d("error: ", "> " + msg);
					 Toast.makeText(contexto,
					 msg,
					 Toast.LENGTH_LONG).show();
				}

				// Intent intent = getIntent();
				// finish();
				// startActivity(intent);
			}
		}

		@Override
		protected void onCancelled() {

//			pd.dismiss();
		}
	}
	
	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = contexto
				.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

}
