package com.maurizio.cice.adapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurizio.cice.model.Pin;

public class PinListAdapter extends BaseAdapter {
	private Context context;
	private List<Pin> items;

	public PinListAdapter(Context context, List<Pin> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder viewHolder;

		if (convertView == null) {
			// Create a new view into the list.
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(com.maurizio.cice.R.layout.list_row,
					parent, false);

			// configure view holder
			viewHolder = new ViewHolder();
			viewHolder.imgIcon = (ImageView) rowView
					.findViewById(com.maurizio.cice.R.id.icon);
			viewHolder.tvFirstLine = (TextView) rowView
					.findViewById(com.maurizio.cice.R.id.firstLine);
			viewHolder.tvSecondLine = (TextView) rowView
					.findViewById(com.maurizio.cice.R.id.secondLine);
			rowView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		Pin item = (Pin) this.items.get(position);

		viewHolder.tvFirstLine.setText(item.getPin());
		viewHolder.tvSecondLine.setText(item.getDate());
		
			
		/*try {

			viewHolder.imgIcon.setTag(item.getImg());
			new DownloadImageTask().execute(viewHolder.imgIcon);

		} catch (Exception e) {
			Log.d("maupin", "ERROR*****");
			Log.d("maupin", e.toString());
		}*/

		return rowView;
	}

	static class ViewHolder {
		public TextView tvFirstLine;
		public TextView tvSecondLine;
		public ImageView imgIcon;
	}

	public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

		ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
			this.imageView = imageViews[0];
			return downloadImage((String) imageView.getTag());
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
		}

		private Bitmap downloadImage(String url) {
			Bitmap bm = null;
			try {
				URL aURL = new URL(url);
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
			} catch (IOException e) {
				Log.e("Hub", "Error getting the image from server : "
						+ e.getMessage().toString());
			}
			return bm;
		}
	}
}
