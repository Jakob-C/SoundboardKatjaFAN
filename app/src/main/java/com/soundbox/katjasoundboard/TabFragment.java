package com.soundbox.katjasoundboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.ads.InterstitialAd;
import com.soundbox.katjasoundboard.tabs.Tab1;
import com.soundbox.katjasoundboard.tabs.Tab2;
import com.soundbox.katjasoundboard.tabs.Tab3;


public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
   int tab_change_counter;

    private InterstitialAd interstitialAd;
    public static int int_items = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        interstitialAd = new InterstitialAd(getContext(), "672280573203313_672280826536621");

        interstitialAd.loadAd();

        View v =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        // Manage Interstitial Ad
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                tab_change_counter++;

                if(Integer.parseInt(getText(R.string.interstitial_ad_frequency).toString()) == tab_change_counter){
                    showad();

                    tab_change_counter = 0;
                }
            }
        });
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return v;
    }


    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        // Tab positions
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new Tab1();
            }
            if(position == 1){
                return new Tab2();
            }
            if(position == 2){
                return new Tab3();
            }
            return null;
        }


        // Tab titles
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return getText(R.string.tab1);
                case 1 :
                    return getText(R.string.tab2);
                case 2 :
                    return getText(R.string.tab3);
            }
            return null;
        }



        @Override
        public int getCount() {
            return int_items;
        }
    }

    // Interstitial Ad
    public void showad(){

        if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
            return;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if(interstitialAd.isAdInvalidated()) {
            return;
        }


        if(interstitialAd.isAdLoaded()){

            interstitialAd.show();
        }

        interstitialAd = new InterstitialAd(getContext(), "672280573203313_672280826536621");
        interstitialAd.loadAd();

    }
}