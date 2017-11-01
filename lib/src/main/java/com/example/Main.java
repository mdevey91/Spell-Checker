package com.example;

import com.example.ISpellCorrector.NoSimilarWordFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		String similar_word;
		similar_word = "fart";
		/**
		 * Create an instance of your corrector here
		 */
		Trie Dictionary = new Trie();
		Trie Dictionary_clone = new Trie();
		Trie empty_dictionary = new Trie();
		empty_dictionary.add(similar_word);
		Scanner sc = new Scanner(new File(args[0]));
		ISpellCorrector corrector = null;
		while(sc.hasNext())
		{
			Dictionary.add(sc.next());
		}
		Dictionary_clone = Dictionary;
		if(Dictionary.equals(empty_dictionary))
			System.out.println("Objects are same!");
		else
			System.out.println("Objects are not the same");
//		System.out.println(Dictionary.toString());
		System.out.println("Number of words " + Dictionary.getWordCount());
		SpellChecker my_spell_checker = new SpellChecker();
		my_spell_checker.useDictionary(dictionaryFileName);
		similar_word = my_spell_checker.suggestSimilarWord("Rann");

		System.out.println("suggested word: " + similar_word);
//		corrector.useDictionary(dictionaryFileName);
//		String suggestion = null;
//		try {
//			suggestion = corrector.suggestSimilarWord(inputWord);
//		} catch (ISpellCorrector.NoSimilarWordFoundException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Suggestion is: " + suggestion);
	}

}
