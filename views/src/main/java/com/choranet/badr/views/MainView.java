/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainView.java
 *
 * Created on 8 févr. 2011, 19:26:16
 */
package com.choranet.badr.views;

import com.choranet.badr.core.Controlleur;
import com.choranet.badr.core.dto.ViewToCoreDTO;
import com.choranet.badr.views.panel.BriquesTechniquesPanel;
import com.choranet.badr.views.panel.ContraintesPanel;
import com.choranet.badr.views.panel.ScaffoldingClassChooser;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rabbah
 */
public class MainView extends javax.swing.JFrame {

    private Controlleur controlleur;

    /** Creates new form MainView */
    public MainView() {
        initComponents();

        org.jdesktop.application.ResourceMap resourceMap =
                org.jdesktop.application.Application.getInstance(Run.class).getContext().getResourceMap(MainView.class);
        setIconImage(resourceMap.getImageIcon("frame.icon").getImage());
        jMenuItemEnregistrer.setEnabled(false);
        jMenuItemFermer.setEnabled(false);
        jMenuItemGenererr.setEnabled(false);
        jMenuItemNextStep1.setEnabled(false);
        jMenuItemLancerApplication.setEnabled(false);
        jMenuItemStoppApplication.setEnabled(false);
        pack();

    }

    public void creerNouveauProjet(ViewToCoreDTO viewToCoreDTO) {
        try {
            controlleur = new Controlleur(viewToCoreDTO);
            getContentPane().removeAll();
            ;
            BriquesTechniquesPanel btp = new BriquesTechniquesPanel(controlleur, this);
            getContentPane().add(btp);
            jMenuItemFermer.setEnabled(true);
            jMenuItemNextStep1.setEnabled(true);
            jMenuItemNouveauProjet.setEnabled(false);
            jMenuItemOuvrirProjet.setEnabled(false);
            pack();
        } catch (Exception ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupClass = new javax.swing.ButtonGroup();
        buttonGroupAttribut = new javax.swing.ButtonGroup();
        jLabelImage = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenuProjet = new javax.swing.JMenu();
        jMenuItemNouveauProjet = new javax.swing.JMenuItem();
        jMenuItemOuvrirProjet = new javax.swing.JMenuItem();
        jMenuItemEnregistrer = new javax.swing.JMenuItem();
        jMenuItemFermer = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemNextStep1 = new javax.swing.JMenuItem();
        jMenuItemGenererr = new javax.swing.JMenuItem();
        jMenuItemLancerApplication = new javax.swing.JMenuItem();
        jMenuItemStoppApplication = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(MainView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(new java.awt.FlowLayout());

        jLabelImage.setIcon(resourceMap.getIcon("jLabelImage.icon")); // NOI18N
        jLabelImage.setName("jLabelImage"); // NOI18N
        getContentPane().add(jLabelImage);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenuProjet.setText(resourceMap.getString("jMenuProjet.text")); // NOI18N
        jMenuProjet.setName("jMenuProjet"); // NOI18N

        jMenuItemNouveauProjet.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNouveauProjet.setText(resourceMap.getString("jMenuItemNouveauProjet.text")); // NOI18N
        jMenuItemNouveauProjet.setActionCommand(resourceMap.getString("jMenuItemNouveauProjet.actionCommand")); // NOI18N
        jMenuItemNouveauProjet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNouveauProjetActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemNouveauProjet);

        jMenuItemOuvrirProjet.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOuvrirProjet.setText(resourceMap.getString("jMenuItemOuvrirProjet.text")); // NOI18N
        jMenuItemOuvrirProjet.setName("jMenuItemOuvrirProjet"); // NOI18N
        jMenuItemOuvrirProjet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOuvrirProjetActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemOuvrirProjet);

        jMenuItemEnregistrer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemEnregistrer.setText(resourceMap.getString("jMenuItemEnregistrer.text")); // NOI18N
        jMenuItemEnregistrer.setName("jMenuItemEnregistrer"); // NOI18N
        jMenuItemEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnregistrerActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemEnregistrer);

