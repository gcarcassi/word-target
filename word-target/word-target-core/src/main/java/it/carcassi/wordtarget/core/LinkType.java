/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

/**
 *
 * @author Matteo
 */
public enum LinkType {
    OneLetterChange,
    OneLetterAddOrRemove,
    Anagram,
    Synonym,
    Antonym,
    WordAssociation;

    public boolean isAutomatic() {
        switch (this) {
            case OneLetterChange:
            case OneLetterAddOrRemove:
            case Anagram:
                return true;
            case Synonym:
            case Antonym:
            case WordAssociation:
            default:
                return false;
        }
    }
}
