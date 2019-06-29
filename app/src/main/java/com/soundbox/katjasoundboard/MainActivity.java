package com.soundbox.katjasoundboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.soundbox.katjasoundboard.tabs.Tab1;
import com.soundbox.katjasoundboard.tabs.Tab2;
import com.soundbox.katjasoundboard.tabs.Tab3;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer mp;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private AdView adView;
    public static boolean isTesting;

    public static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final File FILES_PATH = new File(Environment.getExternalStorageDirectory(), "Android/data/"+ getText(R.string.package_name) +"/files");


        if (Environment.MEDIA_MOUNTED.equals(

                Environment.getExternalStorageState())) {

            if (!FILES_PATH.mkdirs()) {


            }

        } else {


            finish();

        }


        adView = new AdView(this,"672280573203313_672280696536634", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         *
         *
         *
         */
        mNavigationView.setItemIconTintList(null);


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                Boolean link;
                link=true;

                switch (menuItem.getItemId()){



                    case R.id.teilen:
                        link=false;
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "✶"+ R.string.app_name +"✶");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "✶Go and check out the" + " \"" +  getText(R.string.app_name) + "\" " + "✶\n\n " + getText(R.string.playstore_link));
                        startActivity(Intent.createChooser(shareIntent,  "Share via"));
                        break;



                    case R.id.privacypolicy:
                        String policy = "https://sites.google.com/view/soundboxprivacypolicy/home";
                        Intent e = new Intent(Intent.ACTION_VIEW);
                        e.setData(Uri.parse(policy));
                        startActivity(e);
                        break;

                    case R.id.rechtliches:
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage(R.string.rechtliches)
                                .setCancelable(true)
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Imprint");
                        alert.show();
                        break;

                }






                return false;
            }

        });



        /**
         * Setup Drawer Toggle of the Toolbar
         */


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }




    public void TabOneItemClicked(int position) {

        cleanUpMediaPlayer();
        mp=MediaPlayer.create(MainActivity.this, Tab1.soundfiles[position]);
        mp.start();
    }

    public void TabTwoItemClicked(int position) {
        cleanUpMediaPlayer();
        mp=MediaPlayer.create(MainActivity.this, Tab2.soundfiles[position]);
        mp.start();

    }
    public void TabThreeItemClicked(int position) {
        cleanUpMediaPlayer();
        mp=MediaPlayer.create(MainActivity.this, Tab3.soundfiles[position]);
        mp.start();

    }




    public void cleanUpMediaPlayer() {
        if (mp != null) {
            try {
                mp.stop();
                mp.release();
                mp = null;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong, please try to restart the application.", Toast.LENGTH_LONG).show();

            }
        }
    }
    @Override
    public void onPause()
    {
        if(mp != null && mp.isPlaying())
        {
            mp.stop();
        }
        super.onPause();
    }
}
