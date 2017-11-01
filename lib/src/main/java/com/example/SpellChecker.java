package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

class SpellChecker implements ISpellCorrector{
    private TreeSet<String> one_edit_distance_away;
    private TreeSet<String> not_one_edit_distance_away;
    private Trie Dictionary;
    private boolean first_time_running;
    public SpellChecker()
    {
        one_edit_distance_away = new TreeSet<String>(new MyComp());
        not_one_edit_distance_away = new TreeSet<String>();
        first_time_running = true;
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        Scanner sc = new Scanner(new File(dictionaryFileName));
        Dictionary = new Trie();
        while(sc.hasNext())
        {
            Dictionary.add(sc.next().toLowerCase());
        }
    }
    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException
    {
        String lower_case_word = inputWord.toLowerCase();
        if(Dictionary == null)
            throw new NoSimilarWordFoundException();
        if(Dictionary.find(inputWord) != null)
            return inputWord;
        delete(lower_case_word);
        insert(lower_case_word);
        alterate(lower_case_word);
        transpose(lower_case_word);
        if(one_edit_distance_away.isEmpty())
        {
            for(String word: not_one_edit_distance_away)
            {
                delete(word);
                insert(word);
                alterate(word);
                transpose(word);
            }
        }
        if(one_edit_distance_away.isEmpty())
            throw new NoSimilarWordFoundException();
        else
            return one_edit_distance_away.first();
    }
    private void delete(String word)
    {
        StringBuilder new_word;
        for(int i = 0; i < word.length(); i++)
        {
            new_word = new StringBuilder(word);
            new_word.deleteCharAt(i);
            if(Dictionary.find(new_word.toString()) != null)
                one_edit_distance_away.add(new_word.toString());
            else if(first_time_running)
                not_one_edit_distance_away.add(new_word.toString());
        }
        first_time_running = false;
    }
    private void insert(String word)
    {
        StringBuilder new_word;
        new_word = new StringBuilder(word);
        char c;
        for(int i = 0; i < word.length() + 1; i++)
        {
            for(int j = 0; j < 26;j++)
            {
                //resetting the string builder back to the original word
                new_word.setLength(0);
                new_word.append(word);
                c = (char)('a' + j);
                new_word.insert(i, c);
               if(Dictionary.find(new_word.toString()) != null)
                   one_edit_distance_away.add(new_word.toString());
               else if(first_time_running)
                   not_one_edit_distance_away.add(new_word.toString());
            }
        }
    }
    private void alterate(String word)
    {
        StringBuilder new_word;
        new_word = new StringBuilder(word);
        char c;
        for(int i = 0; i < word.length(); i++)
        {
            for(int j = 0; j < 26; j++)
            {
                //resetting the string builder back to the original word
                new_word.setLength(0);
                new_word.append(word.toLowerCase());
                c = (char)('a' + j);
                new_word.setCharAt(i, c);
                if(Dictionary.find(new_word.toString()) != null)
                    one_edit_distance_away.add(new_word.toString());
                else if(first_time_running)
                    not_one_edit_distance_away.add(new_word.toString());
            }
        }
    }
    private void transpose(String word)
    {
        StringBuilder new_word;
        new_word = new StringBuilder(word);
        char first_char;
        char second_char;
        for(int i = 0; i < word.length() - 1; i++)
        {
            //resetting the string builder back to the original word
            new_word.setLength(0);
            new_word.append(word.toLowerCase());
            first_char = new_word.charAt(i);
            second_char = new_word.charAt(i+1);
            new_word.setCharAt(i, second_char);
            new_word.setCharAt(i+1, first_char);
            if(Dictionary.find(new_word.toString()) != null)
                one_edit_distance_away.add(new_word.toString());
            else if(first_time_running)
                not_one_edit_distance_away.add(new_word.toString());
        }
    }
    class MyComp implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            if(Dictionary.find(str1).getValue() > Dictionary.find(str2).getValue())
                return -1;
            else if(Dictionary.find(str1).getValue() < Dictionary.find(str2).getValue())
                return 1;
            else
                return str1.compareTo(str2);
        }

    }
}
