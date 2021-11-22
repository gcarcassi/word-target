/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.Chain;
import it.carcassi.wordtarget.core.Link;
import it.carcassi.wordtarget.core.LinkType;
import it.carcassi.wordtarget.core.Renderer;
import it.carcassi.wordtarget.core.Word;
import it.carcassi.wordtarget.core.WordDatabase;
import it.carcassi.wordtarget.core.WordTargetLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author carcassi
 */
public class ChainEditorModel {
    private WordDatabase db;
    private final List<Chain> chains = new ArrayList<>();
    private final ChainEditorFiles files = new ChainEditorFiles();

    private final ChainModel chainModel = new ChainModel();
    
    class ChainModel extends AbstractListModel<Chain> {
        @Override
        public int getSize() {
            return chains.size();
        }

        @Override
        public Chain getElementAt(int index) {
            return chains.get(index);
        }
        
        public void addChain(Word word) {
            Chain chain = new Chain(word);
            chains.add(chain);
            fireIntervalAdded(this, chains.size() - 1, chains.size() - 1);
        }
        
        public void addChain(Chain chain) {
            chains.add(chain);
            fireIntervalAdded(this, chains.size() - 1, chains.size() - 1);
        }
        
        public void reverseChain(int index) {
            chains.get(index).reverse();
            fireContentsChanged(this, index, index);
        }
        
        public void removeWords(int index, Word word) {
            chains.get(index).removeFrom(word);
            fireContentsChanged(this, index, index);
        }
        
        public void refreshCurrent() {
            int index = getCurrentChainIndex();
            fireContentsChanged(this, index, index);
        }
    }
    
    private final ListSelectionModel chainSelectionModel = new DefaultListSelectionModel();
    private final ListSelectionModel wordSelectionModel = new DefaultListSelectionModel();

    public ListModel<Chain> getChainModel() {
        return chainModel;
    }

    public ListSelectionModel getChainSelectionModel() {
        return chainSelectionModel;
    }

    public ListModel<Word> getWordModel() {
        return wordModel;
    }

    public ListSelectionModel getWordSelectionModel() {
        return wordSelectionModel;
    }

    public DefaultListModel<Link> getSuggestionsModel() {
        return suggestionsModel;
    }
    
    private final WordModel wordModel = new WordModel();
    
    class WordModel extends AbstractListModel<Word> {
        @Override
        public int getSize() {
            if (chainSelectionModel.isSelectionEmpty()) {
                return 0;
            }
            return chains.get(chainSelectionModel.getLeadSelectionIndex()).size();
        }

        @Override
        public Word getElementAt(int index) {
            return chains.get(chainSelectionModel.getLeadSelectionIndex()).words().get(index);
        }
        
        public void reload() {
            fireContentsChanged(this, 0, 100);
            wordSelectionModel.setSelectionInterval(getSize() - 1, getSize() - 1);
        }
        
        public void recalculateSuggestions() {
            suggestionsModel.clear();
            if (getCurrentWord() != null) {
                List<Link> suggestions = db.getLinksFor(getCurrentWord(), getCurrentChain().words()).stream()
                        .sorted(Comparator.comparing(x -> x.getWordB().getText()))
                        .collect(Collectors.toList());
                suggestionsModel.addAll(suggestions);
            }
        }
    };
    
    private final DefaultListModel<Link> suggestionsModel = new DefaultListModel<Link>();
    
    public int getCurrentChainIndex() {
        return chainSelectionModel.getLeadSelectionIndex();
    }
    
    public Chain getCurrentChain() {
        if (chainSelectionModel.isSelectionEmpty()) {
            return null;
        }
        return chains.get(chainSelectionModel.getLeadSelectionIndex());
    }
    
    public int getCurrentWordIndex() {
        return wordSelectionModel.getLeadSelectionIndex();
    }
    
