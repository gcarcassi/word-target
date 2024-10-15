/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author carcassi
 */
public class LongAnagramFinder {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\carcassi\\Documents\\GitHub\\word-target\\databases\\full.wtdb");
        
        WordDatabase db = WordDatabase.deserialize(new BufferedReader(new FileReader(file)));
        for (Word word : db.getAllWords().stream().sorted(Comparator.comparing(Word::getText)).collect(Collectors.toList())) {
            if (word.getText().length() > 10) {
                LetterCounter counter = LetterCounter.countLetters(word);
                BigInteger index = counter.toBigInteger();
                Set<Word> anagrams = db.wordsByHist.get(index);
                if (anagrams.size() > 1) {
                    System.out.println(anagrams);
                }
            }
        }
    }
}
