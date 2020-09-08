/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static it.carcassi.wordtarget.core.CommonTestObjects.*;
import java.util.List;

/**
 *
 * @author Matteo
 */
public class NewChainTest {

    @Test
    public void testValidChainCreation() {
        NewChain chain = new NewChain(cat);
        chain.add(catBat);
        chain.add(batBaseball);
        assertEquals(3, chain.size());
        assertEquals(List.of(catBat, batBaseball), chain.links());
        assertEquals(List.of(cat, bat, baseball), chain.words());
        assertEquals(cat, chain.getInitialWord());
        assertEquals(baseball, chain.getFinalWord());
    }

    @Test
    public void testValidEmptyChainCreation() {
        NewChain chain = new NewChain(cat);
        assertEquals(1, chain.size());
        assertEquals(List.of(), chain.links());
        assertEquals(List.of(cat), chain.words());
        assertEquals(cat, chain.getInitialWord());
        assertEquals(cat, chain.getFinalWord());
    }

    @Test
    public void testInvalidChainCreation() {
        NewChain chain = new NewChain(bat);
        chain.add(batBaseball);
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(catBat);
        });
    }

    @Test
    public void testInvalidChainCreation2() {
        NewChain chain = new NewChain(bat);
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(catBat);
        });
    }

    @Test
    public void testValidChainAdding() {
        NewChain chain = new NewChain(bat);
        chain.add(batBaseball);
        chain.prepend(catBat);
        assertEquals(List.of(catBat, batBaseball), chain.links());
        assertEquals(List.of(cat, bat, baseball), chain.words());
    }

    @Test
    public void testValidChainAddingToEachOther() {
        NewChain chain1 = new NewChain(cat);
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        NewChain chain2 = new NewChain(sport);
        chain2.add(sportPorts);
        chain2.add(portsParts);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport, sportPorts, portsParts), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport, ports, parts), chain1.words());
    }

    @Test
    public void testInvalidChainAddingToEachOther() {
        NewChain chain1 = new NewChain(cat);
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        NewChain chain2 = new NewChain(sport);
        chain2.add(sportPorts);
        chain2.add(portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

    @Test
    public void testAddingEmptyChainToChain() {
        NewChain chain1 = new NewChain(cat);
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        NewChain chain2 = new NewChain(sport);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport), chain1.words());
    }

    @Test
    public void testAddingInvalidEmptyChainToChain() {
        NewChain chain1 = new NewChain(cat);
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        NewChain chain2 = new NewChain(ports);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

//    @Test
//    public void testAddingChainToEmptyChain() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        Chain chain2 = new Chain();
//        chain2.add(chain1);
//        assertEquals(3, chain2.size());
//        assertEquals(catBat, chain2.get(0));
//        assertEquals(batBaseball, chain2.get(1));
//        assertEquals(baseballSport, chain2.get(2));
//    }
//
//    @Test
//    public void testValidChainAddingAtTheBeginning() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        Chain chain2 = new Chain();
//        chain2.add(sportPorts);
//        chain2.add(portsParts);
//        chain2.prepend(chain1);
//        assertEquals(5, chain2.size());
//        assertEquals(catBat, chain2.get(0));
//        assertEquals(batBaseball, chain2.get(1));
//        assertEquals(baseballSport, chain2.get(2));
//        assertEquals(sportPorts, chain2.get(3));
//        assertEquals(portsParts, chain2.get(4));
//    }
//
//    @Test
//    public void testInvalidChainAddingAtTheBeginning() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        Chain chain2 = new Chain();
//        chain2.add(sportPorts);
//        chain2.add(portsParts);
//        assertThrows(IllegalArgumentException.class, () -> {
//            chain1.prepend(chain2);
//        });
//        }
//
//        @Test
//    public void testValidChainInversion() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        chain1.reverse();
//        assertEquals(baseballSport, chain1.get(0));
//        assertEquals(batBaseball, chain1.get(1));
//        assertEquals(catBat, chain1.get(2));
//    }
//
//    @Test
//    public void testEmptyChainInversion() {
//        Chain chain2 = new Chain();
//        chain2.reverse();
//        assertEquals("", chain2.toString());
//    }
//
//    @Test
//    public void testChainToStringConversion() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        assertEquals("CAT-BAT-BASEBALL-SPORT", chain1.toString());
//        Chain chain2 = new Chain();
//        assertEquals("", chain2.toString());
//    }
//
//    @Test
//    public void testValidLinkRemovalFromChain() {
//        Chain chain1 = new Chain();
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        chain1.add(baseballSport);
//        chain1.add(sportPorts);
//        chain1.removeFirst();
//        assertEquals(batBaseball, chain1.get(0));
//        chain1.removeLast();
//        assertEquals(batBaseball, chain1.get(0));
//        assertEquals(baseballSport, chain1.get(1));
//        assertEquals(2, chain1.size());
//    }
//
//    @Test
//    public void testInvalidLinkRemovalFromChain() {
//        Chain chain1 = new Chain();
//        assertThrows(IllegalArgumentException.class, () -> {
//            chain1.removeFirst();
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            chain1.removeLast();
//                });
//        }
//
//        @Test
//    public void testContains() {
//        Chain chain1 = new Chain();
//        assertFalse(chain1.contains(cat));
//        chain1.add(catBat);
//        chain1.add(batBaseball);
//        assertTrue(chain1.contains(cat));
//        assertTrue(chain1.contains(bat));
//        assertTrue(chain1.contains(baseball));
//        assertFalse(chain1.contains(sport));
//        assertFalse(chain1.contains(ports));
//    }
}
