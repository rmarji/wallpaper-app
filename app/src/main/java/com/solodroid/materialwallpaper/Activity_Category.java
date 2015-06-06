package com.solodroid.materialwallpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Category extends Fragment {

	public static final String TAG = "fragment_category";

	public Activity_Category() {
		// Required empty public constructor
	}

	ListView lsv_allphotos;
	List<Item_Category> arrayOfAllphotos;
	Adapter_Category objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	private Item_Category objAllBean;
	public DatabaseHandlerCate db;

	private AdView mAdView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.layout_category, container, false);
		lsv_allphotos=(ListView)rootView.findViewById(R.id.lsv_allphotos);
		arrayOfAllphotos=new ArrayList<Item_Category>();

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

		db=new DatabaseHandlerCate(getActivity());

		lsv_allphotos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				objAllBean=arrayOfAllphotos.get(position);
				String Catid=objAllBean.getCategoryId();
				Constant.CATEGORY_ID=objAllBean.getCategoryId();
				Log.e("cat_id",""+Catid);

				Constant.CATEGORY_TITLE=objAllBean.getCategoryName();

				Intent intcat=new Intent(getActivity(),Activity_CategoryItem.class);
				startActivity(intcat);


			}
		});


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.CATEGORY_URL);
		} else {

			arrayOfAllphotos=db.getAllData();
			if(arrayOfAllphotos.size()==0)
			{
				Toast.makeText(getActivity(), "First Time Load Application from Internet ", Toast.LENGTH_SHORT).show();
			}
			setAdapterToListview();
		}

		return rootView;
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
				showToast("No data found from web!!!");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						Item_Category objItem = new Item_Category();

						db.AddtoFavoriteCate(new Item_Category(objJson.getString(Constant.CATEGORY_CID),objJson.getString(Constant.CATEGORY_NAME),objJson.getString(Constant.CATEGORY_IMAGE_URL)));

						objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
						objItem.setCategoryId(objJson.getString(Constant.CATEGORY_CID));
						objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_IMAGE_URL));
						arrayOfAllphotos.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}


				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapter = new Adapter_Category(getActivity(), R.layout.lsv_item_category,
				arrayOfAllphotos);
		lsv_allphotos.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
