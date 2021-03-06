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
public class LinkTypesTest {

    @Test
    public void testIsAnagram() {
        assertTrue(LinkTypes.isAnagram("bat", "tab"));
        assertTrue(LinkTypes.isAnagram("angel", "angle"));
        assertFalse(LinkTypes.isAnagram("cat", "angle"));
        assertFalse(LinkTypes.isAnagram("cat", "cat"));
        assertTrue(LinkTypes.isAnagram("red sun", "nursed"));
        assertTrue(LinkTypes.isAnagram("nursed", "red sun"));
        assertTrue(LinkTypes.isAnagram("won't", "town"));
        assertTrue(LinkTypes.isAnagram("town", "won't"));
    }

    @Test
    public void testIsOneLetterChange() {
        assertTrue(LinkTypes.isOneLetterChange("moon", "moan"));
        assertTrue(LinkTypes.isOneLetterChange("cart", "care"));
        assertFalse(LinkTypes.isOneLetterChange("cat", "bit"));
        assertFalse(LinkTypes.isOneLetterChange("cat", "angle"));
        assertFalse(LinkTypes.isOneLetterChange("cat", "cat"));
    }

    @Test
    public void testIsOneLetterAdd() {
        assertTrue(LinkTypes.isOneLetterAdd("pan", "pain"));
        assertTrue(LinkTypes.isOneLetterAdd("car", "cart"));
        assertFalse(LinkTypes.isOneLetterAdd("cat", "bite"));
        assertFalse(LinkTypes.isOneLetterAdd("raw", "straw"));
        assertFalse(LinkTypes.isOneLetterAdd("cat", "cat"));
    }

    @Test
    public void testIsOneLetterRemove() {
        assertTrue(LinkTypes.isOneLetterRemove("pain", "pan"));
        assertTrue(LinkTypes.isOneLetterRemove("cart", "car"));
        assertFalse(LinkTypes.isOneLetterRemove("cat", "bite"));
        assertFalse(LinkTypes.isOneLetterRemove("straw", "raw"));
        assertFalse(LinkTypes.isOneLetterRemove("cat", "cat"));
    }

    @Test
    public void testToString1() {
        assertEquals("OneLetterChange", LinkType.OneLetterChange.toString());
        assertEquals("OneLetterAddOrRemove", LinkType.OneLetterAddOrRemove.toString());
        assertEquals("Anagram", LinkType.Anagram.toString());
        assertEquals("Synonym", LinkType.Synonym.toString());
        assertEquals("Antonym", LinkType.Antonym.toString());
        assertEquals("WordAssociation", LinkType.WordAssociation.toString());
    }
    
    @Test
    public void testCalculateLinkType() {
        assertEquals(LinkType.OneLetterChange, LinkTypes.calculateLinkType(cat, bat));
        assertEquals(LinkType.Anagram, LinkTypes.calculateLinkType(sport, ports));
        assertEquals(LinkType.OneLetterAddOrRemove, LinkTypes.calculateLinkType(port, ports));
        assertEquals(null, LinkTypes.calculateLinkType(bat, baseball));
    }

////        @Test
////        public void fromString1() {
////        assertEquals(LinkType.OneLetterChange, LinkTypes.fromString("OneLetterChange"));
////        assertEquals(LinkType.OneLetterAddOrRemove, LinkTypes.fromString("OneLetterAddOrRemove"));
////        assertEquals(LinkType.Anagram, LinkTypes.fromString("Anagram"));
////        assertEquals(LinkType.Synonym, LinkTypes.fromString("Synonym"));
////        assertEquals(LinkType.Antonym, LinkTypes.fromString("Antonym"));
////        assertEquals(LinkType.WordAssociation, LinkTypes.fromString("WordAssociation"));
////        Assert.ThrowsException<System.Exception>
////        (() => LinkTypes.fromString("Wrong"));
//        }

}
