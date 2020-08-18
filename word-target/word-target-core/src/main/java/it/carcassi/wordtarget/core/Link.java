/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.Objects;

/**
 *
 * @author Matteo
 */
public class Link {

    private final Word wordA;
    private final Word wordB;
    private final LinkType type;

    public Link(Word wordA, Word wordB, LinkType type) {
        this.wordA = wordA;
        this.wordB = wordB;
        this.type = type;

        if (type == LinkType.Anagram && !LinkTypes.isAnagram(wordA.getText(), wordB.getText())) {
            throw new IllegalArgumentException(wordA.getText() + " is not an anagram of " + wordB.getText());
        }
        if (type == LinkType.OneLetterChange && !LinkTypes.isOneLetterChange(wordA.getText(), wordB.getText())) {
            throw new IllegalArgumentException(wordA.getText() + " is not a letter change from " + wordB.getText());
        }
        if (type == LinkType.OneLetterAddOrRemove && !LinkTypes.isOneLetterAddOrRemove(wordA.getText(), wordB.getText())) {
            throw new IllegalArgumentException(wordA.getText() + " is not a letter additon or subtraction to " + wordB.getText());
        }

    }

    @Override
    public String toString() {
        return wordA + "-" + wordB;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            Link other = (Link)obj;
            return wordA.equals(other.wordA) && wordB.equals(other.wordB) && type.equals(other.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.wordA);
        hash = 19 * hash + Objects.hashCode(this.wordB);
        hash = 19 * hash + Objects.hashCode(this.type);
        return hash;
    }
    

    public Link Reverse() {
        return new Link(this.wordB, this.wordA, this.type);
    }
}

