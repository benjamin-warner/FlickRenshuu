package com.nihonkaeritai.flickrenshuu.repositories;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.R;

import java.util.Random;

public class KanaRepository {
    private Kana[] kanaDictionary;
    private int currentKanaIndex;
    private TextView kanaKey;
    private TextView chisaiIndicator;

    public KanaRepository(Context context, View keyView, View chisaiIndicatorView){
        initializeKanaDictionary(context);

        kanaKey = (TextView)keyView;
        chisaiIndicator = (TextView)chisaiIndicatorView;

        currentKanaIndex = kanaDictionary.length - 1;
    }

    private void initializeKanaDictionary(Context context){
        String[] kana = context.getResources().getStringArray(R.array.kana);
        kanaDictionary = new Kana[80];
        for(int i = 0; i < kana.length; i++){
            Kana k = new Kana();
            k.kana = Character.toString(kana[i].charAt(0));
            if(kana[i].length() > 1)
                k.parent = Character.toString(kana[i].charAt(1));
            if(i >= 71)
                k.isChisai = true;
            kanaDictionary[i] = k;
        }
    }

    public void getNextKana(){
        if(currentKanaIndex >= kanaDictionary.length - 1){
            generateOrderCombination();
            currentKanaIndex = -1;
        }
        if(kanaDictionary[currentKanaIndex+1].isChisai)
            chisaiIndicator.setVisibility(View.VISIBLE);
        else
            chisaiIndicator.setVisibility(View.INVISIBLE);

        currentKanaIndex++;
        kanaKey.setText(kanaDictionary[currentKanaIndex].kana);
        kanaKey.setTextColor(0xFF000000);
    }

    public int getInputMatchStatus(String input){
        if(input.equals(kanaDictionary[currentKanaIndex].kana)) {
            return MatchStatus.correct;
        }
        else if(input.equals(kanaDictionary[currentKanaIndex].parent)) {
            return MatchStatus.inProgress;
        }
        kanaKey.setTextColor(0xFFFF0000);
        return MatchStatus.incorrect;
    }

    private void generateOrderCombination(){
        Random random = new Random();
        for(int i = kanaDictionary.length - 1; i > 0; i--){
            int randomIndex = random.nextInt((i+1));
            Kana old = kanaDictionary[randomIndex];
            kanaDictionary[randomIndex] = kanaDictionary[i];
            kanaDictionary[i] = old;
        }
    }

    private class Kana{
        public String kana;
        public String parent;
        public boolean isChisai;
    }

    public static class MatchStatus {
        public static int incorrect = 0;
        public static int correct = 1;
        public static int inProgress = 2;
    }
}