    public Word getCurrentWord() {
        Chain chain = getCurrentChain();
        if (chain == null || wordSelectionModel.isSelectionEmpty()) {
            return null;
        }
        return chain.words().get(wordSelectionModel.getLeadSelectionIndex());
    }

    public WordDatabase getDb() {
        return db;
    }
    
    public ChainEditorModel() {
        // TODO initialize the database
        db = files.loadDatabase();
        
        wordSelectionModel.addListSelectionListener((e) -> {
            wordModel.recalculateSuggestions();
        });
        
        chainSelectionModel.addListSelectionListener((e) -> {
            wordModel.reload();
            wordModel.recalculateSuggestions();
        });
        
        chainModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                wordModel.reload();
                wordModel.recalculateSuggestions();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                wordModel.reload();
                wordModel.recalculateSuggestions();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                wordModel.reload();
                wordModel.recalculateSuggestions();
            }
        });
    }
    
    public void addNewChain(String word) {
        Word newWord = Word.of(word);
        if (newWord != null) {
            addWordToDatabaseIfMissing(newWord);
            chainModel.addChain(newWord);
        }
        int last = chains.size() - 1;
        chainSelectionModel.setSelectionInterval(last, last);
    }
    
    public void addLinkToSelectedChain(Link link) {
        int insertionPosition = wordSelectionModel.getLeadSelectionIndex();
        int lastPosition = getCurrentChain().size() - 1;
        if (insertionPosition == lastPosition) {
            // Insert at the end of the current chain
            getCurrentChain().add(link);
            chainModel.refreshCurrent();
            wordModel.reload();
            int last = getCurrentChain().size() - 1;
            wordSelectionModel.setSelectionInterval(last, last);
        } else {
            // Create a new chain and insert at the desired position
            Chain newChain = new Chain(getCurrentChain().getInitialWord());
            for (int i = 0; i < insertionPosition; i++) {
                newChain.add(getCurrentChain().links().get(i));
            }
            newChain.add(link);
            chainModel.addChain(newChain);
            int lastChain = chains.size() - 1;
            chainSelectionModel.setSelectionInterval(lastChain, lastChain);
        }
    }
    
    private Action reverseCurrentChainAction;
    
    private Action createReverseCurrentChainAction() {
        
        return new AbstractAction("Reverse chain") {
        
            {
                updateEnabled();
                chainSelectionModel.addListSelectionListener((e) -> {
                    updateEnabled();
                });
            }
        
            private void updateEnabled() {
                setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                chainModel.reverseChain(chainSelectionModel.getLeadSelectionIndex());
            }
        };
    }

    public Action getReverseCurrentChainAction() {
        if (reverseCurrentChainAction == null) {
            reverseCurrentChainAction = createReverseCurrentChainAction();
        }
        return reverseCurrentChainAction;
    }
    
    private Action removeWordFromCurrentListAction;
    
    private Action createRemoveWordFromCurrentListAction() {
        
        return new AbstractAction("Remove words") {
        
            {
                updateEnabled();
                wordSelectionModel.addListSelectionListener((e) -> {
                    updateEnabled();
                });
            }
        
            private void updateEnabled() {
                setEnabled(wordSelectionModel.getLeadSelectionIndex() > 0);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                chainModel.removeWords(chainSelectionModel.getLeadSelectionIndex(), getCurrentWord());
            }
        };
    }

    public Action removeWordFromCurrentListAction() {
        if (removeWordFromCurrentListAction == null) {
            removeWordFromCurrentListAction = createRemoveWordFromCurrentListAction();
        }
        return removeWordFromCurrentListAction;
    }
    
    private boolean dbChanged = false;
    public static final String PROP_DBCHANGED = "dbChanged";
    private transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    public boolean isDbChanged() {
        return dbChanged;
    }

    public void setDbChanged(boolean dbChanged) {
        boolean oldDbChanged = this.dbChanged;
        this.dbChanged = dbChanged;
        propertyChangeSupport.firePropertyChange(PROP_DBCHANGED, oldDbChanged, dbChanged);
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener )
    {
        propertyChangeSupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener )
    {
        propertyChangeSupport.removePropertyChangeListener( listener );
    }
    
    private void addWordToDatabaseIfMissing(Word word) {
        if (!db.containsWord(word)) {
            db.addWord(word);
            setDbChanged(true);
        }
    }
    
    public void addWordToSelectedChain(Word nextWord) {
        addWordToDatabaseIfMissing(nextWord);
        if (!db.containsLink(getCurrentWord(), nextWord)) {
            Object[] options = {LinkType.WordAssociation, LinkType.Synonym, LinkType.Antonym, "CANCEL"};
            int choice = JOptionPane.showOptionDialog(null, "Select link type", "New Link...",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            if (choice < 0 || choice > 2) {
                return;
            }
            db.addLink(new Link(getCurrentWord(), nextWord, (LinkType) options[choice]));
            setDbChanged(true);
        }
        addLinkToSelectedChain(db.getLinkBetween(getCurrentWord(), nextWord));
    }
    
    private Action saveNewLinksAction = new AbstractAction("Save new links") {

        {
            updateEnabled();
            chainSelectionModel.addListSelectionListener((e) -> {
                updateEnabled();
            });
        }

        private void updateEnabled() {
            setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (files.getDatabaseFilename() == null) {
                throw new IllegalStateException("No valid database file selected");
            }

            try ( BufferedReader reader = new BufferedReader(new FileReader(files.getDatabaseFilename()))) {
                db = WordDatabase.deserialize(reader);
            } catch (IOException ex) {
                Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                db = new WordDatabase();
            }

            db.addFromChain(getCurrentChain());

            try ( BufferedWriter writer = new BufferedWriter(new FileWriter(files.getDatabaseFilename()))) {
                db.serialize(writer);
                setDbChanged(false);
            } catch (Exception ex) {
                Logger.getLogger(ChainEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public Action saveNewLinksAction() {
        return saveNewLinksAction;
    }
    
    private Action saveDbAction = new AbstractAction("Save db") {

        {
            updateEnabled();
            ChainEditorModel.this.addPropertyChangeListener((evt) -> {
                if (evt.getPropertyName() == PROP_DBCHANGED) {
                    updateEnabled();
                }
            });
        }

        private void updateEnabled() {
            setEnabled(isDbChanged());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            files.saveDatabase(db);
            setDbChanged(false);
        }
    };

    public Action getSaveDbAction() {
        return saveDbAction;
    }

    private JFileChooser chainFileChooser = new JFileChooser(files.getChainFolder());
    private JFileChooser exportFileChooser = new JFileChooser(files.getExportFolder());
    
    private Action saveChainAsAction = new AbstractAction("Save chain as...") {

        {
            updateEnabled();
            chainSelectionModel.addListSelectionListener((e) -> {
                updateEnabled();
            });
        }

        private void updateEnabled() {
            setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = chainFileChooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chainFileChooser.getSelectedFile();
                if (file != null) {
                    try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        getCurrentChain().serialize(writer);
                    } catch (IOException ex) {
                        Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    };

    public Action getSaveChainAsAction() {
        return saveChainAsAction;
    }
    
    private Action loadChainAction = new AbstractAction("Load chain...") {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = chainFileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chainFileChooser.getSelectedFile();
                if (file != null) {
                    try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        Chain newChain = Chain.deserialize(reader);
                        chainModel.addChain(newChain);
                        setDbChanged(db.addFromChain(newChain));
                    } catch (IOException ex) {
                        Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    };

    public Action getLoadChainAction() {
        return loadChainAction;
    }
    
    private Action exportChainAction = new AbstractAction("Export chain...") {

        {
            updateEnabled();
            chainSelectionModel.addListSelectionListener((e) -> {
                updateEnabled();
            });
        }

        private void updateEnabled() {
            setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = exportFileChooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = exportFileChooser.getSelectedFile();
                if (file != null) {
                    files.setExportFolder(file.getPath());

                    File solutionFile = new File(file.getParentFile(), file.getName().substring(0, file.getName().length() - 4) + ".txt");
                    try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        List<String> words = new ArrayList<>(getCurrentChain().words().stream().map(x -> x.getText()).collect(Collectors.toList()));
                        Collections.reverse(words);
                        WordTargetLayout layout = new WordTargetLayout(words);
                        layout.doLayout(new Random());
                        writer.write(Renderer.renderWordTarget(layout));
                        writer.flush();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex, "Can't export chain...", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try ( BufferedWriter writer = new BufferedWriter(new FileWriter(solutionFile))) {
                        List<String> words = new ArrayList<>(getCurrentChain().words().stream().map(x -> x.getText()).collect(Collectors.toList()));
                        Collections.reverse(words);
                        writer.write(words.get(0));
                        for (int i = 1; i < words.size(); i++) {
                            writer.write("\n");
                            writer.write(words.get(i));
                        }
                        writer.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Process proc = Runtime.getRuntime().exec("cmd /c start " + exportFileChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    Logger.getLogger(ChainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public Action getExportChainAction() {
        return exportChainAction;
    }
    
    public void exportChainAs(File file) {
    }
    
    private Action exportAndSaveChainAction = new AbstractAction("Export") {

        {
            updateEnabled();
            chainSelectionModel.addListSelectionListener((e) -> {
                updateEnabled();
            });
        }

        private void updateEnabled() {
            setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (files.getChainName() != null && !files.getChainName().isBlank()) {
                File chainFolder = new File(files.getChainFolder());
                File exportFolder = new File(files.getExportFolder());
                
                File chainFile = new File(chainFolder, files.getChainName() + ".wtc");
                File imageFile = new File(exportFolder, files.getChainName() + ".svg");
                File solutionFile = new File(exportFolder, files.getChainName() + ".txt");
                
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter(chainFile))) {
                    getCurrentChain().serialize(writer);
                } catch (IOException ex) {
                    Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter(imageFile))) {
                    List<String> words = new ArrayList<>(getCurrentChain().words().stream().map(x -> x.getText()).collect(Collectors.toList()));
                    Collections.reverse(words);
                    WordTargetLayout layout = new WordTargetLayout(words);
                    layout.doLayout(new Random());
                    writer.write(Renderer.renderWordTarget(layout));
                    writer.flush();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex, "Can't export chain...", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                }

                try ( BufferedWriter writer = new BufferedWriter(new FileWriter(solutionFile))) {
                    List<String> words = new ArrayList<>(getCurrentChain().words().stream().map(x -> x.getText()).collect(Collectors.toList()));
                    Collections.reverse(words);
                    writer.write(words.get(0));
                    for (int i = 1; i < words.size(); i++) {
                        writer.write("\n");
                        writer.write(words.get(i));
                    }
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Process proc = Runtime.getRuntime().exec("cmd /c start " + imageFile.getPath());
                } catch (IOException ex) {
                    Logger.getLogger(ChainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public Action getExportAndSaveChainAction() {
        return exportAndSaveChainAction;
    }
    
    private Action exportDialogAction = new AbstractAction("Export...") {

        {
            updateEnabled();
            chainSelectionModel.addListSelectionListener((e) -> {
                updateEnabled();
            });
        }

        private void updateEnabled() {
            setEnabled(chainSelectionModel.getLeadSelectionIndex() >= 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ChainExportDialog dialog = new ChainExportDialog(null, true, ChainEditorModel.this);
            dialog.setVisible(true);                    
        }
    };

    public Action getExportDialogAction() {
        return exportDialogAction;
    }

    public ChainEditorFiles getFiles() {
        return files;
    }

}
