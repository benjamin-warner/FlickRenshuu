package com.nihonkaeritai.flickrenshuu.repositories;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nihonkaeritai.flickrenshuu.R;

import java.util.Random;

public class KanaRepository {
    private Kana[] kanaDictionary;
    private int[] kanaOrder;
    private int currentKana;
    private TextView chisaiIndicator;

    public KanaRepository(AppCompatActivity context){
        initializeKanaDictionary(context);

        chisaiIndicator = (TextView)context.findViewById(R.id.chisaiIndicator);

        kanaOrder = new int[80];
        for(int i = 0; i < 80; i++){
            kanaOrder[i] = i;
        }
        currentKana = kanaOrder.length - 1;
    }

    private void initializeKanaDictionary(AppCompatActivity context){
        String[] kanaArray;
        kanaArray = context.getResources().getStringArray(R.array.kana);

        kanaDictionary = new Kana[80];
        for(int i = 0; i < kanaArray.length; i++){
            Kana kana = new Kana();
            kana.kana = kanaArray[i];
            //46-70 have diacritical
            if(i >= 46 && i <= 70)
                kana.hasDiacritical = true;
            //71-79 are chisai
            else if( i >= 71)
                kana.isChisai = true;
            else{
                kana.hasDiacritical = false;
                kana.isChisai = false;
            }
            kanaDictionary[i] = kana;
        }
    }

    public String getNextKana(){
        if(currentKana == kanaOrder.length - 1)
            generateOrderCombination();
        if(kanaDictionary[kanaOrder[currentKana]].isChisai)
            chisaiIndicator.setVisibility(View.VISIBLE);
        else
            chisaiIndicator.setVisibility(View.INVISIBLE);

        String thisKana = kanaDictionary[kanaOrder[currentKana]].kana;
        currentKana++;
        return thisKana;
    }

    public boolean inputIsEqualToKey(String input){
        return input.equals(kanaDictionary[kanaOrder[currentKana-1]].kana);
    }

    private void generateOrderCombination(){
        Random random = new Random();
        for(int i = kanaDictionary.length - 1; i > 0; i--){
            int randomIndex = random.nextInt((i+1));
            int old = kanaOrder[randomIndex];
            kanaOrder[randomIndex] = kanaOrder[i];
            kanaOrder[i] = old;
        }
        currentKana = 0;
    }

    private class Kana{
        public String kana;
        public boolean isChisai;
        public boolean hasDiacritical;
    }
}
