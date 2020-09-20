/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static it.carcassi.wordtarget.core.CommonTestObjects.*;
import static it.carcassi.wordtarget.core.LetterCounter.*;

/**
 *
 * @author Matteo
 */
public class LetterCounterTest {

    @Test
    public void testCountLetters() {
        LetterCounter letterCount = countLetters("system");
        assertEquals(2, letterCount.countFor('S'));
        assertEquals(1, letterCount.countFor('Y'));
        assertEquals(1, letterCount.countFor('T'));
        assertEquals(1, letterCount.countFor('E'));
        assertEquals(1, letterCount.countFor('M'));
        assertEquals(0, letterCount.countFor('A'));
        assertEquals(0, letterCount.countFor('B'));
    }

    @Test
    public void testCompareLetterCount() {
        LetterCounter letterCount1 = countLetters("system");
        LetterCounter letterCount2 = countLetters("system");
        assertEquals(letterCount1, letterCount2);

        letterCount1 = countLetters("system");
        letterCount2 = countLetters("sytem");
        assertNotEquals(letterCount1, letterCount2);

        letterCount1 = countLetters("system");
        letterCount2 = countLetters("yetmss");
        assertEquals(letterCount1, letterCount2);

        letterCount1 = countLetters("abc");
        letterCount2 = countLetters("cde");
        assertNotEquals(letterCount1, letterCount2);

        letterCount1 = countLetters("abcd");
        letterCount2 = countLetters("aaaa");
        assertNotEquals(letterCount1, letterCount2);
    }

}
