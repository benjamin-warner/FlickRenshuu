package com.nihonkaeritai.flickrenshuu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
            public void handleInput(String s) {
                checkInput(s);
            }
        };
    }

    private void waitForTapToStart() {
        final Button tapToStart = (Button) findViewById(R.id.pushToStartText);

        tapToStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView startMessage = (TextView) findViewById(R.id.pushToStartText);
                startMessage.setVisibility(TextView.GONE);

                LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
                gameLayout.setVisibility(LinearLayout.VISIBLE);

                initGame();
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
        timer = new FancyCountdownTimer((long)10000, this) { //TODO: turn magic number to user setting
            @Override
            public void onFinish() {
                kanaRepository.getInputMatchStatus(inputWatcher.getCurrentInput());
                timer.start();
                kanaRepository.getNextKana();
            }
        };
    }

    private void checkInput(String input) {
        int status = kanaRepository.getInputMatchStatus(input);
        switch(status){
            case 0:
                inputWatcher.clearInput();
                break;
            case 1:
                kanaRepository.getNextKana();
                inputWatcher.clearInput();
                timer.start();
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    private void openKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(findViewById(R.id.userInput), InputMethodManager.SHOW_IMPLICIT);
    }
}
