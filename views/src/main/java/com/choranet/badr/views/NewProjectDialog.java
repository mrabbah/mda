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
 * NewProjectDialog.java
 *
 * Created on 25 mars 2011, 08:57:47
 */

package com.choranet.badr.views;

import com.choranet.badr.core.dto.ViewToCoreDTO;
import com.choranet.badr.views.filter.XmiFilter;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author rabbah
 */
public class NewProjectDialog extends javax.swing.JDialog {

    final JFileChooser xmiFileChooser = new JFileChooser();
    final JFileChooser outputDirectoryChooser = new JFileChooser();
    private File xmiFile;
    private File outputDirectory;
    private String projectName = "";

    /** Creates new form NewProjectDialog */
    public NewProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Random rand = new Random();
        this.jTextFieldProjectName.setText("Project" + rand.nextInt());

        initXmiFileChooser();
        initOutputDirectoryChooser();
    }

    /**
     * init xmi file chooser
     */
    private void initXmiFileChooser() {
        xmiFileChooser.setDialogTitle("Choix du fichier XMI");
        xmiFileChooser.setFileFilter(new XmiFilter());
        xmiFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        xmiFileChooser.setMultiSelectionEnabled(false);
    }

    /**
     * init output directory chooser
     */
    private void initOutputDirectoryChooser() {
        outputDirectoryChooser.setDialogTitle("Choix du répertoire de sortie");
        outputDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        outputDirectoryChooser.setMultiSelectionEnabled(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        jTextFieldProjectName = new javax.swing.JTextField();
        jButtonValider = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonXmiFile = new javax.swing.JButton();
        jButtonOutputFolder = new javax.swing.JButton();
        jButtonAnnuler = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.choranet.badr.views.Run.class).getContext().getResourceMap(NewProjectDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldProjectName.setName("jTextFieldProjectName"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${projectName}"), jTextFieldProjectName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jButtonValider.setText(resourceMap.getString("jButtonValider.text")); // NOI18N
        jButtonValider.setName("jButtonValider"); // NOI18N
        jButtonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jButtonXmiFile.setText(resourceMap.getString("jButtonXmiFile.text")); // NOI18N
        jButtonXmiFile.setName("jButtonXmiFile"); // NOI18N
        jButtonXmiFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXmiFileActionPerformed(evt);
            }
        });

        jButtonOutputFolder.setText(resourceMap.getString("jButtonOutputFolder.text")); // NOI18N
        jButtonOutputFolder.setName("jButtonOutputFolder"); // NOI18N
        jButtonOutputFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOutputFolderActionPerformed(evt);
            }
        });

        jButtonAnnuler.setText(resourceMap.getString("jButtonAnnuler.text")); // NOI18N
        jButtonAnnuler.setName("jButtonAnnuler"); // NOI18N
        jButtonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .add(jButtonValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 233, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jButtonAnnuler, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel2)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFieldProjectName)
                            .add(jButtonOutputFolder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .add(jButtonXmiFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .add(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jButtonXmiFile))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jButtonOutputFolder))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldProjectName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonValider)
                    .add(jButtonAnnuler))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        boolean existError = false;
        String errorMessage = "";
        if (xmiFile == null) {
            existError = true;
            errorMessage = "Vous deveriez choisir un fichier xmi\n";
        }
        if (outputDirectory == null) {
            existError = true;
            errorMessage += "Vous deveriez choisir un répertoire de sortie\n";
        }
        if (!projectName.matches("[a-zA-Z][a-zA-Z0-9-_]*")) {
            existError = true;
            errorMessage += "Veuillez indiquer un nom de projet valide\n";
        }
        if (existError) {
            JOptionPane.showMessageDialog(this,
                    errorMessage,
                    "Message d'erreurs",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
                viewToCoreDTO.setOutputDirectory(outputDirectory);
                viewToCoreDTO.setProjectName(projectName);
                viewToCoreDTO.setXmiFile(xmiFile);
                ((MainView) getParent()).creerNouveauProjet(viewToCoreDTO);
                this.dispose();
            } catch (Exception ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Message d'erreurs",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_jButtonValiderActionPerformed

    private void jButtonXmiFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXmiFileActionPerformed
        int returnedValue = xmiFileChooser.showOpenDialog(this);
        if (returnedValue == JFileChooser.APPROVE_OPTION) {
            xmiFile = xmiFileChooser.getSelectedFile();
            jButtonXmiFile.setText(xmiFile.getName());
        }
}//GEN-LAST:event_jButtonXmiFileActionPerformed

    private void jButtonOutputFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOutputFolderActionPerformed
        int returnedValue = outputDirectoryChooser.showOpenDialog(this);
        if (returnedValue == JFileChooser.APPROVE_OPTION) {
            outputDirectory = outputDirectoryChooser.getSelectedFile();
            jButtonOutputFolder.setText(outputDirectory.getAbsolutePath());
        }
}//GEN-LAST:event_jButtonOutputFolderActionPerformed

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

    
    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                NewProjectDialog dialog = new NewProjectDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonOutputFolder;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JButton jButtonXmiFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextFieldProjectName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
