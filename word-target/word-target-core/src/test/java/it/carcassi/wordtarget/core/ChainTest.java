/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static it.carcassi.wordtarget.core.CommonTestObjects.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author Matteo
 */
public class ChainTest {

    @Test
    public void testValidChainCreation() {
        Chain chain = new Chain(cat);
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
        Chain chain = new Chain(cat);
        assertEquals(1, chain.size());
        assertEquals(List.of(), chain.links());
        assertEquals(List.of(cat), chain.words());
        assertEquals(cat, chain.getInitialWord());
        assertEquals(cat, chain.getFinalWord());
    }

    @Test
    public void testInvalidChainCreation() {
        Chain chain = new Chain(bat);
        chain.add(batBaseball);
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(catBat);
        });
    }

    @Test
    public void testInvalidChainCreation2() {
        Chain chain = new Chain(bat);
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(catBat);
        });
    }

    @Test
    public void testValidChainAdding() {
        Chain chain = new Chain(bat);
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
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = Chain.of(sportPorts, portsParts);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport, sportPorts, portsParts), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport, ports, parts), chain1.words());
    }

    @Test
    public void testToString() {
        assertEquals("CAT -> CAT [1]", new Chain(cat).toString());
        assertEquals("CAT -> SPORT [4]", Chain.of(catBat, batBaseball, baseballSport).toString());
    }

    @Test
    public void testInvalidChainAddingToEachOther() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = Chain.of(sportPorts, portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

    @Test
    public void testAddingEmptyChainToChain() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = new Chain(sport);
        chain1.add(chain2);
        assertEquals(List.of(catBat, batBaseball, baseballSport), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport), chain1.words());
    }

    @Test
    public void testAddingInvalidEmptyChainToChain() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = new Chain(ports);
        assertThrows(IllegalArgumentException.class, () -> {
            chain2.add(chain1);
        });
    }

    @Test
    public void testAddingChainToEmptyChain() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = new Chain(cat);
        chain2.add(chain1);
        assertEquals(List.of(catBat, batBaseball, baseballSport), chain1.links());
        assertEquals(List.of(cat, bat, baseball, sport), chain1.words());
    }

    @Test
    public void testValidChainAddingAtTheBeginning() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = Chain.of(sportPorts, portsParts);
        chain2.prepend(chain1);
        assertEquals(6, chain2.size());
        assertEquals(List.of(catBat, batBaseball, baseballSport, sportPorts, portsParts), chain2.links());
    }

    @Test
    public void testInvalidChainAddingAtTheBeginning() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        Chain chain2 = Chain.of(sportPorts, portsParts);
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.prepend(chain2);
        });
    }

    @Test
    public void testValidChainInversion() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport);
        chain1.reverse();
        assertEquals(List.of(sportBaseball, baseballBat, batCat), chain1.links());
    }

    @Test
    public void testEmptyChainInversion() {
        Chain chain2 = new Chain(cat);
        chain2.reverse();
        assertEquals("CAT -> CAT [1]", chain2.toString());
    }

    @Test
    public void testValidLinkRemovalFromChain() {
        Chain chain1 = Chain.of(catBat, batBaseball, baseballSport, sportPorts);
        chain1.removeFirst();
        assertEquals(batBaseball, chain1.links().get(0));
        chain1.removeLast();
        assertEquals(batBaseball, chain1.links().get(0));
        assertEquals(baseballSport, chain1.links().get(1));
        assertEquals(3, chain1.size());
    }

    @Test
    public void testInvalidLinkRemovalFromChain() {
        Chain chain1 = new Chain(cat);
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeFirst();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            chain1.removeLast();
        });
    }

    @Test
    public void testContains() {
        Chain chain1 = new Chain(cat);
        assertTrue(chain1.words().contains(cat));
        chain1.add(catBat);
        chain1.add(batBaseball);
        assertTrue(chain1.words().contains(cat));
        assertTrue(chain1.words().contains(bat));
        assertTrue(chain1.words().contains(baseball));
        assertFalse(chain1.words().contains(sport));
        assertFalse(chain1.words().contains(ports));
    }

    @Test
    public void testSerializeChain() throws IOException {
        Chain chain = Chain.of(catBat, batBaseball, baseballSport, sportPorts, portsPort);
        String expected = """
CAT BAT OneLetterChange
BAT BASEBALL WordAssociation
BASEBALL SPORT WordAssociation
SPORT PORTS Anagram
PORTS PORT OneLetterAddOrRemove
                          """;
        StringWriter sw = new StringWriter();
        BufferedWriter writer = new BufferedWriter(sw);
        chain.serialize(writer);
        writer.flush();
        assertEquals(expected, sw.toString());
    }
    
    @Test
    public void testDeserialzeChain() throws IOException {
        String savedChain = """
CAT BAT OneLetterChange
BAT BASEBALL WordAssociation
BASEBALL SPORT WordAssociation
SPORT PORTS Anagram
PORTS PORT OneLetterAddOrRemove
                          """;
        StringReader sr = new StringReader(savedChain);
        BufferedReader reader = new BufferedReader(sr);
        Chain chain = Chain.deserialize(reader);
        Chain chain2 = Chain.of(catBat, batBaseball, baseballSport, sportPorts, portsPort);
        assertEquals(chain2, chain);
    }
}
