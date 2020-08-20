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

/**
 *
 * @author carcassi
 */
public class LinkTypeColor {
    
    private static final Map<LinkType, Color> colorMap = Map.ofEntries(
        entry(LinkType.OneLetterChange, new Color(0, 128, 128, 255)),
        entry(LinkType.OneLetterAddOrRemove, new Color(0, 0, 255, 255)),
        entry(LinkType.Anagram, new Color(128, 0, 128, 255)),
        entry(LinkType.Synonym, new Color(0, 128, 0, 255)),
        entry(LinkType.Antonym, new Color(192, 0, 0, 255)),
        entry(LinkType.WordAssociation, new Color(0, 0, 0, 255))
    );

    public static Color toColor(LinkType type) {
        return colorMap.get(type);
    }
}
