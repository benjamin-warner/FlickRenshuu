package com.nihonkaeritai.flickrenshuu.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public abstract class FancyTextInput {
    EditText userInput;
    String currentText;

    public FancyTextInput(View inputView){
        userInput = (EditText)inputView;

        clearInput();
        startTextChangeListener(userInput);
    }

    protected void startTextChangeListener(final EditText userInput){
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                    handleInput(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public abstract void handleInput(String s);

    public String getCurrentInput(){
        String input = currentText;
        clearInput();
        return input;
    }

    public void clearInput(){
        currentText = "";
        userInput.setText("");
    }
}
