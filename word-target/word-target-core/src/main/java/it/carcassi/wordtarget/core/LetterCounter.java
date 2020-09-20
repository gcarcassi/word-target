/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author carcassi
 */
public class LetterCounter {

    private Map<Character, Integer> counter = new HashMap<>();

    public void addLetter(char letter) {
        if (counter.containsKey(letter)) {
            counter.put(letter, counter.get(letter) + 1);
        } else {
            counter.put(letter, 1);
        }
    }

    public int countFor(char letter) {
        if (counter.containsKey(letter)) {
            return counter.get(letter);
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.counter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LetterCounter other = (LetterCounter) obj;
        if (!Objects.equals(this.counter, other.counter)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return counter.toString();
    }
    
    public static LetterCounter countLetters(String text) {
        return countLetters(Word.of(text));
    }
    
    public static LetterCounter countLetters(Word word) {
        LetterCounter counter = new LetterCounter();
        for (char c : word.getText().toCharArray()) {
            counter.addLetter(c);
        }
        return counter;
    }
    
}
