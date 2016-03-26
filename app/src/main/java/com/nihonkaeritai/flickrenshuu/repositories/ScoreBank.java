package com.nihonkaeritai.flickrenshuu.repositories;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreBank {
    private int points;
    private int _basePoints;
    private int _consecutiveMod;
    private TextView pointDisplay;

    public ScoreBank(int basePoints, int consecutiveBonus){
        points = 0;
        _basePoints = basePoints;
        _consecutiveMod = consecutiveBonus;
    }

    public void setDisplayReference(View displayView){
        pointDisplay = (TextView)displayView;
    }

    public void addPoints(int consecutiveCorrectInputs, int timeLeft){
        int thisConsecutiveMod = _consecutiveMod * consecutiveCorrectInputs;
        int timeBonus = timeLeft / 10;
        points += _basePoints + thisConsecutiveMod + timeBonus;
        Log.d("Points:", String.valueOf(points));

        updatePointDisplay();
    }

    private void updatePointDisplay() {
        if(pointDisplay != null){
            pointDisplay.setText(String.valueOf(points));
        }
    }
}
