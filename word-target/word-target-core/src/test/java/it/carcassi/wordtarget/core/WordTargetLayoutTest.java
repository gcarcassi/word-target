/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Matteo
 */
public class WordTargetLayoutTest {

    @Test
    public void testLinkCreation() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE"));
        assertEquals(1, layout.getWordsInCircle(5).size());
        assertEquals("VARIABLE", layout.getWordsInCircle(5).get(0));
        assertEquals(0, layout.getWordsInCircle(4).size());
        assertEquals(0, layout.getWordsInCircle(3).size());
        assertEquals(0, layout.getWordsInCircle(2).size());
        assertEquals("STATE", layout.getWordInCenter());
    }

    // Assign word to a circle: error if it's not in the list or if it's first/last word;
    @Test
    public void testAssignWord() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("UNCLEAR", 4);
        assertThrows(IllegalArgumentException.class, () -> {
            layout.assignWord("NUCLEAR", 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.assignWord("VARIABLE", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.assignWord("STATE", 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.assignWord("VAGUE", 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.assignWord("VALUE", 4);
        });
        assertEquals("STATE", layout.getWordInCenter());
        assertEquals(1, layout.getWordsInCircle(3).size());
        assertEquals(1, layout.getWordsInCircle(4).size());
        assertEquals("VALUE", layout.getWordsInCircle(3).get(0));
        assertEquals("UNCLEAR", layout.getWordsInCircle(4).get(0));

    }

    // Remove from other circles if there;
    @Test
    public void testRemoveWord() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "UNCLEAR", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("UNCLEAR", 4);
        assertThrows(IllegalArgumentException.class, () -> {
            layout.removeWord("NUCLEAR", 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.removeWord("UNCLEAR", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.removeWord("VARIABLE", 5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.removeWord("STATE", 1);
        });
        layout.removeWord("VALUE", 3);
        assertEquals(0, layout.getWordsInCircle(3).size());
        assertEquals(1, layout.getWordsInCircle(4).size());
        assertEquals("UNCLEAR", layout.getWordsInCircle(4).get(0));
    }

    // add to the target circle at the end of the list; calcualte the fraction and add it to the list of fractions for the circle
    @Test
    public void testFracsWhenAssigningWords() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "UNCLEAR", "STATE"));
        assertEquals(0, layout.getFracsInCircle(3).size());
        layout.assignWord("VALUE", 3);
        assertEquals(1, layout.getFracsInCircle(3).size());
        assertEquals(0.111, layout.getFracsInCircle(3).get(0), 0.001);
        layout.assignWord("UNCLEAR", 3);
        assertEquals(2, layout.getFracsInCircle(3).size());
        assertEquals(0.162, layout.getFracsInCircle(3).get(1), 0.001);
    }

    @Test
    public void testFracsWhenRemovingWords() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("UNCLEAR", 3);
        assertEquals(2, layout.getFracsInCircle(3).size());
        layout.removeWord("UNCLEAR", 3);
        assertEquals(1, layout.getFracsInCircle(3).size());
        layout.removeWord("VALUE", 3);
        assertEquals(0, layout.getFracsInCircle(3).size());
    }

    // Calculate empty space for circle: add up all the fractions, add min space for separator, return what's left
    @Test
    public void testEmptySpace() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("UNCLEAR", 3);
        assertEquals(0.660, layout.getEmptySpace(3), 0.001);
        assertEquals(1.0, layout.getEmptySpace(4));
        assertEquals(0.892, layout.getEmptySpace(5), 0.001);
        assertThrows(IllegalArgumentException.class, () -> {
            layout.getEmptySpace(6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.getEmptySpace(1);
        });
    }

    // Is circle full? Check that there is no more space in the circle
    @Test
    public void testIsCircleFull() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("VAGUE", 3);
        layout.assignWord("UNCLEAR", 3);
        layout.assignWord("NUCLEAR", 3);
        assertEquals(false, layout.isCircleFull(3));
        layout.assignWord("CORE", 3);
        layout.assignWord("CORN", 3);
        layout.assignWord("COIN", 3);
        layout.assignWord("JOIN", 3);
        assertThrows(IllegalArgumentException.class, () -> {
            layout.isCircleFull(1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            layout.isCircleFull(6);
        });
        assertEquals(true, layout.isCircleFull(3));

    }

    // Where is the word? Return the circle or null if not assigned
    @Test
    public void testFindWord() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        layout.assignWord("VALUE", 3);
        layout.assignWord("UNCLEAR", 4);
        layout.assignWord("VAGUE", 2);
        assertEquals(2, layout.findWord("VAGUE"));
        assertEquals(3, layout.findWord("VALUE"));
        assertEquals(4, layout.findWord("UNCLEAR"));
        assertEquals(5, layout.findWord("VARIABLE"));
        assertEquals(1, layout.findWord("STATE"));
        assertEquals(null, layout.findWord("NUCLEAR"));
    }

    // Get previous and next words
    @Test
    public void testGetNextWord() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        assertEquals("VALUE", layout.getNextWord("VARIABLE"));
        assertEquals("VAGUE", layout.getNextWord("VALUE"));
        assertEquals(null, layout.getNextWord("STATE"));
        assertThrows(IllegalArgumentException.class, () -> {
            layout.getNextWord("NOTTHERE");
        });
    }

    @Test
    public void testGetPreviousWord() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE"));
        assertEquals(null, layout.getPreviousWord("VARIABLE"));
        assertEquals("VARIABLE", layout.getPreviousWord("VALUE"));
        assertEquals("UNCLEAR", layout.getPreviousWord("STATE"));
        assertThrows(IllegalArgumentException.class, () -> {
            layout.getNextWord("NOTTHERE");
        });
    }

    // Get words that are not assigned
    @Test
    public void testGetUnassignedWords() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "STATE"));
        layout.assignWord("VALUE", 2);
        layout.assignWord("VAGUE", 2);
        Set<String> unassigned = new HashSet<>(Set.of("NUCLEAR", "UNCLEAR"));
        assertEquals(unassigned, layout.getUnassignedWords());
    }

    @Test
    public void testStartingAngles() {
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "STATE"));
        layout.setStartingAngle(2, 80);
        layout.setStartingAngle(3, 0);
        layout.setStartingAngle(4, 5);
        assertEquals(80, layout.getStartingAngle(2));
        assertEquals(0, layout.getStartingAngle(3));
        assertEquals(5, layout.getStartingAngle(4));
    }

    @Test
    public void testDistributeRandomly() {
        Random rand = new Random(235);
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE"));
        layout.distributeRandomly(rand);
        // TODO complete method
    }

    @Test
    public void testEquilibrateOnce() {
        Random rand = new Random(236);
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE"));
        layout.distributeRandomly(rand);
        layout.equilibrateOnce(rand);
        // TODO complete method
    }

    @Test
    public void testDoLayout() {
        Random rand = new Random(249);
        WordTargetLayout layout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE"));
        layout.doLayout(rand);
        // TODO complete method
        String wordTarget = Renderer.renderWordTarget(layout);
        //System.IO.File.WriteAllText(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\WordTargetOutput.svg", wordTarget);
    }
}
