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

    // TODO: make sure you can't create a loop (i.e. link to a word already included in the chain
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
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(new Link(baseball, bat, LinkType.WordAssociation));
        });
    }

    @Test
    public void testValidChainAddingToEachOther() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = NewChain.of(sportPorts, portsParts);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport, sportPorts, portsParts), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport, ports, parts), chain1.words());
    }

    @Test
    public void testToString() {
        assertEquals("CAT -> CAT [1]", new NewChain(cat).toString());
        assertEquals("CAT -> SPORT [4]", NewChain.of(catBat, batBaseball, baseballSport).toString());
    }

    // TODO: use NewChain.of in all tests
    @Test
    public void testInvalidChainAddingToEachOther() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = NewChain.of(sportPorts, portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

    @Test
    public void testAddingEmptyChainToChain() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = new NewChain(sport);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport), chain1.words());
    }

    @Test
    public void testAddingInvalidEmptyChainToChain() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = new NewChain(ports);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

    @Test
    public void testAddingChainToEmptyChain() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = new NewChain(cat);
        chain2.add(chain1);
        assertEquals(List.of(catBat, batBaseball, baseballSport), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport), chain1.words());
    }

    @Test
    public void testValidChainAddingAtTheBeginning() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = NewChain.of(sportPorts, portsParts);
        chain2.prepend(chain1);
        assertEquals(6, chain2.size());
        assertEquals(List.of(catBat, batBaseball, baseballSport, sportPorts, portsParts), chain2.links());
    }

    @Test
    public void testInvalidChainAddingAtTheBeginning() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        NewChain chain2 = NewChain.of(sportPorts, portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.prepend(chain2);
        });
    }

        @Test
    public void testValidChainInversion() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport);
        chain1.reverse();
        assertEquals(List.of(baseballSport, batBaseball, catBat), chain1.links());
    }

    @Test
    public void testEmptyChainInversion() {
        NewChain chain2 = new NewChain(cat);
        chain2.reverse();
        assertEquals("CAT -> CAT [1]", chain2.toString());
    }

    @Test
    public void testValidLinkRemovalFromChain() {
        NewChain chain1 = NewChain.of(catBat, batBaseball, baseballSport, sportPorts);
        chain1.removeFirst();
        assertEquals(batBaseball, chain1.links().get(0));
        chain1.removeLast();
        assertEquals(batBaseball, chain1.links().get(0));
        assertEquals(baseballSport, chain1.links().get(1));
        assertEquals(3, chain1.size());
    }

    @Test
    public void testInvalidLinkRemovalFromChain() {
        NewChain chain1 = new NewChain(cat);
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeFirst();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeLast();
                });
        }

        @Test
    public void testContains() {
        NewChain chain1 = new NewChain(cat);
        assertTrue(chain1.words().contains(cat));
        chain1.add(catBat); 
        chain1.add(batBaseball);
        assertTrue(chain1.words().contains(cat));
        assertTrue(chain1.words().contains(bat));
        assertTrue(chain1.words().contains(baseball));
        assertFalse(chain1.words().contains(sport));
        assertFalse(chain1.words().contains(ports));
    }
}
