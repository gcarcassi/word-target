/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static it.carcassi.wordtarget.core.CommonTestObjects.*;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Matteo
 */
public class WordDatabaseTest {

    @Test
    public void testPopulatingDatabase() {
        WordDatabase db = new WordDatabase();
        db.addWord(cat);
        db.addWords(Arrays.asList(bat, baseball, sport, ports, port));
        assertEquals(Set.of(cat, bat, baseball, sport, ports, port), db.getAllWords());
        Set<Link> batLinks = db.getLinksFor(bat);
        assertEquals(1, batLinks.size());
        assertTrue(batLinks.contains(catBat.reverse()));
        Set<Link> portsLinks = db.getLinksFor(ports);
        assertEquals(2, portsLinks.size());
        assertTrue(portsLinks.contains(new Link(ports, sport, LinkType.Anagram)));
        assertTrue(portsLinks.contains(new Link(ports, port, LinkType.OneLetterAddOrRemove)));
    }

    @Test
    public void testSerializeDatabase() throws IOException {
        String expected = """
Words:
BASEBALL
BAT
CAT
PORT
PORTS
SPORT

Links:
        """;
        StringWriter sw = new StringWriter();
        BufferedWriter writer = new BufferedWriter(sw);
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.serialize(writer);
        writer.flush();
        assertEquals(expected, sw.toString());
    }

    @Test
    public void testSerializeDatabase1() throws IOException {
        String expected = """
Words:
NURSED
RED SUN
SIGHT
TOWN
VITAMIN A
WON'T

Links:
SIGHT, VITAMIN A, WordAssociation
        """;

        StringWriter sw = new StringWriter();
        BufferedWriter writer = new BufferedWriter(sw);
        WordDatabase db = new WordDatabase();
        db.addWords(Word.of("NURSED", "RED SUN", "SIGHT", "TOWN", "VITAMIN A", "WON'T"));
        db.addLink(new Link(Word.of("VITAMIN A"), Word.of("SIGHT"), LinkType.WordAssociation));
        db.serialize(writer);
        writer.flush();
        assertEquals(expected, sw.toString());
    }

    @Test
    public void testDeserializeDatabase() throws IOException {
        String expected = """
Words:
BASEBALL
BAT
CAT
PORT
PORTS
SPORT

Links:
BASEBALL, BAT, WordAssociation
BASEBALL, SPORT, WordAssociation
        """;

        StringReader sr = new StringReader(expected);
        BufferedReader reader = new BufferedReader(sr);
        WordDatabase db = WordDatabase.deserialize(reader);
        assertEquals(Set.of(baseball, bat, cat, sport, ports, port), db.getAllWords());
        assertTrue(db.containsLink(baseball, bat));
        assertTrue(db.containsLink(bat, baseball));
        assertTrue(db.containsLink(ports, sport));
    }

    @Test
    public void testDeserializeDatabase1() throws IOException {
        String expected = """
Words:
NURSED
RED SUN
SIGHT
TOWN
VITAMIN A
WON'T

Links:
VITAMIN A, SIGHT, WordAssociation
        """;

        StringReader sr = new StringReader(expected);
        BufferedReader reader = new BufferedReader(sr);
        WordDatabase db = WordDatabase.deserialize(reader);
        Word nursed = Word.of("NURSED");
        Word redSun = Word.of("RED SUN");
        Word vitaminA = Word.of("VITAMIN A");
        Word sight = Word.of("SIGHT");
        Word town = Word.of("TOWN");
        Word wont = Word.of("WON'T");
        assertEquals(Set.of(nursed, redSun, vitaminA, sight, wont, town), db.getAllWords());
        assertTrue(db.containsLink(vitaminA, sight));
        assertTrue(db.containsLink(sight, vitaminA));
        assertTrue(db.containsLink(nursed, redSun));
        assertTrue(db.containsLink(redSun, nursed));
        assertTrue(db.containsLink(wont, town));
        assertTrue(db.containsLink(town, wont));
    }

    @Test
    public void testFindChain() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports));
        db.addLink(batBaseball);
        db.addLink(new Link(sport, baseball, LinkType.WordAssociation));
        Chain chain = db.findChain(cat, ports);
        assertEquals(5, chain.size());
        assertEquals(catBat, chain.links().get(0));
        assertEquals(batBaseball, chain.links().get(1));
        assertEquals(baseballSport, chain.links().get(2));
        assertEquals(sportPorts, chain.links().get(3));
    }

    @Test
    public void testImproperWordAdd() {
        WordDatabase db = new WordDatabase();
        db.addWord(cat);
        assertEquals(Set.of(cat), db.getAllWords());
        db.addWord(cat);
        assertEquals(1, db.getAllWords().size());
        assertEquals(Set.of(cat), db.getAllWords());
    }

    @Test
    public void testContainsWord() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball));
        assertTrue(db.containsWord(cat));
        assertTrue(db.containsWord(bat));
        assertTrue(db.containsWord(baseball));
        assertFalse(db.containsWord(ports));
        assertFalse(db.containsWord(sport));
        assertFalse(db.containsWord(null));
    }

    @Test
    public void testContainsLink() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.addLink(batBaseball);
        assertTrue(db.containsLink(cat, bat));
        assertTrue(db.containsLink(bat, cat));
        assertTrue(db.containsLink(bat, baseball));
        assertTrue(db.containsLink(sport, ports));
        assertFalse(db.containsLink(cat, sport));
        assertFalse(db.containsLink(cat, Word.of("RAT")));
    }

    @Test
    public void testGetLinkBetween() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.addLink(batBaseball);
        db.addLink(baseballSport);
        assertEquals(catBat, db.getLinkBetween(cat, bat));
        assertEquals(baseballSport, db.getLinkBetween(baseball, sport));
        assertEquals(null, db.getLinkBetween(cat, baseball));
        assertEquals(null, db.getLinkBetween(car, cat));
        assertEquals(null, db.getLinkBetween(null, cat));
    }

    @Test
    public void testRemoveLink() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.addLink(batBaseball);
        db.addLink(baseballSport);
        assertTrue(db.containsLink(sport, port));
        db.removeLink(sportPort);
        assertFalse(db.containsLink(sport, port));
        assertThrows(IllegalArgumentException.class, () -> {
            db.removeLink(portsParts);
        });
    }
    
    public void testGetLinkForWithExclusions() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.addLink(batBaseball);
        db.addLink(baseballSport);
        assertEquals(Set.of(catBat.reverse(), batBaseball), db.getLinksFor(bat, Set.of()));
        assertEquals(Set.of(batBaseball), db.getLinksFor(bat, Set.of(cat)));
        assertEquals(Set.of(), db.getLinksFor(bat, Set.of(cat, baseball)));
    }
    
    @Test
    public void testAddFromChain() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball));
        db.addLink(batBaseball);
        db.addLink(baseballSport);

        Chain chain = Chain.of(catBat, batBaseball, baseballSport, sportPorts);
        assertTrue(db.addFromChain(chain));
        assertEquals(Set.of(cat, bat, baseball, sport, ports), db.getAllWords());
        for (Link link : chain.links()) {
            assertTrue(db.containsLink(link));
        }
    }
}
