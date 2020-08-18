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

    public static bool IsAnagram(string textA, string textB) {
        if (textA.Length != textB.Length) {
            return false;
        }

        if (textA == textB) {
            return false;
        }

        return CountLetters(textA).Equals(CountLetters(textB));
    }

    public static bool IsOneLetterChange(string textA, string textB) {
        if (textA.Length != textB.Length) {
            return false;
        }

        bool firstLetterChange = false;
        for (int i = 0; i < textA.Length; i++) {
            if (textA[i] != textB[i]) {
                if (firstLetterChange) {
                    return false;
                } else {
                    firstLetterChange = true;
                }

            }
        }
        return firstLetterChange;
    }

    public static bool IsOneLetterAdd(string textA, string textB) {
        if (textA.Length - textB.Length != -1) {
            return false;
        }

        bool firstLetterAdded = false;
        for (int i = 0; i < textA.Length; i++) {
            int offset = firstLetterAdded ? 1 : 0;
            if (textA[i] != textB[i + offset]) {
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

    public static bool IsOneLetterRemove(string textA, string textB) {
        return IsOneLetterAdd(textB, textA);
    }

    public static bool IsOneLetterAddOrRemove(string textA, string textB) {
        return IsOneLetterAdd(textA, textB) || IsOneLetterRemove(textA, textB);
    }

    public static LinkType FromString(string token) {
        switch (token) {
            case "OneLetterChange":
                return LinkType.OneLetterChange;
            case "OneLetterAddOrRemove":
                return LinkType.OneLetterAddOrRemove;
            case "Anagram":
                return LinkType.Anagram;
            case "Synonym":
                return LinkType.Synonym;
            case "Antonym":
                return LinkType.Antonym;
            case "WordAssociation":
                return LinkType.WordAssociation;
            default:
                throw new Exception("The string " + token + " does not correspond to a valid link type");
        }
    }
}
}
