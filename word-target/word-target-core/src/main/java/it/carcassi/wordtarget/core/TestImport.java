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
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author carcassi
 */
public class TestImport {
    
    public static Map<BigInteger, Set<Word>> wordsByHist = new HashMap<>();
    
    public static void addWord(Word word, BigInteger hist) {
        Set<Word> set = wordsByHist.get(hist);
        if (set == null) {
            set = new HashSet<>();
            wordsByHist.put(hist, set);
        }
        set.add(word);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File file = new File("C:\\Users\\carcassi\\Desktop\\Temp\\sowpods.txt\\sowpods.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        WordDatabase db = new WordDatabase();
        List<Word> list = new ArrayList<>();
        Set<Word> set = new HashSet<>();
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
        int i = 0;
//        while (reader.ready()) {
//            Word word = Word.of(reader.readLine());
//            LetterCounter countLetters = LetterCounter.countLetters(word);
//            addWord(word, countLetters.toBigInteger());
////            System.out.println(word + " " + index);
//        }
//        System.out.println((System.currentTimeMillis() - startTime));
//        for (Map.Entry<BigInteger, Set<Word>> entry : wordsByHist.entrySet()) {
//            Set<Word> val = entry.getValue();
////            if (val.size() > 1) {
//                System.out.println(val);
////            }
//        }
        while (reader.ready()) {
//            db.addWord(Word.of(reader.readLine()));
            list.add(Word.of(reader.readLine()));
//            i++;
//            if (i % 100 == 0) {
//                System.out.println(i + " " + (i * 1000) / (System.currentTimeMillis() - startTime));
//            }
        }
        db = WordDatabase.of(list);
        //db.addWords(list);
        System.out.println((System.currentTimeMillis() - startTime));
        System.out.println(db.getAllWords().size());
        System.out.println(db.getLinksFor(Word.of("SPORT")));
    }
}
