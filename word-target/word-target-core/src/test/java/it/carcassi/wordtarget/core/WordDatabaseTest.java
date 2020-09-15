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
BASEBALL, BAT, CAT, PORT, PORTS, SPORT

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
    public void testDeserializeDatabase() throws IOException {
        String expected = """
Words:
BASEBALL, BAT, CAT, PORT, PORTS, SPORT

Links:
BASEBALL BAT WordAssociation
        """;

        StringReader sr = new StringReader(expected);
        BufferedReader reader = new BufferedReader(sr);
        WordDatabase db = WordDatabase.deserialize(reader);
        assertEquals(Set.of(baseball, bat, cat, sport, ports, port), db.getAllWords());
        assertTrue(db.containsLink("BASEBALL", "BAT"));
        assertTrue(db.containsLink("BAT", "BASEBALL"));
        assertTrue(db.containsLink("PORTS", "SPORT"));
    }

    @Test
    public void testFindChain() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports));
        db.addLink(batBaseball);
        db.addLink(new Link(sport, baseball, LinkType.WordAssociation));
        Chain chain = db.findChain(cat, ports);
        assertEquals(4, chain.size());
        assertEquals(catBat, chain.get(0));
        assertEquals(batBaseball, chain.get(1));
        assertEquals(baseballSport, chain.get(2));
        assertEquals(sportPorts, chain.get(3));
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
        assertTrue(db.containsWord("BAT"));
        assertTrue(db.containsWord(baseball));
        assertFalse(db.containsWord(ports));
        assertFalse(db.containsWord("SPORT"));
        assertFalse(db.containsWord(""));
    }

    @Test
    public void testContainsLink() {
        WordDatabase db = new WordDatabase();
        db.addWords(Arrays.asList(cat, bat, baseball, sport, ports, port));
        db.addLink(batBaseball);
        assertTrue(db.containsLink(cat, bat));
        assertTrue(db.containsLink(bat, cat));
        assertTrue(db.containsLink(bat, baseball));
        assertTrue(db.containsLink("SPORT", "PORTS"));
        assertFalse(db.containsLink(cat, sport));
        assertFalse(db.containsLink("CAT", "RAT"));
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
        assertEquals(null, db.getLinkBetween("", "CAT"));
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
}
