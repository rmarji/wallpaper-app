package com.solodroid.materialwallpaper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class Adapter_RecentWallpaper extends ArrayAdapter<Item_RecentWallpaper> {
	
	private Activity activity;
	private List<Item_RecentWallpaper> itemsLatest;
	private Item_RecentWallpaper objLatestBean;
	private int row;
	public ImageLoader imageLoader; 
	private int imageWidth;
	 public Adapter_RecentWallpaper(Activity act, int resource, List<Item_RecentWallpaper> arrayList, int columnWidth) {
			super(act, resource, arrayList);
			this.activity = act;
			this.row = resource;
			this.itemsLatest = arrayList;
			imageLoader=new ImageLoader(activity);
			this.imageWidth=columnWidth;
		}
	 @Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(row, null);

				holder = new ViewHolder();
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			if ((itemsLatest == null) || ((position + 1) > itemsLatest.size()))
				return view;

			objLatestBean = itemsLatest.get(position);

			 
			 holder.imgv_latetst=(ImageView)view.findViewById(R.id.item);
			 holder.imgv_latetst.setScaleType(ImageView.ScaleType.CENTER_CROP);
			 holder.imgv_latetst.setLayoutParams(new GridView.LayoutParams(imageWidth,
						imageWidth));
			 
			 imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB+objLatestBean.getImageurl().toString(), holder.imgv_latetst);
			
			return view;
			
		}

		public class ViewHolder {
		 
			public ImageView imgv_latetst;
			 
		}

}
