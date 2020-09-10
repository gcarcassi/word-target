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
 * @author Matteo
 */
public class LinkTypes {

    public static class LetterCounter {

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
    }

    public static LetterCounter countLetters(String text) {
        LetterCounter counter = new LetterCounter();
        for (char c : text.toCharArray()) {
            counter.addLetter(c);
        }
        return counter;
    }

    public static boolean isAnagram(String textA, String textB) {
        if (textA.length() != textB.length()) {
            return false;
        }

        if (textA == textB) {
            return false;
        }

        return countLetters(textA).equals(countLetters(textB));
    }

    public static boolean isOneLetterChange(String textA, String textB) {
        if (textA.length() != textB.length()) {
            return false;
        }

        boolean firstLetterChange = false;
        for (int i = 0; i < textA.length(); i++) {
            if (textA.charAt(i) != textB.charAt(i)) {
                if (firstLetterChange) {
                    return false;
                } else {
                    firstLetterChange = true;
                }

            }
        }
        return firstLetterChange;
    }

    public static boolean isOneLetterAdd(String textA, String textB) {
        if (textA.length() - textB.length() != -1) {
            return false;
        }

        boolean firstLetterAdded = false;
        for (int i = 0; i < textA.length(); i++) {
            int offset = firstLetterAdded ? 1 : 0;
            if (textA.charAt(i) != textB.charAt(i + offset)) {
                if (firstLetterAdded) {
                    return false;
                } else {
                    firstLetterAdded = true;
                    i--;
                }
            }
        }
        return true;
    }

    public static boolean isOneLetterRemove(String textA, String textB) {
        return isOneLetterAdd(textB, textA);
    }

    public static boolean isOneLetterAddOrRemove(String textA, String textB) {
        return isOneLetterAdd(textA, textB) || isOneLetterRemove(textA, textB);
    }
    
    public static LinkType calculateLinkType(Word word1, Word word2) {
        if (LinkTypes.isOneLetterAddOrRemove(word1.toString(), word2.toString())) {
            return LinkType.OneLetterAddOrRemove;
        }
        if (LinkTypes.isOneLetterChange(word1.toString(), word2.toString())) {
            return LinkType.OneLetterChange;
        }
        if (LinkTypes.isAnagram(word1.toString(), word2.toString())) {
            return LinkType.Anagram;
        }
        else {
            return null;
        }
    }

//    public static LinkType fromString(String token) {
//        switch (token) {
//            case "OneLetterChange":
//                return LinkType.OneLetterChange;
//            case "OneLetterAddOrRemove":
//                return LinkType.OneLetterAddOrRemove;
//            case "Anagram":
//                return LinkType.Anagram;
//            case "Synonym":
//                return LinkType.Synonym;
//            case "Antonym":
//                return LinkType.Antonym;
//            case "WordAssociation":
//                return LinkType.WordAssociation;
//            default:
//                throw new Exception("The string " + token + " does not correspond to a valid link type");
//        }
//    }
}
