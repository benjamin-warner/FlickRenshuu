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
import com.nihonkaeritai.flickrenshuu.repositories.ScoreBank;
import com.nihonkaeritai.flickrenshuu.utility.FancyCountdownTimer;
import com.nihonkaeritai.flickrenshuu.utility.FancyTextInput;

public class RenshuuActivity extends AppCompatActivity {
    private FancyCountdownTimer timer;
    private KanaRepository kanaRepository;
    private FancyTextInput inputWatcher;
    private ScoreBank userPoints;
    int consecutiveCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renshuu);
        
        View kanaKeyView = findViewById(R.id.kanaKey);
        View chisaiIndicator =  findViewById(R.id.chisaiIndicator);
        kanaRepository = new KanaRepository(this.getApplicationContext(), kanaKeyView, chisaiIndicator);

        userPoints = new ScoreBank(100,10);
        View displayPoints = findViewById(R.id.pointDisplay);
        userPoints.setDisplayReference(displayPoints);
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
                startNextRound();
            }
        });
    }

    private void startNextRound() {
        kanaRepository.getNextKana();
        inputWatcher.clearInput();
        timer.start();
    }

    private void initGame(){
        startFancyInputWatcher();
        initTimer();
    }

    private void initTimer() {
        timer = new FancyCountdownTimer((long)3000, this) { //TODO: turn magic number to user setting
            @Override
            public void onFinish() {
                startNextRound();
            }
        };
    }

    private void checkInput(String input) {
        int status = kanaRepository.getInputMatchStatus(input);
        if(status == KanaRepository.MatchStatus.incorrect) {
            consecutiveCorrect = 0;
            inputWatcher.clearInput();
        }
        if(status == KanaRepository.MatchStatus.correct) {
            consecutiveCorrect++;
            userPoints.addPoints(consecutiveCorrect,timer.remainingTimeInMillis());
            startNextRound();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(timer != null) {
            timer.cancel();
        }
    }
}
