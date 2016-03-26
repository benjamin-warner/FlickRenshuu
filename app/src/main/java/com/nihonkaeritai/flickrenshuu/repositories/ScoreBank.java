package com.nihonkaeritai.flickrenshuu.repositories;

import android.util.Log;

public class ScoreBank {
    private int points;
    private int _basePoints;
    private int _consecutiveMod;

    public ScoreBank(int basePoints, int consecutiveBonus){
        points = 0;
        _basePoints = basePoints;
        _consecutiveMod = 10;
    }

    public void addPoints(int consecutiveCorrectInputs, int timeLeft){
        int thisConsecutiveMod = _consecutiveMod * consecutiveCorrectInputs;
        int timeBonus = timeLeft / 10;
        points += _basePoints + thisConsecutiveMod + timeBonus;
        Log.d("Points:", String.valueOf(points));
    }
}
