package com.solodroid.materialwallpaper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_Category extends ArrayAdapter<Item_Category> {
	
	private Activity activity;
	private List<Item_Category> itemsAllphotos;
	private Item_Category objAllBean;
	private int row;
	ImageLoader imageloader; 
	
	 public Adapter_Category(Activity act, int resource, List<Item_Category> arrayList) {
			super(act, resource, arrayList);
			this.activity = act;
			this.row = resource;
			this.itemsAllphotos = arrayList;
			imageloader=new ImageLoader(activity);
		 
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

			if ((itemsAllphotos == null) || ((position + 1) > itemsAllphotos.size()))
				return view;

			objAllBean = itemsAllphotos.get(position);
			holder.txt=(TextView)view.findViewById(R.id.txt_allphotos_categty);
			holder.img_cat=(ImageView)view.findViewById(R.id.image_category);
			holder.txt.setText(objAllBean.getCategoryName().toString());
			imageloader.DisplayImage(Constant.SERVER_IMAGE_UPFOLDER_CATEGORY+objAllBean.getCategoryImage().toString(), holder.img_cat);
			return view;
			
		}

		public class ViewHolder {
		 
			public TextView txt;
			public ImageView img_cat;
		}

}
