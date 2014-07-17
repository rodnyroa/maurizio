package com.maurizio.cice.adapter;

import java.util.List;

import com.maurizio.cice.R;
import com.maurizio.cice.model.Pin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FollowingPinsAdapter  extends BaseAdapter{
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
		
		holder.imgFavorito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("", "clicking img fav!!!");
				
			}
		});
		
		return convertView;
	}

}
