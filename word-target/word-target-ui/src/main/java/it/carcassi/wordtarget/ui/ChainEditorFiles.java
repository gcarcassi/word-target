/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.WordDatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author carcassi
 */
public class ChainEditorFiles {
    private final Preferences prefs = Preferences.userRoot().node(ChainEditor.class.getName());
    private static String LAST_USED_DB = "LAST_USED_DB";
    private static String CHAIN_FOLDER = "CHAIN_FOLDER";
    private static String EXPORT_FOLDER = "EXPORT_FOLDER";
    
    private String databaseFilename;
    
    public ChainEditorFiles() {
        databaseFilename = prefs.get(LAST_USED_DB, null);
    }
    
    public WordDatabase loadDatabase() {
        if (databaseFilename != null) {
            File databaseFile = new File(databaseFilename);
            try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
                return WordDatabase.deserialize(reader);
            } catch (IOException ex) {
                Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                return new WordDatabase();
            }
        } else {
            return new WordDatabase();
        }
    }
}
