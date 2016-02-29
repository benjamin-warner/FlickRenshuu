package com.nihonkaeritai.flickrenshuu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

        timer = new FancyCountdownTimer(DURATION,this){
            @Override
            public void onFinish() {
                userInput.setText("");
                timer.setDisplay("0:00");
                generateRandomKana();
            }
        };

        kanaKey = (TextView)findViewById(R.id.kanaKey);
        chisaiIndicator = (TextView)findViewById(R.id.chisaiIndicator);

        KanaRepository.initialize(this);
        generateRandomKana();

        userInput = (EditText)findViewById(R.id.userInput);
        startTextWatcher(userInput);
    }

    private void startTextWatcher(final EditText userInput) {
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
