/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static it.carcassi.wordtarget.core.CommonTestObjects.*;

/**
 *
 * @author Matteo
 */
public class LinkTest {

    @Test
    public void linkCreation() {

        Link link = new Link(cat, bat, LinkType.OneLetterChange);
        assertEquals(cat, link.getWordA());
        assertEquals(bat, link.getWordB());
        assertEquals(LinkType.OneLetterChange, link.getType());
    }

    @Test
    public void verifyAnagram() {
        Link link = new Link(bat, tab, LinkType.Anagram);
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, bat, LinkType.Anagram);
        });
    }

    @Test
    public void verifyOneLetterChange() {
        Link link = new Link(cat, bat, LinkType.OneLetterChange);
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, brat, LinkType.OneLetterChange);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, bit, LinkType.OneLetterChange);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, cat, LinkType.OneLetterChange);
        });
    }

    @Test
    public void verifyOneLetterAddOrRemove() {
        Link link1 = new Link(cat, cart, LinkType.OneLetterAddOrRemove);
        Link link2 = new Link(cart, cat, LinkType.OneLetterAddOrRemove);
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, brat, LinkType.OneLetterChange);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, car, LinkType.OneLetterChange);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(cat, cat, LinkType.OneLetterChange);
        });
    }

    @Test
    public void linkStingConversion() {
        Link link = new Link(cat, bat, LinkType.OneLetterChange);
        assertEquals("CAT-BAT", link.toString());
    }

    @Test
    public void validLinkEquivalency() {
        Link link1 = new Link(cat, bat, LinkType.OneLetterChange);
        Link link2 = new Link(cat, bat, LinkType.OneLetterChange);
        assertEquals(link1, link2);
        assertEquals(link1.hashCode(), link2.hashCode());
    }

    @Test
    public void validLinkInversionForOneLetterChange() {
        Link link1 = new Link(cat, bat, LinkType.OneLetterChange);
        Link link2 = link1.Reverse();
        assertEquals(cat, link1.getWordA());
        assertEquals(bat, link1.getWordB());
        assertEquals(bat, link2.getWordA());
        assertEquals(cat, link2.getWordB());
    }
}
