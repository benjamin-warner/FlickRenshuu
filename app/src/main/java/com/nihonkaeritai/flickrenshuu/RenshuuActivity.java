package com.nihonkaeritai.flickrenshuu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.repositories.KanaRepository;
import com.nihonkaeritai.flickrenshuu.utility.FancyCountdownTimer;

import java.util.Random;

//TODO: Settings screen for FancyCountdownTimer duration, etc
//TODO: Fancypants splashcreen, first-time setup (time pref etc)
//TODO: Scoring (Time, accuracy, combo/streaks, etc)
//TODO: Good grafix (JUICE! LENS FLARE! VIBRATION!)
//TODO: Mapper class: phonetic -> kana
//TODO: Words!
//TODO: Complete sentence capability
//TODO: Random sentence gen?
//TODO: Strict mode (Erases input on typo)
//TODO: Kanji capability

public class RenshuuActivity extends AppCompatActivity {
    private FancyCountdownTimer timer;
    private KanaRepository kanaRepository;
    private TextView kanaKey;
    private TextView chisaiIndicator;
    private EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renshuu);

        kanaRepository = new KanaRepository(this.getBaseContext());

        kanaKey = (TextView)findViewById(R.id.kanaKey);
        chisaiIndicator = (TextView)findViewById(R.id.chisaiIndicator);
        generateRandomKana();

        userInput = (EditText)findViewById(R.id.userInput);
        startTextWatcher(userInput);
    }

    private void startTextWatcher(final EditText userInput) {
        userInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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

    //TODO: Improve kana picking method
    private void generateRandomKana(){
        int randomIndex = new Random().nextInt(kanaRepository.getLength());
        String randomString = kanaRepository.kanaArray[randomIndex];
        kanaKey.setText(randomString);

        if(kanaRepository.isIndexChisaiKana(randomIndex)){
            chisaiIndicator.setText(getString(R.string.chisai));
        }else{
            chisaiIndicator.setText("");
        }
        timer = new FancyCountdownTimer(3000,this);
    }

    private void clearInputField(){
        userInput.setText("");
    }
}
