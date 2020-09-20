/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static it.carcassi.wordtarget.core.LetterCounter.*;

/**
 *
 * @author Matteo
 */
public class LinkTypes {

    public static boolean isAnagram(String textA, String textB) {
        return isAnagram(Word.of(textA), Word.of(textB));
    }
    
    public static boolean isAnagram(Word wordA, Word wordB) {
        if (wordA.getText().length() != wordB.getText().length()) {
            return false;
        }

        if (wordA.equals(wordB)) {
            return false;
        }

        return countLetters(wordA).equals(countLetters(wordB));
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
