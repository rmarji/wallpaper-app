package com.solodroid.materialwallpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Activity_Latest extends Fragment {

	public static final String TAG = "fragment_latest";

	public Activity_Latest() {
		// Required empty public constructor
	}

	GridView grid;
	List<Item_RecentWallpaper> arrayOfLatestImage;
	Adapter_RecentWallpaper objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListImage,allListImageCatName;
	String[] allArrayImage,allArrayImageCatName;

	private Item_RecentWallpaper objAllBean;
	private int columnWidth;
	JsonUtils util;
	public DatabaseHandlerLatest db;

	private AdView mAdView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.layout_recent_wallpaper, container, false);
		grid=(GridView)rootView.findViewById(R.id.latest_grid);

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

		db=new DatabaseHandlerLatest(getActivity());

		arrayOfLatestImage=new ArrayList<Item_RecentWallpaper>();
		allListImage=new ArrayList<String>();
		allListImageCatName=new ArrayList<String>();

		allArrayImage=new String[allListImage.size()];
		allArrayImageCatName=new String[allListImageCatName.size()];

		util=new JsonUtils(getActivity());
		InitilizeGridLayout();
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub


				Intent intslider=new Intent(getActivity(),SlideImageActivity.class);
				intslider.putExtra("POSITION_ID", position);
				intslider.putExtra("IMAGE_ARRAY", allArrayImage);
				intslider.putExtra("IMAGE_CATNAME", allArrayImageCatName);

				startActivity(intslider);

			}
		});
		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.LATEST_URL);
		} else {


			arrayOfLatestImage= db.getAllData();
			if(arrayOfLatestImage.size()==0)
			{
				Toast.makeText(getActivity(), "First Time Load Application from Internet ", Toast.LENGTH_SHORT).show();
			}
			setAdapterToListview();
			for(int j=0;j<arrayOfLatestImage.size();j++)
			{

				objAllBean=arrayOfLatestImage.get(j);

				allListImage.add(objAllBean.getImageurl());
				allArrayImage=allListImage.toArray(allArrayImage);

				allListImageCatName.add(objAllBean.getCategoryName());
				allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

			}
		}

		return rootView;
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((util.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

		grid.setNumColumns(Constant.NUM_OF_COLUMNS);
		grid.setColumnWidth(columnWidth);
		grid.setStretchMode(GridView.NO_STRETCH);
		grid.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		grid.setHorizontalSpacing((int) padding);
		grid.setVerticalSpacing((int) padding);
	}
	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
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
				showToast("Server Connection Error");
				alert.showAlertDialog(getActivity(), "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						Item_RecentWallpaper objItem = new Item_RecentWallpaper();

						db.AddtoFavoriteLatest(new Item_RecentWallpaper(objJson.getString(Constant.LATEST_IMAGE_CATEGORY_NAME),objJson.getString(Constant.LATEST_IMAGE_URL)));

						objItem.setCategoryName(objJson.getString(Constant.LATEST_IMAGE_CATEGORY_NAME));
						objItem.setImageurl(objJson.getString(Constant.LATEST_IMAGE_URL));

						arrayOfLatestImage.add(objItem);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				for(int j=0;j<arrayOfLatestImage.size();j++)
				{

					objAllBean=arrayOfLatestImage.get(j);

					allListImage.add(objAllBean.getImageurl());
					allArrayImage=allListImage.toArray(allArrayImage);

					allListImageCatName.add(objAllBean.getCategoryName());
					allArrayImageCatName=allListImageCatName.toArray(allArrayImageCatName);

				}

				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		objAdapter = new Adapter_RecentWallpaper(getActivity(), R.layout.lsv_item_gridwallpaper,
				arrayOfLatestImage,columnWidth);
		grid.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
