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
 * StringContraintesPanelChooser.java
 *
 * Created on 21 mars 2011, 17:54:55
 */
package com.choranet.badr.views.panel;

import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.contrainte.*;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
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
public class StringContraintesPanelChooser extends javax.swing.JPanel {

    private Component parent;
    private Attribut attribut;

    /** Creates new form StringContraintesPanelChooser */
    public StringContraintesPanelChooser(Attribut attribut, Component parent) {
        this.parent = parent;
        this.attribut = attribut;
        listeContrainte = new DefaultListModel();
        initComponents();
        Binding bindingMatch = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, matches, ELProperty.create("${selected}"), jTextFieldMatches, BeanProperty.create("visible"));
        Binding bindingAjouter = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, inList, ELProperty.create("${selected}"), jButtonAjouter, BeanProperty.create("visible"));
        Binding bindingSupprimer = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, inList, ELProperty.create("${selected}"), jButtonSupprimer, BeanProperty.create("visible"));
        Binding bindingList = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, inList, ELProperty.create("${selected}"), jScrollPane1, BeanProperty.create("visible"));
        Binding bindingMax = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, size, ELProperty.create("${selected}"), jTextFieldMax, BeanProperty.create("visible"));

        bindingGroup.addBinding(bindingMatch);
        bindingGroup.addBinding(bindingAjouter);
        bindingGroup.addBinding(bindingSupprimer);
        bindingGroup.addBinding(bindingList);
        bindingGroup.addBinding(bindingMax);
        bindingGroup.bind();

        loadAttributContraites();
    }

    private void loadAttributContraites() {
        List<AbstractContrainte> contraintes = attribut.getContraintes();
        for (Iterator<AbstractContrainte> it = contraintes.iterator(); it.hasNext();) {
            AbstractContrainte contrainte = it.next();
            if (contrainte instanceof BlankContrainte) {
                blank.setSelected(true);
            } else if (contrainte instanceof CreditCardContrainte) {
                creditCard.setSelected(true);
            } else if (contrainte instanceof EmailContrainte) {
                email.setSelected(true);
            } else if (contrainte instanceof InListContrainte) {
                InListContrainte ilc = (InListContrainte) contrainte;
                List<String> listeChoisie = ilc.getListeChoix();
                for (Iterator<String> it1 = listeChoisie.iterator(); it1.hasNext();) {
                    String string = it1.next();
                    listeContrainte.addElement(string);
                }
                inList.setSelected(true);
            } else if (contrainte instanceof MatchesContrainte) {
                MatchesContrainte mc = (MatchesContrainte) contrainte;
                matches.setSelected(true);
                jTextFieldMatches.setText(mc.getValeur().substring(1, mc.getValeur().length()));
            } else if (contrainte instanceof MaxSizeContrainte) {
                MaxSizeContrainte ms = (MaxSizeContrainte) contrainte;
                maxSize.setSelected(true);
                jTextFieldValue.setText(ms.getValeur());
            } else if (contrainte instanceof MinSizeContrainte) {
                MinSizeContrainte ms = (MinSizeContrainte) contrainte;
                minSize.setSelected(true);
                jTextFieldValue.setText(ms.getValeur());
            } else if (contrainte instanceof NotEqualContrainte) {
                NotEqualContrainte ne = (NotEqualContrainte) contrainte;
                notEqual.setSelected(true);
                jTextFieldValue.setText(ne.getValeur());
            } else if (contrainte instanceof NullableContrainte) {
                nullable.setSelected(true);
            } else if (contrainte instanceof SizeContrainte) {
                SizeContrainte sc = (SizeContrainte) contrainte;
                jTextFieldMax.setText(sc.getValeur().split("\\.\\.")[0]);
                jTextFieldValue.setText(sc.getValeur().split("\\.\\.")[1]);
                size.setSelected(true);
            } else if (contrainte instanceof UniqueContrainte) {
                unique.setSelected(true);
            } else if (contrainte instanceof UrlContrainte) {
                url.setSelected(true);
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

        buttonGroupCaracteristique = new javax.swing.ButtonGroup();
        buttonGroupTaille = new javax.swing.ButtonGroup();
        email = new javax.swing.JRadioButton();
        creditCard = new javax.swing.JRadioButton();
        url = new javax.swing.JRadioButton();
        blank = new javax.swing.JCheckBox();
        none = new javax.swing.JRadioButton();
        nullable = new javax.swing.JCheckBox();
        matches = new javax.swing.JRadioButton();
        jTextFieldMatches = new javax.swing.JTextField();
        inList = new javax.swing.JRadioButton();
        maxSize = new javax.swing.JRadioButton();
        notEqual = new javax.swing.JRadioButton();
        minSize = new javax.swing.JRadioButton();
        size = new javax.swing.JRadioButton();
        unique = new javax.swing.JCheckBox();
        jTextFieldValue = new javax.swing.JTextField();
        jButtonAjouter = new javax.swing.JButton();
        jButtonSupprimer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListContrainte = new javax.swing.JList();
        jTextFieldMax = new javax.swing.JTextField();
        jButtonEnregistrer = new javax.swing.JButton();

        setName("Form"); // NOI18N

        buttonGroupCaracteristique.add(email);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(StringContraintesPanelChooser.class);
        email.setText(resourceMap.getString("email.text")); // NOI18N
        email.setName("email"); // NOI18N

        buttonGroupCaracteristique.add(creditCard);
        creditCard.setText(resourceMap.getString("creditCard.text")); // NOI18N
        creditCard.setName("creditCard"); // NOI18N

        buttonGroupCaracteristique.add(url);
        url.setText(resourceMap.getString("url.text")); // NOI18N
        url.setName("url"); // NOI18N

        blank.setText(resourceMap.getString("blank.text")); // NOI18N
        blank.setName("blank"); // NOI18N

        buttonGroupCaracteristique.add(none);
        none.setSelected(true);
        none.setText(resourceMap.getString("none.text")); // NOI18N
        none.setName("none"); // NOI18N

        nullable.setText(resourceMap.getString("nullable.text")); // NOI18N
        nullable.setName("nullable"); // NOI18N

        buttonGroupCaracteristique.add(matches);
        matches.setText(resourceMap.getString("matches.text")); // NOI18N
        matches.setName("matches"); // NOI18N
        matches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchesActionPerformed(evt);
            }
        });

        jTextFieldMatches.setText(resourceMap.getString("jTextFieldMatches.text")); // NOI18N
        jTextFieldMatches.setName("jTextFieldMatches"); // NOI18N

        buttonGroupTaille.add(inList);
        inList.setText(resourceMap.getString("inList.text")); // NOI18N
        inList.setName("inList"); // NOI18N

        buttonGroupTaille.add(maxSize);
        maxSize.setText(resourceMap.getString("maxSize.text")); // NOI18N
        maxSize.setName("maxSize"); // NOI18N

        buttonGroupTaille.add(notEqual);
        notEqual.setText(resourceMap.getString("notEqual.text")); // NOI18N
        notEqual.setName("notEqual"); // NOI18N

        buttonGroupTaille.add(minSize);
        minSize.setText(resourceMap.getString("minSize.text")); // NOI18N
        minSize.setName("minSize"); // NOI18N

        buttonGroupTaille.add(size);
        size.setText(resourceMap.getString("size.text")); // NOI18N
        size.setName("size"); // NOI18N
        size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeActionPerformed(evt);
            }
        });

        unique.setText(resourceMap.getString("unique.text")); // NOI18N
        unique.setName("unique"); // NOI18N

        jTextFieldValue.setText(resourceMap.getString("jTextFieldValue.text")); // NOI18N
        jTextFieldValue.setName("jTextFieldValue"); // NOI18N
        jTextFieldValue.setPreferredSize(new java.awt.Dimension(40, 19));

        jButtonAjouter.setText(resourceMap.getString("jButtonAjouter.text")); // NOI18N
        jButtonAjouter.setName("jButtonAjouter"); // NOI18N
        jButtonAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterActionPerformed(evt);
            }
        });

        jButtonSupprimer.setText(resourceMap.getString("jButtonSupprimer.text")); // NOI18N
        jButtonSupprimer.setEnabled(false);
        jButtonSupprimer.setName("jButtonSupprimer"); // NOI18N
        jButtonSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jListContrainte.setModel(listeContrainte);
        jListContrainte.setName("jListContrainte"); // NOI18N
        jListContrainte.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListContrainteValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListContrainte);

        jTextFieldMax.setText(resourceMap.getString("jTextFieldMax.text")); // NOI18N
        jTextFieldMax.setName("jTextFieldMax"); // NOI18N
        jTextFieldMax.setPreferredSize(new java.awt.Dimension(40, 19));

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
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(email)
                        .add(18, 18, 18)
                        .add(creditCard)
                        .add(18, 18, 18)
                        .add(url)
                        .add(18, 18, 18)
                        .add(matches)
                        .add(18, 18, 18)
                        .add(none))
                    .add(layout.createSequentialGroup()
                        .add(blank)
                        .add(18, 18, 18)
                        .add(nullable)
                        .add(18, 18, 18)
                        .add(unique))
                    .add(jTextFieldMatches, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(inList)
                        .add(18, 18, 18)
                        .add(maxSize)
                        .add(18, 18, 18)
                        .add(notEqual)
                        .add(18, 18, 18)
                        .add(minSize)
                        .add(18, 18, 18)
                        .add(size))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jTextFieldValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jTextFieldMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButtonAjouter)
                        .add(18, 18, 18)
                        .add(jButtonSupprimer))
                    .add(jButtonEnregistrer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(email)
                    .add(creditCard)
                    .add(url)
                    .add(matches)
                    .add(none))
                .add(18, 18, 18)
                .add(jTextFieldMatches, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(24, 24, 24)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(blank)
                    .add(nullable)
                    .add(unique))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(inList)
                    .add(maxSize)
                    .add(notEqual)
                    .add(minSize)
                    .add(size))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonSupprimer)
                    .add(jButtonAjouter)
                    .add(jTextFieldMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 151, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jButtonEnregistrer)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void matchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchesActionPerformed
        parent.validate();
    }//GEN-LAST:event_matchesActionPerformed

    private void sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeActionPerformed
        parent.validate();
    }//GEN-LAST:event_sizeActionPerformed

    private void jButtonEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnregistrerActionPerformed
        if (attribut.getContraintes() != null) {
            attribut.getContraintes().clear();
        }
        if (email.isSelected()) {
            EmailContrainte ec = new EmailContrainte();
            ec.isEmailAdress(true);
            attribut.getContraintes().add(ec);
        } else if (creditCard.isSelected()) {
            CreditCardContrainte ccc = new CreditCardContrainte();
            ccc.isCreditCard(true);
            attribut.getContraintes().add(ccc);
        } else if (url.isSelected()) {
            UrlContrainte uc = new UrlContrainte();
            uc.isUrl(true);
            attribut.getContraintes().add(uc);
        } else if (matches.isSelected()) {
            MatchesContrainte mc = new MatchesContrainte();
            mc.setValeur(jTextFieldMatches.getText());
            attribut.getContraintes().add(mc);
        }

        if (blank.isSelected()) {
            BlankContrainte bc = new BlankContrainte();
            bc.isBlank(false);
            attribut.getContraintes().add(bc);
        }
        if (nullable.isSelected()) {
            NullableContrainte nc = new NullableContrainte();
            nc.isNullable(false);
            attribut.getContraintes().add(nc);
        }
        if (unique.isSelected()) {
            UniqueContrainte uc = new UniqueContrainte();
            uc.isUnique(true);
            attribut.getContraintes().add(uc);
        }

        if (inList.isSelected()) {
            InListContrainte ilc = new InListContrainte();
            int taille = listeContrainte.size();
            for (int compt = 0; compt < taille; compt++) {
                ilc.addChoix((String) listeContrainte.get(compt));
            }
            attribut.getContraintes().add(ilc);
        } else if (maxSize.isSelected()) {
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
                        "La valeur maxsize n est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (notEqual.isSelected()) {
            NotEqualContrainte nec = new NotEqualContrainte();
            nec.setValeur(jTextFieldValue.getText());
            attribut.getContraintes().add(nec);
        } else if (minSize.isSelected()) {
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
                        "La valeur minsize n'est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (size.isSelected()) {
            try {
                Integer i1 = Integer.valueOf(jTextFieldValue.getText());
                if (i1 < 0) {
                    throw new Exception("Entier non valide");
                }
                Integer i2 = Integer.valueOf(jTextFieldMax.getText());
                if(i2 < 0) {
                    throw new Exception("Entier non valide");
                }
                SizeContrainte sc = new SizeContrainte();
                sc.setMaxValue(i2);
                sc.setMinValue(i1);
                attribut.getContraintes().add(sc);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "La valeur min et/ou max n est pas un entier valide",
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonEnregistrerActionPerformed

    private void jButtonAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterActionPerformed
        String val = jTextFieldValue.getText();
        if (val.length() > 0) {
            listeContrainte.addElement(val);
        }
    }//GEN-LAST:event_jButtonAjouterActionPerformed

    private void jButtonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerActionPerformed
        Object[] os = jListContrainte.getSelectedValues();
        for (Object object : os) {
            listeContrainte.removeElement(object);
        }
    }//GEN-LAST:event_jButtonSupprimerActionPerformed

    private void jListContrainteValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListContrainteValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (jListContrainte.getSelectedValues().length > 0) {
                jButtonSupprimer.setEnabled(true);
            } else {
                jButtonSupprimer.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jListContrainteValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox blank;
    private javax.swing.ButtonGroup buttonGroupCaracteristique;
    private javax.swing.ButtonGroup buttonGroupTaille;
    private javax.swing.JRadioButton creditCard;
    private javax.swing.JRadioButton email;
    private javax.swing.JRadioButton inList;
    private javax.swing.JButton jButtonAjouter;
    private javax.swing.JButton jButtonEnregistrer;
    private javax.swing.JButton jButtonSupprimer;
    private javax.swing.JList jListContrainte;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldMatches;
    private javax.swing.JTextField jTextFieldMax;
    private javax.swing.JTextField jTextFieldValue;
    private javax.swing.JRadioButton matches;
    private javax.swing.JRadioButton maxSize;
    private javax.swing.JRadioButton minSize;
    private javax.swing.JRadioButton none;
    private javax.swing.JRadioButton notEqual;
    private javax.swing.JCheckBox nullable;
    private javax.swing.JRadioButton size;
    private javax.swing.JCheckBox unique;
    private javax.swing.JRadioButton url;
    // End of variables declaration//GEN-END:variables
    private BindingGroup bindingGroup = new BindingGroup();
    private DefaultListModel listeContrainte;

    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setPreferredSize(new Dimension(500, 500));
        Attribut attribut = new Attribut();
        StringContraintesPanelChooser scpc = new StringContraintesPanelChooser(attribut, jframe);
        jframe.setContentPane(scpc);
        jframe.pack();
        jframe.setVisible(true);
    }
}
