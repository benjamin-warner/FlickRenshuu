package com.nihonkaeritai.flickrenshuu.utility;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.R;

public class FancyCountdownTimer {
    private TextView _timeTextView;
    private long timeRemaining;
    public static CountDownTimer timer;
    private long _duration;

    public FancyCountdownTimer(long duration, AppCompatActivity activity) {
        _timeTextView = (TextView)activity.findViewById(R.id.timer);
        _duration = duration;
        initializeTimer();
    }

    private void initializeTimer() {
        timeRemaining = _duration;
        timer = new CountDownTimer(_duration, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                displayTime();
            }

            @Override
            public void onFinish() {
                _timeTextView.setText("0");

            }
        };
    }

    public void kill(){
        timer.cancel();
    }

    public void reset(){
        kill();
        start();
    }

    public void start(){
        timer.start();
    }

    public boolean isFinished(){
        return timeRemaining >= 0;
    }

    public long getTimeRemaining(){
        return timeRemaining;
    }

    public void displayTime(){
        _timeTextView.setText(timeRemainingToString());
    }

    private String timeRemainingToString(){
        String seconds = String.valueOf(timeRemaining / 1000 + 1);
        //String millis = String.valueOf(timeRemaining / 100) + String.valueOf(timeRemaining / 10);
        return seconds;// + ":" + millis;
    }

}
