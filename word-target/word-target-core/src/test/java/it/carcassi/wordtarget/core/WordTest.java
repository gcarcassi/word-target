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
    
}
