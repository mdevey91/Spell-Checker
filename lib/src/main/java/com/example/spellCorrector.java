package com.example;

import java.io.IOException;

/**
 * Created by devey on 1/20/17.
 */

public class spellCorrector implements ISpellCorrector {

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        return null;
    }
}
