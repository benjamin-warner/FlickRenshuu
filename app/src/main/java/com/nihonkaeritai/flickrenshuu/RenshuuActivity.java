package com.nihonkaeritai.flickrenshuu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.repositories.KanaRepository;
import com.nihonkaeritai.flickrenshuu.utility.FancyCountdownTimer;

public class RenshuuActivity extends AppCompatActivity {
    private FancyCountdownTimer timer;
    private TextView kanaKey;
    private TextView chisaiIndicator;
    private EditText userInput;
    private final long DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renshuu);

        kanaKey = (TextView)findViewById(R.id.kanaKey);
        chisaiIndicator = (TextView)findViewById(R.id.chisaiIndicator);

        userInput = (EditText)findViewById(R.id.userInput);

        userInput.setVisibility(EditText.INVISIBLE);
        chisaiIndicator.setVisibility(TextView.INVISIBLE);

        KanaRepository.initialize(this);

        waitForTapToStart();
    }

    private void setVisible(){
        userInput.setVisibility(EditText.VISIBLE);
        chisaiIndicator.setVisibility(TextView.VISIBLE);
    }

    private void waitForTapToStart() {
        final RelativeLayout tapToStart = (RelativeLayout)findViewById(R.id.mainLayout);
        tapToStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView startMessage = (TextView) findViewById(R.id.pushToStartText);
                startMessage.setVisibility(TextView.INVISIBLE);
                setVisible();
                startTextWatcher();
                startTimer();
                generateRandomKana();
                return false;
            }
        });
    }

    private void startTimer() {
        timer = new FancyCountdownTimer(DURATION,this){
            @Override
            public void onFinish() {
                userInput.setText("");
                timer.setDisplay("0:00");
                generateRandomKana();
            }
        };
    }

    private void startTextWatcher() {
        if(userInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(userInput, InputMethodManager.SHOW_IMPLICIT);
        }
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    compareInputToKey(s.toString());
                }
            }
        });
    }

    private void compareInputToKey(String userInputAsString) {
        String kanaKeyAsString = kanaKey.getText().toString();
        if(userInputAsString.equals(kanaKeyAsString)) {
            clearInputField();
            generateRandomKana();
        }else if(userInputAsString.length() > kanaKeyAsString.length()){
            clearInputField();
        }
    }

    private void generateRandomKana(){
        String nextKana = KanaRepository.getNextKana();
        kanaKey.setText(nextKana);

        if(KanaRepository.isIndexChisaiKana()){
            chisaiIndicator.setText(getString(R.string.chisai));
        }else{
            chisaiIndicator.setText("");
        }
        timer.start();
    }

    private void clearInputField(){
        userInput.setText("");
    }
}
