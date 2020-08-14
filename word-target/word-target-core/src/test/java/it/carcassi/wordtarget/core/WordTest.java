/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author carcassi
 */
public class WordTest {

    @org.junit.Test
    public void testGetText() {
        Word cat = new Word("CAT");
        assertEquals("CAT", cat.getText());
    }
    
}
