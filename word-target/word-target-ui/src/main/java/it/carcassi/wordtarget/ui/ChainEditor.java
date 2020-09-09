/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.Chain;
import it.carcassi.wordtarget.core.Link;
import it.carcassi.wordtarget.core.LinkType;
import it.carcassi.wordtarget.core.NewChain;
import it.carcassi.wordtarget.core.Word;
import it.carcassi.wordtarget.core.WordDatabase;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 *
 * @author carcassi
 */
public class ChainEditor extends javax.swing.JFrame {

    /** Creates new form ChainEditor */
    public ChainEditor() {
        initComponents();
        prefs = Preferences.userRoot().node(getClass().getName());
        String filename = prefs.get(LAST_USED_DB, null);
        if (filename != null) {
            currentFile = new File(filename);
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                db = WordDatabase.deserialize(reader);
            } catch (IOException ex) {
                Logger.getLogger(WordDatabaseEditor.class.getName()).log(Level.SEVERE, null, ex);
                db = new WordDatabase();
            }
        } else {
            db = new WordDatabase();
        }
    }
    private DefaultListModel<NewChain> chainsModel = new DefaultListModel<>();
    private DefaultListModel<Word> chainModel = new DefaultListModel<>();
    private DefaultListModel<Link> linksModel = new DefaultListModel<>();
    private ListCellRenderer<Object> chainEndsRenderer = new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Chain) {
                Chain chain = (Chain) value;
                String text = targetWord + " - ";
                if (chain.size() > 0) {
                    text = chain.get(0).getWordA().toString() + " - " + chain.get(chain.size() - 1).getWordB().toString();
                }
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            } else {
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        }
    };

    private Preferences prefs;
    private static String LAST_USED_DB = "LAST_USED_DB";
    private File currentFile;
    private WordDatabase db;
    private Word targetWord;
    private NewChain currentChain;
    private Word currentWord;
    private boolean dbChanged;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editDbButton = new javax.swing.JButton();
        targetWordField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chainsList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedChainList = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nextWordField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        linksList = new javax.swing.JList<>();
        saveDbButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        editDbButton.setText("Edit db...");
        editDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDbButtonActionPerformed(evt);
            }
        });

        targetWordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetWordFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Target word:");

        chainsList.setModel(chainsModel);
        chainsList.setCellRenderer(chainEndsRenderer);
        chainsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                chainsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(chainsList);

        selectedChainList.setModel(chainModel);
        selectedChainList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                selectedChainListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(selectedChainList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jLabel2.setText("Next word:");

        nextWordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextWordFieldActionPerformed(evt);
            }
        });

        linksList.setModel(linksModel);
        linksList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linksListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(linksList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextWordField, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextWordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );

        saveDbButton.setText("Save db");
        saveDbButton.setEnabled(false);
        saveDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDbButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editDbButton)
                        .addGap(18, 18, 18)
                        .addComponent(saveDbButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetWordField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(targetWordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editDbButton)
                            .addComponent(saveDbButton)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDbButtonActionPerformed
        WordDatabaseEditor dbEditor = new WordDatabaseEditor();
        dbEditor.setCurrentFile(currentFile);
        dbEditor.setDb(db);
        dbEditor.setModal(true);
        dbEditor.setVisible(true);
        setCurrentFile(dbEditor.getCurrentFile());
        db = dbEditor.getDb();
    }//GEN-LAST:event_editDbButtonActionPerformed

    private void targetWordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetWordFieldActionPerformed
        setTargetWord(Word.of(targetWordField.getText()));
    }//GEN-LAST:event_targetWordFieldActionPerformed

    private void chainsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_chainsListValueChanged
        setCurrentChain(chainsList.getSelectedValue());
    }//GEN-LAST:event_chainsListValueChanged

    private void selectedChainListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_selectedChainListValueChanged
        setCurrentWord(selectedChainList.getSelectedValue());
    }//GEN-LAST:event_selectedChainListValueChanged

    private void linksListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linksListMouseClicked
        if (evt.getClickCount() > 1 && evt.getButton() == MouseEvent.BUTTON1) {
            addLink(linksList.getSelectedValue());
        }
    }//GEN-LAST:event_linksListMouseClicked

    private void nextWordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextWordFieldActionPerformed
        nextWord(Word.of(nextWordField.getText()));
    }//GEN-LAST:event_nextWordFieldActionPerformed

    private void saveDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDbButtonActionPerformed
        saveDb();
    }//GEN-LAST:event_saveDbButtonActionPerformed

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
        if (currentFile != null) {
            prefs.put(LAST_USED_DB, currentFile.getPath());
        }
    }

    public void setTargetWord(Word targetWord) {
        if (targetWord == null || targetWord.equals(this.targetWord)) {
            return;
        }
        this.targetWord = targetWord;
        if (!db.containsWord(targetWord)) {
            db.addWord(targetWord);
            setDbChanged(true);
        }
        chainsModel.addElement(new NewChain(targetWord));
    }

    public void setCurrentChain(NewChain chain) {
        this.currentChain = chain;
        chainModel.clear();
        if (targetWord != null) {
            if (chain != null) {
                for (Word word : chain.words()) {
                    chainModel.addElement(word);
                }
            }
        }
    }

    public void setCurrentWord(Word currentWord) {
        this.currentWord = currentWord;
        linksModel.clear();
        linksModel.addAll(db.getLinksFor(currentWord));
    }
    
    public void addLink(Link link) {
        currentChain.add(link);
        chainsModel.set(chainsModel.indexOf(currentChain), currentChain);
        setCurrentChain(currentChain);
        selectedChainList.setSelectedIndex(currentChain.words().size() - 1);
    }

    private void nextWord(Word nextWord) {
        if (!db.containsWord(nextWord)) {
            db.addWord(nextWord);
            setDbChanged(true);
        }
        if (!db.containsLink(currentChain.getFinalWord(), nextWord)) {
            Object[] options = { LinkType.WordAssociation, LinkType.Synonym, LinkType.Antonym, "CANCEL" };
            int choice = JOptionPane.showOptionDialog(this, "Select link type", "New Link...",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            if (choice < 0 || choice > 2) {
                return;
            }
            db.addLink(new Link(currentChain.getFinalWord(), nextWord, (LinkType) options[choice]));
            setDbChanged(true);
        }
        addLink(db.getLinkBetween(currentChain.getFinalWord(), nextWord));
    }

    public void setDbChanged(boolean dbChanged) {
        this.dbChanged = dbChanged;
        saveDbButton.setEnabled(dbChanged && currentFile != null);
    }
    
    private void saveDb() {
        if (!dbChanged || currentFile == null) {
            throw new IllegalStateException("Shouldn't be able to save db if not changed or no valid file was chosen");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            db.serialize(writer);
            setDbChanged(false);
        } catch (Exception ex) {
            Logger.getLogger(ChainEditor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordDatabaseEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChainEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<NewChain> chainsList;
    private javax.swing.JButton editDbButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<Link> linksList;
    private javax.swing.JTextField nextWordField;
    private javax.swing.JButton saveDbButton;
    private javax.swing.JList<Word> selectedChainList;
    private javax.swing.JTextField targetWordField;
    // End of variables declaration//GEN-END:variables

}
