package com.solodroid.materialwallpaper;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;


public class NavigationDrawerMain extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Parse push notification
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));
        ParseAnalytics.trackAppOpened(getIntent());
        PushService.setDefaultPushCallback(this, NavigationDrawerMain.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(NavigationDrawerMain.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.app_email),
                BitmapFactory.decodeResource(getResources(), R.drawable.nav_drawer_icon)
        );
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment;
        switch (position) {
            case 0: //search//todo
                fragment = getFragmentManager().findFragmentByTag(Activity_Latest.TAG);
                if (fragment == null) {
                    fragment = new Activity_Latest();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Activity_Latest.TAG).commit();
                break;
            case 1: //stats
                fragment = getFragmentManager().findFragmentByTag(Activity_Category.TAG);
                if (fragment == null) {
                    fragment = new Activity_Category();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Activity_Category.TAG).commit();
                break;
            case 2: //my account //todo
                fragment = getFragmentManager().findFragmentByTag(Activity_Favorite.TAG);
                if (fragment == null) {
                    fragment = new Activity_Favorite();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Activity_Favorite.TAG).commit();
                break;
            case 3: //settings //todo
                fragment = getFragmentManager().findFragmentByTag(Activity_About.TAG);
                if (fragment == null) {
                    fragment = new Activity_About();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Activity_About.TAG).commit();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_moreapp:

                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.play_more_apps))));

                return true;

            case R.id.menu_rateapp:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

}
