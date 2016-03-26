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
import com.nihonkaeritai.flickrenshuu.managers.FancyFragmentManager;

public class MainMenuActivity extends AppCompatActivity {
    FancyFragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_menu);

        fragmentManager = new FancyFragmentManager(this);

        Fragment titleFragment = new TitleFragment();
        fragmentManager.swapFragment(titleFragment, "main");
    }

    public void menuButtonListener(View v){
        switch (v.getId()) {
            case R.id.startKanaAttack:
                Fragment kanaAttackFragment = new KanaAttackTitleFragment();
                fragmentManager.swapFragment(kanaAttackFragment, "kana");
        }
    }

    public void startGame(View v){
        switch (v.getId()){
            case R.id.playGame:
                Intent startGame = new Intent(this,RenshuuActivity.class);
                startActivity(startGame);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.getContainerFragment(R.id.mainMenuLayout);
        if (!(currentFragment instanceof TitleFragment)) {
            Fragment mainMenu = fragmentManager.getFragmentByTag("main");
            fragmentManager.swapFragment(mainMenu, "main");
        }else{
            finish();
        }
    }
}
