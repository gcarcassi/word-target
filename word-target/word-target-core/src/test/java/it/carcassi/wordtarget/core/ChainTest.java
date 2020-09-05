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
public class ChainTest {

    @Test
    public void testValidChainCreation() {
        Chain chain = new Chain();
        chain.add(catBat);
        chain.add(batBaseball);
        assertEquals(2, chain.size());
        assertEquals(catBat, chain.get(0));
        assertEquals(batBaseball, chain.get(1));
    }

    @Test
    public void testInvalidChainCreation() {
        Chain chain = new Chain();
        chain.add(batBaseball);
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(catBat);
        });
    }

    @Test
    public void testValidChainAdding() {
        Chain chain = new Chain();
        chain.add(batBaseball);
        chain.prepend(catBat);
        assertEquals(catBat, chain.get(0));
    }

    @Test
    public void testValidChainAddingToEachOther() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain2.add(sportPorts);
        chain2.add(portsParts);
        chain1.add(chain2);
        assertEquals(5, chain1.size());
        assertEquals(catBat, chain1.get(0));
        assertEquals(batBaseball, chain1.get(1));
        assertEquals(baseballSport, chain1.get(2));
        assertEquals(sportPorts, chain1.get(3));
        assertEquals(portsParts, chain1.get(4));
    }

    @Test
    public void testInvalidChainAddingToEachOther() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain2.add(sportPorts);
        chain2.add(portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
        }

        @Test
    public void testAddingEmptyChainToChain() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain1.add(chain2);
        assertEquals(3, chain1.size());
        assertEquals(catBat, chain1.get(0));
        assertEquals(batBaseball, chain1.get(1));
        assertEquals(baseballSport, chain1.get(2));
    }

    @Test
    public void testAddingChainToEmptyChain() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain2.add(chain1);
        assertEquals(3, chain2.size());
        assertEquals(catBat, chain2.get(0));
        assertEquals(batBaseball, chain2.get(1));
        assertEquals(baseballSport, chain2.get(2));
    }

    @Test
    public void testValidChainAddingAtTheBeginning() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain2.add(sportPorts);
        chain2.add(portsParts);
        chain2.prepend(chain1);
        assertEquals(5, chain2.size());
        assertEquals(catBat, chain2.get(0));
        assertEquals(batBaseball, chain2.get(1));
        assertEquals(baseballSport, chain2.get(2));
        assertEquals(sportPorts, chain2.get(3));
        assertEquals(portsParts, chain2.get(4));
    }

    @Test
    public void testInvalidChainAddingAtTheBeginning() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        Chain chain2 = new Chain();
        chain2.add(sportPorts);
        chain2.add(portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.prepend(chain2);
        });
        }

        @Test
    public void testValidChainInversion() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        chain1.reverse();
        assertEquals(baseballSport, chain1.get(0));
        assertEquals(batBaseball, chain1.get(1));
        assertEquals(catBat, chain1.get(2));
    }

    @Test
    public void testEmptyChainInversion() {
        Chain chain2 = new Chain();
        chain2.reverse();
        assertEquals("", chain2.toString());
    }

    @Test
    public void testChainToStringConversion() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        assertEquals("CAT-BAT-BASEBALL-SPORT", chain1.toString());
        Chain chain2 = new Chain();
        assertEquals("", chain2.toString());
    }

    @Test
    public void testValidLinkRemovalFromChain() {
        Chain chain1 = new Chain();
        chain1.add(catBat);
        chain1.add(batBaseball);
        chain1.add(baseballSport);
        chain1.add(sportPorts);
        chain1.removeFirst();
        assertEquals(batBaseball, chain1.get(0));
        chain1.removeLast();
        assertEquals(batBaseball, chain1.get(0));
        assertEquals(baseballSport, chain1.get(1));
        assertEquals(2, chain1.size());
    }

    @Test
    public void testInvalidLinkRemovalFromChain() {
        Chain chain1 = new Chain();
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeFirst();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeLast();
                });
        }

        @Test
    public void testContains() {
        Chain chain1 = new Chain();
        assertFalse(chain1.contains(cat));
        chain1.add(catBat);
        chain1.add(batBaseball);
        assertTrue(chain1.contains(cat));
        assertTrue(chain1.contains(bat));
        assertTrue(chain1.contains(baseball));
        assertFalse(chain1.contains(sport));
        assertFalse(chain1.contains(ports));
    }
}
