/*
================= IMPORTANT - READ CAREFULLY BEFORE USING =====================
THE SOFTWARE PRODUCT. The SOFTWARE PRODUCT is owned by CHORA INFORMATIQUE and
is copyrighted and licensed, not sold. The SOFTWARE PRODUCT is protected by
copyright law and international copyright treaties, as well as other
intellectual property laws and treaties. By installing, copying or otherwise
using this SOFTWARE PRODUCT, you agree to be bound by the terms of CHORA
INFORMATIQUE's General Terms and Conditions in relation to the type of license
you have acquired. (See your Contract Administrator for complete details of
your license agreement with CHORA INFORMATIQUE).
If you do not agree to the terms of CHORA INFORMATIQUE's License Agreement,
do not install or use the SOFTWARE PRODUCT. The term "SOFTWARE PRODUCT" means
the original program and all whole or partial copies of it. A Program consists
of machine-readable instructions, its associated media, printed materials, and
"online" or electronic documentation and related licensed materials
("SOFTWARE PRODUCT").
COPYRIGHT. All title and copyrights in and to this SOFTWARE PRODUCT (including
but not limited to any images, photographs, video, audio, text and "applets"
incorporated into the SOFTWARE PRODUCT), the accompanying printed materials,
and any copies of the SOFTWARE PRODUCT are owned by CHORA INFORMATIQUE or its
suppliers. The SOFTWARE PRODUCT is protected by copyright law and international
treaty provisions. Therefore, you must treat the SOFTWARE PRODUCT like any
other copyrighted material except that you may install the SOFTWARE PRODUCT
on a single computer provided you keep the original solely for backup or
archival purposes. Should you have any questions, please contact your local
CHORA INFORMATIQUE Office or Distributor.
 */

/*
 * ScaffoldingClassChooser.java
 *
 * Created on 23 mars 2011, 14:05:39
 */
package com.choranet.badr.views.panel;

import com.choranet.badr.core.Controlleur;
import com.choranet.badr.core.model.Clazz;
import java.awt.Component;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author rabbah
 */
public class ScaffoldingClassChooser extends javax.swing.JPanel {

    private Controlleur controlleur;
    private DefaultListModel listeClassesToScaffold;
    private Component parent;

    /** Creates new form ScaffoldingClassChooser */
    public ScaffoldingClassChooser(Controlleur controlleur, Component parent) {
        this.controlleur = controlleur;
        this.parent = parent;

        listeClassesToScaffold = new DefaultListModel();
        List<Clazz> classes = this.controlleur.getCoreToViewDTO().getClazzs();
        for (Iterator<Clazz> it = classes.iterator(); it.hasNext();) {
            Clazz clazz = it.next();
            listeClassesToScaffold.addElement(clazz);
        }

        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListClassToScaffold = new javax.swing.JList();
        jButtonValider = new javax.swing.JButton();
        jButtonSelectAll = new javax.swing.JButton();
        jButtonDeslectAll = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(ScaffoldingClassChooser.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jListClassToScaffold.setModel(listeClassesToScaffold);
        jListClassToScaffold.setName("jListClassToScaffold"); // NOI18N
        jScrollPane1.setViewportView(jListClassToScaffold);

        jButtonValider.setText(resourceMap.getString("jButtonValider.text")); // NOI18N
        jButtonValider.setName("jButtonValider"); // NOI18N
        jButtonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderActionPerformed(evt);
            }
        });

        jButtonSelectAll.setText(resourceMap.getString("jButtonSelectAll.text")); // NOI18N
        jButtonSelectAll.setName("jButtonSelectAll"); // NOI18N
        jButtonSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        jButtonDeslectAll.setText(resourceMap.getString("jButtonDeslectAll.text")); // NOI18N
        jButtonDeslectAll.setName("jButtonDeslectAll"); // NOI18N
        jButtonDeslectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeslectAllActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .add(jLabel1)
                    .add(layout.createSequentialGroup()
                        .add(jButtonSelectAll)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButtonDeslectAll))
                    .add(jButtonValider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonSelectAll)
                    .add(jButtonDeslectAll))
                .add(18, 18, 18)
                .add(jButtonValider)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        this.jListClassToScaffold.setSelectionInterval(0, listeClassesToScaffold.size() - 1);
        parent.repaint();
    }//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonDeslectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeslectAllActionPerformed
        this.jListClassToScaffold.clearSelection();
        parent.repaint();

    }//GEN-LAST:event_jButtonDeslectAllActionPerformed

    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        if (jListClassToScaffold.isSelectionEmpty()) {
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Aucune sélection n'a été effecuté êtes vous sûr de vouloir continuer",
                    "Avertissement",
                    JOptionPane.YES_NO_OPTION);
            if (reponse == JOptionPane.YES_OPTION) {
                controlleur.getViewToCoreDTO().getClassToScaffold().clear();
            }

        } else {
            Object[] classesToScaffold = (Object[]) jListClassToScaffold.getSelectedValues();
            controlleur.getViewToCoreDTO().getClassToScaffold().clear();
            for (Object clazz : classesToScaffold) {
                controlleur.getViewToCoreDTO().getClassToScaffold().add((Clazz) clazz);
            }
            //controlleur.getViewToCoreDTO().getClassToScaffold().addAll(Arrays.asList(classesToScaffold));
        }
    }//GEN-LAST:event_jButtonValiderActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDeslectAll;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListClassToScaffold;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
