package com.nihonkaeritai.flickrenshuu.repositories;

import android.content.Context;

import com.nihonkaeritai.flickrenshuu.R;

public class KanaRepository {
    public static String[] kanaArray;
    public static final int NUMBER_OF_CHISAI_KANA = 9;

    public KanaRepository(Context context){
        kanaArray = context.getResources().getStringArray(R.array.kana);
    }

    public int getLength(){
        return kanaArray.length;
    }

    public int getNumberOfChisaiKana(){
        return NUMBER_OF_CHISAI_KANA;
    }

    public boolean isIndexChisaiKana(int index){
        return index >= getLength() - getNumberOfChisaiKana();
    }
}
