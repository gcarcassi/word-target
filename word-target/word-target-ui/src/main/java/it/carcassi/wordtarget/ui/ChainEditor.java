/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.ui;

import it.carcassi.wordtarget.core.Chain;
import it.carcassi.wordtarget.core.Link;
import it.carcassi.wordtarget.core.LinkType;
import it.carcassi.wordtarget.core.Chain;
import it.carcassi.wordtarget.core.Renderer;
import it.carcassi.wordtarget.core.Word;
import it.carcassi.wordtarget.core.WordDatabase;
import it.carcassi.wordtarget.core.WordTargetLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author carcassi
 */
public class ChainEditor extends javax.swing.JFrame {

    private ChainEditorModel model = new ChainEditorModel();

    /**
     * Creates new form ChainEditor
     */
    public ChainEditor() {
        initComponents();
        model.getWordModel().addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                refresh();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                refresh();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                refresh();
            }
            
            private void refresh() {
                if (model.getCurrentChain() != null) {
                    boolean targetFits = Renderer.wordFitsInCenter(model.getCurrentChain().getInitialWord().toString());
                    largeTargetWordCheck.setSelected(!targetFits);
                } else {
                    largeTargetWordCheck.setSelected(false);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
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
        jPanel3 = new javax.swing.JPanel();
        editDbButton = new javax.swing.JButton();
        saveDbButton = new javax.swing.JButton();
        loadChainButton = new javax.swing.JButton();
        saveChainButton = new javax.swing.JButton();
        saveChainAsButton = new javax.swing.JButton();
        exportChainButton = new javax.swing.JButton();
        reverseChainButton = new javax.swing.JButton();
        largeTargetWordCheck = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveLinksItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        removeWordItem = new javax.swing.JMenuItem();
        reverseChainItem = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        targetWordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetWordFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Target word:");

        chainsList.setModel(model.getChainModel());
        chainsList.setSelectionModel(model.getChainSelectionModel());
        jScrollPane1.setViewportView(chainsList);

        selectedChainList.setModel(model.getWordModel());
        selectedChainList.setSelectionModel(model.getWordSelectionModel());
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

        linksList.setModel(model.getSuggestionsModel());
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

        editDbButton.setText("Edit db...");
        editDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDbButtonActionPerformed(evt);
            }
        });

        saveDbButton.setAction(model.getSaveDbAction());
        saveDbButton.setText("Save db");
        saveDbButton.setEnabled(false);

        loadChainButton.setAction(model.getLoadChainAction());
        loadChainButton.setText("Load chain...");

        saveChainButton.setText("Save chain");
        saveChainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChainButtonActionPerformed(evt);
            }
        });

        saveChainAsButton.setAction(model.getSaveChainAsAction());
        saveChainAsButton.setText("Save chain as...");

        exportChainButton.setAction(model.getExportDialogAction());
        exportChainButton.setText("Export...");

        reverseChainButton.setAction(model.getReverseCurrentChainAction());
        reverseChainButton.setText("Reverse chain");
        reverseChainButton.setEnabled(false);

        largeTargetWordCheck.setText("Target word too big");
        largeTargetWordCheck.setEnabled(false);
        largeTargetWordCheck.setFocusable(false);

        jButton1.setAction(model.removeWordFromCurrentListAction());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(largeTargetWordCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editDbButton)
                    .addComponent(saveDbButton)
                    .addComponent(loadChainButton)
                    .addComponent(saveChainButton)
                    .addComponent(saveChainAsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportChainButton)
                    .addComponent(reverseChainButton)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(largeTargetWordCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editDbButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveDbButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reverseChainButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadChainButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveChainButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveChainAsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportChainButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        saveLinksItem.setAction(model.saveNewLinksAction());
        jMenu1.add(saveLinksItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        removeWordItem.setAction(model.removeWordFromCurrentListAction());
        jMenu2.add(removeWordItem);

        reverseChainItem.setAction(model.getReverseCurrentChainAction());
        jMenu2.add(reverseChainItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetWordField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(targetWordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addGap(32, 32, 32))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDbButtonActionPerformed
//        WordDatabaseEditor dbEditor = new WordDatabaseEditor();
//        dbEditor.setCurrentFile(currentDbFile);
//        dbEditor.setDb(db);
//        dbEditor.setModal(true);
//        dbEditor.setVisible(true);
//        setCurrentDbFile(dbEditor.getCurrentFile());
//        db = dbEditor.getDb();
    }//GEN-LAST:event_editDbButtonActionPerformed

    private void targetWordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetWordFieldActionPerformed
        model.addNewChain(targetWordField.getText());
    }//GEN-LAST:event_targetWordFieldActionPerformed

    private void linksListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linksListMouseClicked
        if (evt.getClickCount() > 1 && evt.getButton() == MouseEvent.BUTTON1) {
            model.addLinkToSelectedChain(linksList.getSelectedValue());
        }
    }//GEN-LAST:event_linksListMouseClicked

    private void nextWordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextWordFieldActionPerformed
        model.addWordToSelectedChain(Word.of(nextWordField.getText()));
    }//GEN-LAST:event_nextWordFieldActionPerformed

    private void saveChainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChainButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveChainButtonActionPerformed

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
    private javax.swing.JList<it.carcassi.wordtarget.core.Chain> chainsList;
    private javax.swing.JButton editDbButton;
    private javax.swing.JButton exportChainButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox largeTargetWordCheck;
    private javax.swing.JList<Link> linksList;
    private javax.swing.JButton loadChainButton;
    private javax.swing.JTextField nextWordField;
    private javax.swing.JMenuItem removeWordItem;
    private javax.swing.JButton reverseChainButton;
    private javax.swing.JMenuItem reverseChainItem;
    private javax.swing.JButton saveChainAsButton;
    private javax.swing.JButton saveChainButton;
    private javax.swing.JButton saveDbButton;
    private javax.swing.JMenuItem saveLinksItem;
    private javax.swing.JList<Word> selectedChainList;
    private javax.swing.JTextField targetWordField;
    // End of variables declaration//GEN-END:variables

}
