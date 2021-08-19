/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.Chain;
import it.carcassi.wordtarget.core.Link;
import it.carcassi.wordtarget.core.Word;
import it.carcassi.wordtarget.core.WordDatabase;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
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
        
        public void addChain(String word) {
            Chain chain = new Chain(Word.of(word));
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
        chainModel.addChain(word);
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
    
}
