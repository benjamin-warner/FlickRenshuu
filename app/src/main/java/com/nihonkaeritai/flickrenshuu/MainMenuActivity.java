package com.nihonkaeritai.flickrenshuu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_menu);

    }

    public void startGame(View v){
        switch (v.getId()){
            case R.id.playGame:
                Intent startGame = new Intent(this,RenshuuActivity.class);
                startActivity(startGame);
        }
    }
}
