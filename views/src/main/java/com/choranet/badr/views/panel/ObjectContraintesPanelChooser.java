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
 * ObjectContraintesPanelChooser.java
 *
 * Created on 22 mars 2011, 16:06:56
 */

package com.choranet.badr.views.panel;

import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.contrainte.AbstractContrainte;
import com.choranet.badr.core.model.contrainte.MaxContrainte;
import com.choranet.badr.core.model.contrainte.MinContrainte;
import com.choranet.badr.core.model.contrainte.NullableContrainte;
import com.choranet.badr.core.model.contrainte.UniqueContrainte;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author rabbah
 */
public class ObjectContraintesPanelChooser extends javax.swing.JPanel {

    Attribut attribut;
    Component parent;

    /** Creates new form ObjectContraintesPanelChooser */
    public ObjectContraintesPanelChooser(Attribut attribut, Component parent) {
        this.parent = parent;
        this.attribut = attribut;
        initComponents();
        loadAttributContraites();
    }

    private void loadAttributContraites() {
        List<AbstractContrainte> contraintes = attribut.getContraintes();
        for (Iterator<AbstractContrainte> it = contraintes.iterator(); it.hasNext();) {
            AbstractContrainte contrainte = it.next();
            if(contrainte instanceof MinContrainte) {
                MinContrainte mc = (MinContrainte) contrainte;
                jRadioButtonMin.setSelected(true);
                jTextFieldValue.setText(mc.getValeur());
            } else if(contrainte instanceof MaxContrainte) {
                MaxContrainte mc = (MaxContrainte) contrainte;
                jRadioButtonMax.setSelected(true);
                jTextFieldValue.setText(mc.getValeur());
            } else if(contrainte instanceof NullableContrainte) {
                jCheckBoxNullable.setSelected(true);
            } else if(contrainte instanceof UniqueContrainte) {
                jCheckBoxUnique.setSelected(true);
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButtonMin = new javax.swing.JRadioButton();
        jRadioButtonMax = new javax.swing.JRadioButton();
        jTextFieldValue = new javax.swing.JTextField();
        jCheckBoxNullable = new javax.swing.JCheckBox();
        jCheckBoxUnique = new javax.swing.JCheckBox();
        jButtonEnregistrer = new javax.swing.JButton();
        jRadioButtonNone = new javax.swing.JRadioButton();

        setName("Form"); // NOI18N

        buttonGroup1.add(jRadioButtonMin);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(ObjectContraintesPanelChooser.class);
        jRadioButtonMin.setText(resourceMap.getString("jRadioButtonMin.text")); // NOI18N
        jRadioButtonMin.setName("jRadioButtonMin"); // NOI18N

        buttonGroup1.add(jRadioButtonMax);
        jRadioButtonMax.setText(resourceMap.getString("jRadioButtonMax.text")); // NOI18N
        jRadioButtonMax.setName("jRadioButtonMax"); // NOI18N

        jTextFieldValue.setText(resourceMap.getString("jTextFieldValue.text")); // NOI18N
        jTextFieldValue.setName("jTextFieldValue"); // NOI18N

        jCheckBoxNullable.setText(resourceMap.getString("jCheckBoxNullable.text")); // NOI18N
        jCheckBoxNullable.setName("jCheckBoxNullable"); // NOI18N

        jCheckBoxUnique.setText(resourceMap.getString("jCheckBoxUnique.text")); // NOI18N
        jCheckBoxUnique.setName("jCheckBoxUnique"); // NOI18N

        jButtonEnregistrer.setText(resourceMap.getString("jButtonEnregistrer.text")); // NOI18N
        jButtonEnregistrer.setName("jButtonEnregistrer"); // NOI18N
        jButtonEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnregistrerActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonNone);
        jRadioButtonNone.setSelected(true);
        jRadioButtonNone.setText(resourceMap.getString("jRadioButtonNone.text")); // NOI18N
        jRadioButtonNone.setName("jRadioButtonNone"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonEnregistrer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jCheckBoxNullable)
                        .add(18, 18, 18)
                        .add(jCheckBoxUnique))
                    .add(layout.createSequentialGroup()
                        .add(jRadioButtonMin)
                        .add(18, 18, 18)
                        .add(jRadioButtonMax)
                        .add(18, 18, 18)
                        .add(jRadioButtonNone))
                    .add(jTextFieldValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioButtonMin)
                    .add(jRadioButtonMax)
                    .add(jRadioButtonNone))
                .add(18, 18, 18)
                .add(jTextFieldValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCheckBoxNullable)
                    .add(jCheckBoxUnique))
                .add(18, 18, 18)
                .add(jButtonEnregistrer)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnregistrerActionPerformed
        if (attribut.getContraintes() != null) {
            attribut.getContraintes().clear();
        }
        if(jRadioButtonMin.isSelected()) {
            try {
                Integer i = Integer.valueOf(jTextFieldValue.getText());
                if (i < 0) {
                    throw new Exception("Entier non valide");
                }
                MinContrainte mc = new MinContrainte();
                mc.setValeur(jTextFieldValue.getText());
                attribut.getContraintes().add(mc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur min n'est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (jRadioButtonMax.isSelected()) {
            try {
                Integer i = Integer.valueOf(jTextFieldValue.getText());
                if (i < 0) {
                    throw new Exception("Entier non valide");
                }
                MaxContrainte mc = new MaxContrainte();
                mc.setValeur(jTextFieldValue.getText());
                attribut.getContraintes().add(mc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur max n est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if(jCheckBoxNullable.isSelected()) {
            NullableContrainte nc = new NullableContrainte();
            nc.isNullable(false);
            attribut.getContraintes().add(nc);
        }
        if(jCheckBoxUnique.isSelected()) {
            UniqueContrainte uc = new UniqueContrainte();
            uc.isUnique(true);
            attribut.getContraintes().add(uc);
        }
    }//GEN-LAST:event_jButtonEnregistrerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonEnregistrer;
    private javax.swing.JCheckBox jCheckBoxNullable;
    private javax.swing.JCheckBox jCheckBoxUnique;
    private javax.swing.JRadioButton jRadioButtonMax;
    private javax.swing.JRadioButton jRadioButtonMin;
    private javax.swing.JRadioButton jRadioButtonNone;
    private javax.swing.JTextField jTextFieldValue;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setPreferredSize(new Dimension(500, 500));
        Attribut attribut = new Attribut();
        ObjectContraintesPanelChooser scpc = new ObjectContraintesPanelChooser(attribut, jframe);
        jframe.setContentPane(scpc);
        jframe.pack();
        jframe.setVisible(true);
    }
}