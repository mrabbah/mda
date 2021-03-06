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
 * RealContraintesPanelChooser.java
 *
 * Created on 22 mars 2011, 16:06:30
 */
package com.choranet.badr.views.panel;

import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.contrainte.AbstractContrainte;
import com.choranet.badr.core.model.contrainte.MaxContrainte;
import com.choranet.badr.core.model.contrainte.MinContrainte;
import com.choranet.badr.core.model.contrainte.NullableContrainte;
import com.choranet.badr.core.model.contrainte.RangeContrainte;
import com.choranet.badr.core.model.contrainte.ScaleContrainte;
import com.choranet.badr.core.model.contrainte.UniqueContrainte;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author rabbah
 */
public class RealContraintesPanelChooser extends javax.swing.JPanel {

    Attribut attribut;
    Component parent;

    /** Creates new form RealContraintesPanelChooser */
    public RealContraintesPanelChooser(Attribut attribut, Component parent) {
        this.parent = parent;
        this.attribut = attribut;
        initComponents();

        Binding bindingRange = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, jRadioButtonRange, ELProperty.create("${selected}"), jTextFieldMax, BeanProperty.create("visible"));
        Binding bindingScale = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, jCheckBoxScale, ELProperty.create("${selected}"), jTextFieldScale, BeanProperty.create("visible"));

        bindingGroup.addBinding(bindingRange);
        bindingGroup.addBinding(bindingScale);

        bindingGroup.bind();

        loadAttributContraites();
    }

    private void loadAttributContraites() {
        List<AbstractContrainte> contraintes = attribut.getContraintes();
        for (Iterator<AbstractContrainte> it = contraintes.iterator(); it.hasNext();) {
            AbstractContrainte contrainte = it.next();
            if (contrainte instanceof MinContrainte) {
                MinContrainte mc = (MinContrainte) contrainte;
                jRadioButtonMin.setSelected(true);
                jTextFieldMin.setText(mc.getValeur());
            } else if (contrainte instanceof MaxContrainte) {
                MaxContrainte mc = (MaxContrainte) contrainte;
                jRadioButtonMax.setSelected(true);
                jTextFieldMin.setText(mc.getValeur());
            } else if (contrainte instanceof NullableContrainte) {
                jCheckBoxNullable.setSelected(true);
            } else if (contrainte instanceof RangeContrainte) {
                RangeContrainte rc = (RangeContrainte) contrainte;
                jTextFieldMin.setText(rc.getValeur().split("\\.\\.")[0]);
                jTextFieldMax.setText(rc.getValeur().split("\\.\\.")[1]);
                jRadioButtonRange.setSelected(true);
            } else if (contrainte instanceof UniqueContrainte) {
                jCheckBoxUnique.setSelected(true);
            } else if (contrainte instanceof ScaleContrainte) {
                ScaleContrainte sc = (ScaleContrainte) contrainte;
                jTextFieldScale.setText(sc.getValeur());
                jCheckBoxScale.setSelected(true);
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
        jRadioButtonRange = new javax.swing.JRadioButton();
        jRadioButtonNone = new javax.swing.JRadioButton();
        jCheckBoxNullable = new javax.swing.JCheckBox();
        jCheckBoxUnique = new javax.swing.JCheckBox();
        jCheckBoxScale = new javax.swing.JCheckBox();
        jTextFieldScale = new javax.swing.JTextField();
        jTextFieldMin = new javax.swing.JTextField();
        jTextFieldMax = new javax.swing.JTextField();
        jButtonEnregistrer = new javax.swing.JButton();

        setName("Form"); // NOI18N

        buttonGroup1.add(jRadioButtonMin);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(RealContraintesPanelChooser.class);
        jRadioButtonMin.setText(resourceMap.getString("jRadioButtonMin.text")); // NOI18N
        jRadioButtonMin.setName("jRadioButtonMin"); // NOI18N

        buttonGroup1.add(jRadioButtonMax);
        jRadioButtonMax.setText(resourceMap.getString("jRadioButtonMax.text")); // NOI18N
        jRadioButtonMax.setName("jRadioButtonMax"); // NOI18N

        buttonGroup1.add(jRadioButtonRange);
        jRadioButtonRange.setText(resourceMap.getString("jRadioButtonRange.text")); // NOI18N
        jRadioButtonRange.setName("jRadioButtonRange"); // NOI18N
        jRadioButtonRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonRangeActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonNone);
        jRadioButtonNone.setSelected(true);
        jRadioButtonNone.setText(resourceMap.getString("jRadioButtonNone.text")); // NOI18N
        jRadioButtonNone.setName("jRadioButtonNone"); // NOI18N

        jCheckBoxNullable.setText(resourceMap.getString("jCheckBoxNullable.text")); // NOI18N
        jCheckBoxNullable.setName("jCheckBoxNullable"); // NOI18N

        jCheckBoxUnique.setText(resourceMap.getString("jCheckBoxUnique.text")); // NOI18N
        jCheckBoxUnique.setName("jCheckBoxUnique"); // NOI18N

        jCheckBoxScale.setText(resourceMap.getString("jCheckBoxScale.text")); // NOI18N
        jCheckBoxScale.setName("jCheckBoxScale"); // NOI18N
        jCheckBoxScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxScaleActionPerformed(evt);
            }
        });

        jTextFieldScale.setText(resourceMap.getString("jTextFieldScale.text")); // NOI18N
        jTextFieldScale.setName("jTextFieldScale"); // NOI18N

        jTextFieldMin.setText(resourceMap.getString("jTextFieldMin.text")); // NOI18N
        jTextFieldMin.setName("jTextFieldMin"); // NOI18N

        jTextFieldMax.setText(resourceMap.getString("jTextFieldMax.text")); // NOI18N
        jTextFieldMax.setName("jTextFieldMax"); // NOI18N

        jButtonEnregistrer.setText(resourceMap.getString("jButtonEnregistrer.text")); // NOI18N
        jButtonEnregistrer.setName("jButtonEnregistrer"); // NOI18N
        jButtonEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnregistrerActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonEnregistrer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jCheckBoxScale)
                        .add(18, 18, 18)
                        .add(jTextFieldScale, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jCheckBoxNullable)
                        .add(18, 18, 18)
                        .add(jCheckBoxUnique))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTextFieldMin)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(jRadioButtonMin)
                                .add(18, 18, 18)
                                .add(jRadioButtonMax)))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(jRadioButtonRange)
                                .add(18, 18, 18)
                                .add(jRadioButtonNone))
                            .add(jTextFieldMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioButtonMin)
                    .add(jRadioButtonMax)
                    .add(jRadioButtonRange)
                    .add(jRadioButtonNone))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCheckBoxNullable)
                    .add(jCheckBoxUnique))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCheckBoxScale)
                    .add(jTextFieldScale, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jButtonEnregistrer)
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonRangeActionPerformed
        parent.validate();
    }//GEN-LAST:event_jRadioButtonRangeActionPerformed

    private void jCheckBoxScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxScaleActionPerformed
        parent.validate();
    }//GEN-LAST:event_jCheckBoxScaleActionPerformed

    private void jButtonEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnregistrerActionPerformed
        if (attribut.getContraintes() != null) {
            attribut.getContraintes().clear();
        }
        if (jRadioButtonMin.isSelected()) {
            try {
                Float.valueOf(jTextFieldMin.getText());

                MinContrainte mc = new MinContrainte();
                mc.setValeur(jTextFieldMin.getText());
                attribut.getContraintes().add(mc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur min n'est pas un réel valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (jRadioButtonMax.isSelected()) {
            try {
                Float.valueOf(jTextFieldMin.getText());

                MaxContrainte mc = new MaxContrainte();
                mc.setValeur(jTextFieldMin.getText());
                attribut.getContraintes().add(mc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur max n est pas un réel valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (jRadioButtonRange.isSelected()) {
            try {
                Float i1 = Float.valueOf(jTextFieldMin.getText());

                Float i2 = Float.valueOf(jTextFieldMax.getText());

                RangeContrainte rc = new RangeContrainte();
                if (i1 <= i2) {
                    rc.setMinValue(i1.toString());
                    rc.setMaxValue(i2.toString());
                } else {
                    rc.setMinValue(i2.toString());
                    rc.setMaxValue(i1.toString());
                }
                attribut.getContraintes().add(rc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur min et/ou max n est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (jCheckBoxNullable.isSelected()) {
            NullableContrainte nc = new NullableContrainte();
            nc.isNullable(false);
            attribut.getContraintes().add(nc);
        }
        if (jCheckBoxUnique.isSelected()) {
            UniqueContrainte uc = new UniqueContrainte();
            uc.isUnique(true);
            attribut.getContraintes().add(uc);
        }
        if (jCheckBoxScale.isSelected()) {
            try {
                Integer i = Integer.valueOf(jTextFieldScale.getText());
                if (i < 0) {
                    throw new Exception("Entier non valide");
                }
                ScaleContrainte sc = new ScaleContrainte();
                sc.setValeur(i);
                attribut.getContraintes().add(sc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur scale n'est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonEnregistrerActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonEnregistrer;
    private javax.swing.JCheckBox jCheckBoxNullable;
    private javax.swing.JCheckBox jCheckBoxScale;
    private javax.swing.JCheckBox jCheckBoxUnique;
    private javax.swing.JRadioButton jRadioButtonMax;
    private javax.swing.JRadioButton jRadioButtonMin;
    private javax.swing.JRadioButton jRadioButtonNone;
    private javax.swing.JRadioButton jRadioButtonRange;
    private javax.swing.JTextField jTextFieldMax;
    private javax.swing.JTextField jTextFieldMin;
    private javax.swing.JTextField jTextFieldScale;
    // End of variables declaration//GEN-END:variables
    private BindingGroup bindingGroup = new BindingGroup();

    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setPreferredSize(new Dimension(500, 500));
        Attribut attribut = new Attribut();
        RealContraintesPanelChooser scpc = new RealContraintesPanelChooser(attribut, jframe);
        jframe.setContentPane(scpc);
        jframe.pack();
        jframe.setVisible(true);
    }
}
