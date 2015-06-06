package com.solodroid.materialwallpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_CategoryItem extends ActionBarActivity {

	GridView grid_cat_item;
	List<Item_All_ByCategory> arrayOfCategoryImage;
	Adapter_ItemCategory objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListImage,allListImageCatName,allListItemId;
	String[] allArrayImage,allArrayImageCatName,allArrayItemId;
	private int columnWidth;
	JsonUtils util;
	private AdView mAdView;
	public DatabaseHandlerCateList db;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_item_by_category);
		setTitle(Constant.CATEGORY_TITLE);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		db=new DatabaseHandlerCateList(Activity_CategoryItem.this);

		// Look up the AdView as a resource and load a request.
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());

		grid_cat_item=(GridView)findViewById(R.id.category_grid);
		arrayOfCategoryImage=new ArrayList<Item_All_ByCategory>();

		allListImage=new ArrayList<String>();
		allListImageCatName=new ArrayList<String>();
		allListItemId=new ArrayList<String>();

		allArrayImage=new String[allListImage.size()];
		allArrayImageCatName=new String[allListImageCatName.size()];
		allArrayItemId=new String[allListItemId.size()];

		util=new JsonUtils(getApplicationContext());
		InitilizeGridLayout();

		grid_cat_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intslider=new Intent(getApplicationContext(),SlideImageActivity.class);
				intslider.putExtra("POSITION_ID", position);
				intslider.putExtra("IMAGE_ARRAY", allArrayImage);
				intslider.putExtra("IMAGE_CATNAME", allArrayImageCatName);
				intslider.putExtra("ITEMID", allArrayItemId);

				startActivity(intslider);

			}
		});

		if (JsonUtils.isNetworkAvailable(Activity_CategoryItem.this)) {
			new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_ID);
		} 
		else
		{
			arrayOfCategoryImage=db.getFavRow(Constant.CATEGORY_ID);
			if(arrayOfCategoryImage.size()==0)
			{
				Toast.makeText(getApplicationContext(), "First Time Load Application from Internet ", Toast.LENGTH_SHORT).show();
			}
			setAdapterToListview();
			for(int j=0;j<arrayOfCategoryImage.size();j++)
			{

				Item_All_ByCategory objCategoryBean=arrayOfCategoryImage.get(j);

				allListImage.add(objCategoryBean.getItemImageurl());
				allArrayImage=allListImage.toArray(allArrayImage);

				allListImageCatName.add(objCategoryBean.getItemCategoryName());
				allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

				allListItemId.add(objCategoryBean.getItemCatId());
				allArrayItemId=allListItemId.toArray(allArrayItemId);

			}

		}

	}
	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid_cat_item.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid_cat_item.setColumnWidth(columnWidth);
		grid_cat_item.setStretchMode(GridView.NO_STRETCH);
		grid_cat_item.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid_cat_item.setHorizontalSpacing((int) padding);
		grid_cat_item.setVerticalSpacing((int) padding);
	}

	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Activity_CategoryItem.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("No data found from web!!!");
				Activity_CategoryItem.this.finish();
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ITEM_ARRAY);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						Item_All_ByCategory objItem = new Item_All_ByCategory();

						db.AddtoFavoriteCateList(new Item_All_ByCategory(objJson.getString(Constant.CATEGORY_ITEM_CATNAME),objJson.getString(Constant.CATEGORY_ITEM_IMAGEURL),objJson.getString(Constant.CATEGORY_ITEM_CATID)));
						Log.e("og", ""+objJson.getString(Constant.CATEGORY_ITEM_CATNAME));
						Log.e("og", ""+objJson.getString(Constant.CATEGORY_ITEM_IMAGEURL));
						Log.e("og", ""+objJson.getString(Constant.CATEGORY_ITEM_CATID));
						objItem.setItemCategoryName(objJson.getString(Constant.CATEGORY_ITEM_CATNAME));
						objItem.setItemImageurl(objJson.getString(Constant.CATEGORY_ITEM_IMAGEURL));
						objItem.setItemCatId(objJson.getString(Constant.CATEGORY_ITEM_CATID));

						arrayOfCategoryImage.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				for(int j=0;j<arrayOfCategoryImage.size();j++)
				{

					Item_All_ByCategory objCategoryBean=arrayOfCategoryImage.get(j);

					allListImage.add(objCategoryBean.getItemImageurl());
					allArrayImage=allListImage.toArray(allArrayImage);

					allListImageCatName.add(objCategoryBean.getItemCategoryName());
					allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

					allListItemId.add(objCategoryBean.getItemCatId());
					allArrayItemId=allListItemId.toArray(allArrayItemId);

				}

				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		objAdapter = new Adapter_ItemCategory(Activity_CategoryItem.this, R.layout.lsv_item_gridwallpaper,
				arrayOfCategoryImage,columnWidth);
		grid_cat_item.setAdapter(objAdapter);


	}

	public void showToast(String msg) {
		Toast.makeText(Activity_CategoryItem.this, msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home: 
			onBackPressed();
			return true;

		case R.id.menu_moreapp:

			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.play_more_apps))));

			return true;

		case R.id.menu_rateapp:

			final String appName = getPackageName();// your application package
													// name i.e play store
													// application url
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=" + appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
