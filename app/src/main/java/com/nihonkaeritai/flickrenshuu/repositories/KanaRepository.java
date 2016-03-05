package com.nihonkaeritai.flickrenshuu.repositories;

import android.content.Context;
import android.util.Log;

import com.nihonkaeritai.flickrenshuu.R;

import java.util.Random;

public class KanaRepository {
    private static final int NUMBER_OF_CHISAI_KANA = 8;
    private static String[] kanaArray;
    private static int[] kanaOrder;
    private static int currentKana;

    public static void initialize(Context context){
        kanaArray = context.getResources().getStringArray(R.array.kana);

        kanaOrder = new int[80];
        for(int i = 0; i < 80; i++){
            kanaOrder[i] = i;
        }
        currentKana = kanaOrder.length - 1;
    }

    public static String getNextKana(){
        if(currentKana == kanaOrder.length - 1)
            generateOrderCombination();
        String thisKana = kanaArray[kanaOrder[currentKana]];
        currentKana++;
        return thisKana;
    }

    private static void generateOrderCombination(){
        Random random = new Random();
        for(int i = kanaArray.length - 1; i > 0; i--){
            int randomIndex = random.nextInt((i+1));
            int old = kanaOrder[randomIndex];
            kanaOrder[randomIndex] = kanaOrder[i];
            kanaOrder[i] = old;
        }
        currentKana = 0;
    }

    public static boolean isIndexChisaiKana(){
        return kanaOrder[currentKana-1] >= kanaArray.length - 1 - NUMBER_OF_CHISAI_KANA;
    }
}
