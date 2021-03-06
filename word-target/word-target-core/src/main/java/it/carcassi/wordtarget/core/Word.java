/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a word.
 *
 * @author carcassi
 */
public class Word {
    private final String text;

    /**
     * Creates a new word with the given text, which is converted to uppercase.
     * 
     * @param text the text of the new word; can't be null
     */
    public Word(String text) {
        if (text == null)
            throw new NullPointerException("The text of the word cannot be null");
        
        this.text = text.trim().toUpperCase();
    }

    /**
     * Returns the text representation of the word, which is always uppercase.
     * 
     * @return the text representation; never null
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.text);
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
        final Word other = (Word) obj;
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return true;
    }
    
    
    // TODO add tests
    /**
     * Converts a String to a Word, returning null if the String has only
     * empty characters.
     * 
     * @param text
     * @return a Word or null
     */
    public static Word of(String text) {
        if (text.trim().isEmpty()) {
            return null;
        } else {
            return new Word(text);
        }
    }

    public static List<Word> of(String... texts) {
        List<Word> words = new ArrayList<>();
        for (String text : texts) {
            Word word = Word.of(text);
            if (word != null) {
                words.add(word);
            }
        }
        return words;
    }
    
}
