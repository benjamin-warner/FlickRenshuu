package com.nihonkaeritai.flickrenshuu.managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nihonkaeritai.flickrenshuu.R;

public class FancyFragmentManager {
    private FragmentManager fragmentManager;
    private AppCompatActivity _activity;

    public FancyFragmentManager(AppCompatActivity activity){
        _activity = activity;
        fragmentManager = _activity.getSupportFragmentManager();
    }

    public void swapFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainMenuLayout, fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public Fragment getContainerFragment(int layoutID){
     return fragmentManager.findFragmentById(layoutID);
    }

    public Fragment getFragmentByTag(String tag){
        return fragmentManager.findFragmentByTag(tag);
    }
}
