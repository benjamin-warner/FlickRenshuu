package com.nihonkaeritai.flickrenshuu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nihonkaeritai.flickrenshuu.fragments.KanaAttackTitleFragment;
import com.nihonkaeritai.flickrenshuu.fragments.TitleFragment;

public class MainMenuActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_menu);

        fragmentManager = getSupportFragmentManager();
        Fragment titleFragment = new TitleFragment();
        addFragment(titleFragment, "main");

    }
    public void menuButtonListener(View v){
        switch (v.getId()) {
            case R.id.startKanaAttack:
                Fragment kanaAttackFragment = new KanaAttackTitleFragment();
                addFragment(kanaAttackFragment, "kana");
        }
    }

    public void startGame(View v){
        switch (v.getId()){
            case R.id.playGame:
                Intent startGame = new Intent(this,RenshuuActivity.class);
                startActivity(startGame);
        }
    }

    private void addFragment(Fragment fragment, String newFragmentTag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(fragmentManager.getFragments() == null) {
            transaction.add(R.id.mainMenuLayout, fragment, newFragmentTag);
            transaction.addToBackStack(newFragmentTag);
        }
        else{
            transaction.replace(R.id.mainMenuLayout,fragment,newFragmentTag);
        }
        transaction.commit();
    }

    private void swapFragment(String fragmentTag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainMenuLayout, fragmentManager.findFragmentByTag(fragmentTag));
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        String currentFragmentTag = fragmentManager.findFragmentById(R.id.mainMenuLayout).getTag();
        if(!currentFragmentTag.equals("main")) {
            swapFragment("main");
        }
    }
}
