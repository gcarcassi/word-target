/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.WordDatabase;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author carcassi
 */
public class ChainEditorFiles {

    private final Preferences prefs = Preferences.userRoot().node(ChainEditor.class.getName());
    private static final String LAST_USED_DB = "LAST_USED_DB";
    private static final String CHAIN_FOLDER = "CHAIN_FOLDER";
    private static final String CHAIN_NAME = "CHAIN_NAME";
    private static final String EXPORT_FOLDER = "EXPORT_FOLDER";

    private String databaseFilename;
    private String exportFolder;
    private String chainFolder;
    private String chainName;

    public ChainEditorFiles() {
        databaseFilename = prefs.get(LAST_USED_DB, null);
        exportFolder = prefs.get(EXPORT_FOLDER, new File(".").getAbsolutePath());
        chainFolder = prefs.get(CHAIN_FOLDER, new File(".").getAbsolutePath());
        chainName = prefs.get(CHAIN_NAME, "");
    }

    public WordDatabase loadDatabase() {
        if (databaseFilename != null) {
            File databaseFile = new File(databaseFilename);
            try ( BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
                return WordDatabase.deserialize(reader);
            } catch (IOException ex) {
                Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                return new WordDatabase();
            }
        } else {
            return new WordDatabase();
        }
    }
    
    public void saveDatabase(WordDatabase db) {
        if (databaseFilename == null) {
            throw new IllegalStateException("Shouldn't be able to save db if no valid file was chosen");
        }

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFilename))) {
            db.serialize(writer);
        } catch (Exception ex) {
            Logger.getLogger(ChainEditor.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Couldn't save db", ex);
        }
    }

    public String getDatabaseFilename() {
        return databaseFilename;
    }

    public String getExportFolder() {
        return exportFolder;
    }

    public void setExportFolder(String exportFolder) {
        this.exportFolder = exportFolder;
        prefs.put(EXPORT_FOLDER, exportFolder);
    }

    public String getChainFolder() {
        return chainFolder;
    }

    public void setChainFolder(String chainFolder) {
        this.chainFolder = chainFolder;
        prefs.put(CHAIN_FOLDER, chainFolder);
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
        prefs.put(CHAIN_NAME, chainName);
    }

}
