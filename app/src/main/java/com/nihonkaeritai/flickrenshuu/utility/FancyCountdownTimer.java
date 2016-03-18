package com.nihonkaeritai.flickrenshuu.utility;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.R;

public abstract class FancyCountdownTimer extends CountDownTimer {
    private TextView timeTextView;
    private long timeRemaining;

    public FancyCountdownTimer(long duration, AppCompatActivity activity){
        super(duration,10);

        timeTextView = (TextView)activity.findViewById(R.id.timer);
        timeRemaining = duration;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeRemaining = millisUntilFinished;
        displayTime();
    }

    public interface onFinish{

    }

    public void stop(){
        this.cancel();
    }

    public void setDisplay(String text){
        timeTextView.setText(text);
    }

    public void displayTime(){
        timeTextView.setText(timeRemainingToFormattedString());
    }

    public String timeRemainingToFormattedString(){
        long seconds = timeRemaining / 1000;
        long tenths = (timeRemaining - seconds * 1000) / 100;
        long hundredths = ((timeRemaining - seconds * 1000) - tenths * 100) / 10;

        return seconds + ":" + tenths + hundredths;
    }
}
