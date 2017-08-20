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

package com.choranet.badr.core.commandrunner;

import com.choranet.badr.core.Controlleur;
import com.choranet.badr.core.model.commande.CommandesDatabase;
import com.choranet.badr.core.model.commande.GeneralGrailsCommandes;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description ThreadRunGrailsApp
 * @version 1.0-SNAPSHOT
 * @date 25 mars 2011
 * @author rabbah
 */
public class ThreadRunGrailsApp extends Thread {
    
    private Controlleur controlleur;
    private static final String FS = System.getProperty("file.separator");

    public ThreadRunGrailsApp(Controlleur controlleur) {
        this.controlleur = controlleur;
    }

    @Override
    public void run() {
        try {
            File projectDirectory = new File(controlleur.getViewToCoreDTO().getOutputDirectory()
                    + FS
                    + controlleur.getViewToCoreDTO().getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le répertoire du projet n'exite pas");
            }
            RunCommandStatusEnum status = CommandRunner.run(
                    CommandesDatabase.generalGrailsCommandesDb.get(GeneralGrailsCommandes.RUNAPPLICATION), projectDirectory);
            if (status == RunCommandStatusEnum.ECHEC) {
                throw new Exception("Echec de l'installation des briques techniques");
            }
        } catch (Exception exception) {
            Logger.getLogger(ThreadRunGrailsApp.class.toString()).log(Level.SEVERE, exception.getMessage());
        }
    }

    

}
