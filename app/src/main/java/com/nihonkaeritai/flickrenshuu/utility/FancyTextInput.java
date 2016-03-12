package com.nihonkaeritai.flickrenshuu.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public abstract class FancyTextInput {
    EditText userInput;
    String currentText;

    public FancyTextInput(View inputView){
        userInput = (EditText)inputView;

        currentText = "";
        startSendListener(userInput);
        startTextChangeListener(userInput);
    }

    protected void startTextChangeListener(EditText userInput){
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void startSendListener(EditText input){
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                handleUserForcedCheck(getCurrentInput());
                return true;
            }
        });
    }

    public abstract void handleUserForcedCheck(String s);

    public String getCurrentInput(){
        String input = currentText;
        currentText = "";
        userInput.setText("");
        return input;
    }
}
