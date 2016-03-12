package com.nihonkaeritai.flickrenshuu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.repositories.KanaRepository;
import com.nihonkaeritai.flickrenshuu.utility.FancyCountdownTimer;
import com.nihonkaeritai.flickrenshuu.utility.FancyTextInput;

public class RenshuuActivity extends AppCompatActivity {
    private FancyCountdownTimer timer;
    private KanaRepository kanaRepository;
    private FancyTextInput inputWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renshuu);

        kanaRepository = new KanaRepository(this.getApplicationContext(), findViewById(R.id.kanaKey), findViewById(R.id.chisaiIndicator));

        waitForTapToStart();
    }

    private void startFancyInputWatcher() {
        inputWatcher = new FancyTextInput(findViewById(R.id.userInput)){
            @Override
            public void handleUserForcedCheck(String s) {
                handleInputtedText(s);
            }
        };
    }

    private void waitForTapToStart() {
        final RelativeLayout tapToStart = (RelativeLayout) findViewById(R.id.mainLayout);
        tapToStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView startMessage = (TextView) findViewById(R.id.pushToStartText);
                startMessage.setVisibility(TextView.GONE);

                LinearLayout gameLayout = (LinearLayout)findViewById(R.id.gameLayout);
                gameLayout.setVisibility(LinearLayout.VISIBLE);

                initGame();
                return false;
            }
        });
    }

    private void initGame(){
        startFancyInputWatcher();
        initTimer();
        kanaRepository.getNextKana();
        openKeyboard();
        timer.start();
    }

    private void initTimer() {
        timer = new FancyCountdownTimer((long)3000, this) { //TODO: turn magic number to user setting
            @Override
            public void onFinish() {
                kanaRepository.inputIsEqualToKey(inputWatcher.getCurrentInput());
                timer.start();
                kanaRepository.getNextKana();
            }
        };
    }

    private void handleInputtedText(String input) {
        if(kanaRepository.inputIsEqualToKey(input)){
            kanaRepository.getNextKana();
            timer.start();
        }
    }

    private void openKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(findViewById(R.id.userInput), InputMethodManager.SHOW_IMPLICIT);
    }
}
