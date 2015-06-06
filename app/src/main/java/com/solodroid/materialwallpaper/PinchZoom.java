package com.solodroid.materialwallpaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class PinchZoom extends ActionBarActivity {

	String[] mZoomImages,mZoomCatName;
	int position;
	public ImageLoader imageLoader; 
	DisplayImageOptions options;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinch_zoom);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.mipmap.ic_launcher)
		.showImageOnFail(R.mipmap.ic_launcher)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();


		ZoomableImageView zoom=(ZoomableImageView)findViewById(R.id.IMAGEID);

		Intent i=getIntent();
		mZoomImages=i.getStringArrayExtra("ZOOM_IMAGE_URL");
		mZoomCatName=i.getStringArrayExtra("ZOOM_IMAGE_CATEGORY");
		position=i.getIntExtra("POSITION_ID", 0);

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		ImageLoader.getInstance().displayImage(Constant.SERVER_IMAGE_DETAILS+mZoomImages[position], zoom, options, new SimpleImageLoadingListener());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{
		switch (menuItem.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				break;

			default:
				return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}
}
