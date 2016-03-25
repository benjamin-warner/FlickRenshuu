package com.nihonkaeritai.flickrenshuu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nihonkaeritai.flickrenshuu.R;

public class KanaAttackTitleFragment extends android.support.v4.app.Fragment{
    public static final String TAG = "kana";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
     super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fagment_kana_attack_title,container,false);
    }
}
