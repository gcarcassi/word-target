/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author carcassi
 */
public class WordTest {

    @Test
    public void testGetText() {
        Word cat = new Word("CAT");
        assertEquals("CAT", cat.getText());
    }
    
//    TODO: Implement
//
//    @Test
//    public void testToString() {
//        assertEquals("CAT", Word.of("CAT").toString());
//        assertEquals("'VITAMIN A'", Word.of("vitamin A").toString());
//    }
//
//    @Test
//    public void testWordOf() {
//        assertEquals("CAT", Word.of("CAT").getText());
//        assertEquals(null, Word.of(""));
//        assertEquals(null, Word.of(" "));
//        assertEquals("CAT", Word.of("cat").getText());
//        assertEquals("CAT", Word.of("\"cat\"").getText());
//        assertEquals("VITAMIN A", Word.of("vitamin A").getText());
//        assertEquals("VITAMIN A", Word.of("\"vitamin A\"").getText());
//    }
    
}
