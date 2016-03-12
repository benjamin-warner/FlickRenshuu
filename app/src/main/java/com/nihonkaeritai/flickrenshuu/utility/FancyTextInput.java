package com.nihonkaeritai.flickrenshuu.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleUserForcedCheck(getCurrentInput());
                }
                return false;
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
