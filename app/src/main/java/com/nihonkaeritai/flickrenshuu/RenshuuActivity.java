package com.nihonkaeritai.flickrenshuu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.repositories.KanaRepository;
import com.nihonkaeritai.flickrenshuu.utility.FancyCountdownTimer;

public class RenshuuActivity extends AppCompatActivity {
    private FancyCountdownTimer timer;
    private TextView kanaKey;
    private KanaRepository kanaRepository;
    private EditText userInput;
    private final long DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renshuu);

        kanaKey = (TextView) findViewById(R.id.kanaKey);

        userInput = (EditText) findViewById(R.id.userInput);
        userInput.setVisibility(EditText.INVISIBLE);

        kanaRepository = new KanaRepository(this);

        waitForTapToStart();
    }

    private void waitForTapToStart() {
        final RelativeLayout tapToStart = (RelativeLayout) findViewById(R.id.mainLayout);
        tapToStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView startMessage = (TextView) findViewById(R.id.pushToStartText);

                startMessage.setVisibility(TextView.INVISIBLE);
                userInput.setVisibility(EditText.VISIBLE);

                startTextWatcher();
                initTimer();
                kanaKey.setText(kanaRepository.getNextKana());
                timer.start();
                return false;
            }
        });
    }

    private void initTimer() {
        timer = new FancyCountdownTimer(DURATION, this) {
            @Override
            public void onFinish() {
                userInput.setText("");
                timer.start();
                kanaKey.setText(kanaRepository.getNextKana());
            }
        };
    }

    private void startTextWatcher() {
        if (userInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(userInput, InputMethodManager.SHOW_IMPLICIT);
        }
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    handleInputtedText(s.toString());
                }
            }
        });
    }

    private void handleInputtedText(String input) {
        if(kanaRepository.inputIsEqualToKey(input)){
            kanaKey.setText(kanaRepository.getNextKana());
            userInput.setText("");
            timer.start();
        }
    }
}
