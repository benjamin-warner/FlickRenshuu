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
        swapFragment(titleFragment, "main");
    }

    public void menuButtonListener(View v){
        switch (v.getId()) {
            case R.id.startKanaAttack:
                Fragment kanaAttackFragment = new KanaAttackTitleFragment();
                swapFragment(kanaAttackFragment, "kana");
        }
    }

    public void startGame(View v){
        switch (v.getId()){
            case R.id.playGame:
                Intent startGame = new Intent(this,RenshuuActivity.class);
                startActivity(startGame);
        }
    }

    private void swapFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainMenuLayout, fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainMenuLayout);
        if (!(currentFragment instanceof TitleFragment)) {
            Fragment mainMenu = fragmentManager.findFragmentByTag("main");
            swapFragment(mainMenu, "main");
        }else{
            finish();
        }
    }
}