        jMenuItemFermer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemFermer.setLabel(resourceMap.getString("jMenuItemFermer.label")); // NOI18N
        jMenuItemFermer.setName("jMenuItemFermer"); // NOI18N
        jMenuItemFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFermerActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemFermer);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenuProjet.add(jSeparator1);

        jMenuItemNextStep1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNextStep1.setText(resourceMap.getString("jMenuItemNextStep1.text")); // NOI18N
        jMenuItemNextStep1.setName("jMenuItemNextStep1"); // NOI18N
        jMenuItemNextStep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNextStep1ActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemNextStep1);

        jMenuItemGenererr.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemGenererr.setText(resourceMap.getString("jMenuItemGenererr.text")); // NOI18N
        jMenuItemGenererr.setName("jMenuItemGenererr"); // NOI18N
        jMenuItemGenererr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenererrActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemGenererr);

        jMenuItemLancerApplication.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemLancerApplication.setText(resourceMap.getString("jMenuItemLancerApplication.text")); // NOI18N
        jMenuItemLancerApplication.setName("jMenuItemLancerApplication"); // NOI18N
        jMenuItemLancerApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLancerApplicationActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemLancerApplication);

        jMenuItemStoppApplication.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemStoppApplication.setText(resourceMap.getString("jMenuItemStoppApplication.text")); // NOI18N
        jMenuItemStoppApplication.setName("jMenuItemStoppApplication"); // NOI18N
        jMenuItemStoppApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStoppApplicationActionPerformed(evt);
            }
        });
        jMenuProjet.add(jMenuItemStoppApplication);

        menuBar.add(jMenuProjet);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        MainAboutBox mab = new MainAboutBox(this);
        mab.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void jMenuItemGenererrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenererrActionPerformed
        OutputDialog od = new OutputDialog(controlleur, this, false);
        od.setSize(new Dimension(600, 500));
        od.setVisible(true);
        od.lancerGeneration();
        jMenuItemLancerApplication.setEnabled(true);
    }//GEN-LAST:event_jMenuItemGenererrActionPerformed

    private void jMenuItemNouveauProjetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNouveauProjetActionPerformed
        NewProjectDialog npd = new NewProjectDialog(this, true);
        npd.setVisible(true);
    }//GEN-LAST:event_jMenuItemNouveauProjetActionPerformed

    private void jMenuItemNextStep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNextStep1ActionPerformed
        getContentPane().removeAll();
        jTabbedPaneParametrage = new javax.swing.JTabbedPane();
        jTabbedPaneParametrage.setName("jTabbedPaneParametrage");
        getContentPane().add(jTabbedPaneParametrage);
        ContraintesPanel cp = new ContraintesPanel(controlleur, this);
        ScaffoldingClassChooser scc = new ScaffoldingClassChooser(controlleur, this);
        jTabbedPaneParametrage.add("Gestion des contraintes", cp);
        jTabbedPaneParametrage.addTab("Scaffolding", scc);
        jMenuItemEnregistrer.setEnabled(true);
        jMenuItemGenererr.setEnabled(true);
        jMenuItemNextStep1.setEnabled(false);
        pack();
    }//GEN-LAST:event_jMenuItemNextStep1ActionPerformed

    private void jMenuItemFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFermerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemFermerActionPerformed

    private void jMenuItemEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEnregistrerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemEnregistrerActionPerformed

    private void jMenuItemOuvrirProjetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOuvrirProjetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemOuvrirProjetActionPerformed

    private void jMenuItemLancerApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLancerApplicationActionPerformed
        try {
            jMenuItemStoppApplication.setEnabled(true);
            jMenuItemLancerApplication.setEnabled(false);
            controlleur.lancerApplication();
        } catch (Exception ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemLancerApplicationActionPerformed

    private void jMenuItemStoppApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStoppApplicationActionPerformed
        try {
            jMenuItemStoppApplication.setEnabled(false);
            jMenuItemLancerApplication.setEnabled(true);
            controlleur.stoperApplication();
        } catch (Exception ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemStoppApplicationActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MainView mv = new MainView();
                mv.setVisible(true);
                
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                MainView mv = new MainView();
//                mv.setVisible(true);
//                //new MainView().setVisible(true);
//            }
//        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.ButtonGroup buttonGroupAttribut;
    private javax.swing.ButtonGroup buttonGroupClass;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JMenuItem jMenuItemEnregistrer;
    private javax.swing.JMenuItem jMenuItemFermer;
    private javax.swing.JMenuItem jMenuItemGenererr;
    private javax.swing.JMenuItem jMenuItemLancerApplication;
    private javax.swing.JMenuItem jMenuItemNextStep1;
    private javax.swing.JMenuItem jMenuItemNouveauProjet;
    private javax.swing.JMenuItem jMenuItemOuvrirProjet;
    private javax.swing.JMenuItem jMenuItemStoppApplication;
    private javax.swing.JMenu jMenuProjet;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JTabbedPane jTabbedPaneParametrage;
}
