package com.nihonkaeritai.flickrenshuu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nihonkaeritai.flickrenshuu.R;

public class TitleFragment extends android.support.v4.app.Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstatnceState){
        return inflater.inflate(R.layout.fragment_welcome,container, false);
    }
}
