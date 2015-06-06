package com.solodroid.materialwallpaper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class Adapter_Favorite extends BaseAdapter {

	LayoutInflater inflate;
	Activity activity;
	private List<Pojo> data;
	private Pojo objFavoriteBean;
	public ImageLoader imageLoader;
	private int imageWidth;

	public Adapter_Favorite(List<Pojo> contactList, Activity activity,
			int columnWidth) {
		this.activity = activity;
		this.data = contactList;
		inflate = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity);
		this.imageWidth = columnWidth;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class GroupItem {
		public ImageView img_fav;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		objFavoriteBean = data.get(position);

		View v = null;
		final GroupItem item = new GroupItem();
		
		v = inflate.inflate(R.layout.lsv_item_gridwallpaper, null);
		item.img_fav = (ImageView) v.findViewById(R.id.item);
		item.img_fav.setScaleType(ImageView.ScaleType.CENTER_CROP);
		item.img_fav.setLayoutParams(new GridView.LayoutParams(imageWidth,
				imageWidth));
		imageLoader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_THUMB
				+ objFavoriteBean.getImageurl().toString(), item.img_fav);

		return v;
	}
	


}
