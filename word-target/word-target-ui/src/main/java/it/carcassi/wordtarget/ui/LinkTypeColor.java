/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.LinkType;
import java.awt.Color;
import java.util.Map;
import static java.util.Map.entry;    
import static it.carcassi.wordtarget.core.LinkType.*;    

/**
 *
 * @author carcassi
 */
public class LinkTypeColor {
    
    private static final Map<LinkType, Color> colorMap = Map.ofEntries(
        entry(OneLetterChange, new Color(0, 128, 128, 255)),
        entry(OneLetterAddOrRemove, new Color(0, 0, 255, 255)),
        entry(Anagram, new Color(128, 0, 128, 255)),
        entry(Synonym, new Color(0, 128, 0, 255)),
        entry(Antonym, new Color(192, 0, 0, 255)),
        entry(WordAssociation, new Color(0, 0, 0, 255))
    );

    public static Color toColor(LinkType type) {
        return colorMap.get(type);
    }
}
