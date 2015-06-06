package com.solodroid.materialwallpaper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.io.IOException;

import lib.cropper.wallpaper.CropImageView;

public class SetAsWallpaperActivity extends ActionBarActivity {

	private CropImageView mCropImageView;
	String[] mImages,mCatName;
	int position;
    Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set_as_wallpaper);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		Intent i=getIntent();
		mImages=i.getStringArrayExtra("WALLPAPER_IMAGE_URL");
		mCatName=i.getStringArrayExtra("WALLPAPER_IMAGE_CATEGORY");
		position=i.getIntExtra("POSITION_ID", 0);
		mCropImageView = (CropImageView)findViewById(R.id.CropImageView);
		  
		  ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		  ImageLoader.getInstance().loadImage(Constant.SERVER_IMAGE_DETAILS+mImages[position], new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				mCropImageView.setImageBitmap(arg2);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		   
		
	}
	 
	 public void setAsWallpaper(View view) throws IOException
	    {
		 (new SetWallpaperTask(SetAsWallpaperActivity.this)).execute("");
	    }
	 
	 public class SetWallpaperTask extends AsyncTask<String , String , String>
	 {
	     private Context context;
	     private ProgressDialog pDialog;
	     Bitmap bmImg = null;

	     public SetWallpaperTask(Context context) {
	         this.context = context;
	     }

	     @Override
	     protected void onPreExecute() {
	         // TODO Auto-generated method stub
	         super.onPreExecute();
	         
	         pDialog = new ProgressDialog(context);
	         pDialog.setMessage("Wallpaer set ...");
	         pDialog.setIndeterminate(false);
	         pDialog.setCancelable(false);
	         pDialog.show();

	     }

	     @Override
	     protected String doInBackground(String... args) {
	         // TODO Auto-generated method stub
	    	bmImg=mCropImageView.getCroppedImage();
	         return null;   
	     }


	     @Override
	     protected void onPostExecute(String args) {
	         // TODO Auto-generated method stub
	         WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext()); // --The method context() is undefined for the type SetWallpaperTask
	         try {
				wpm.setBitmap(bmImg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         pDialog.dismiss();
	         Toast.makeText(SetAsWallpaperActivity.this, "WallPaper Set", Toast.LENGTH_SHORT).show();
	         finish();
	     }
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
