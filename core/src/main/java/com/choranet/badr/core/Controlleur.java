/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core;

import com.choranet.badr.core.commandrunner.CommandRunner;
import com.choranet.badr.core.dto.CoreToViewDTO;
import com.choranet.badr.core.dto.ViewToCoreDTO;
import com.choranet.badr.core.groovymda.GroovyMDA;
import com.choranet.badr.core.groovymda.ProcessorEnum;
import com.choranet.badr.core.commandrunner.RunCommandStatusEnum;
import com.choranet.badr.core.commandrunner.ThreadRunGrailsApp;
import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.Clazz;
import com.choranet.badr.core.model.commande.CommandesDatabase;
import com.choranet.badr.core.model.commande.BriquesTechniquesCommandes;
import com.choranet.badr.core.model.commande.GeneralGrailsCommandes;
import com.choranet.badr.core.model.commande.ScaffoldingCommandes;
import com.choranet.badr.core.transformer.FileTransformer;
import com.choranet.badr.core.transformer.ZipTransformer;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Description Controlleur
 * @version 1.0-SNAPSHOT
 * @date 20 févr. 2011
 * @author rabbah
 */
public class Controlleur {

    private CoreToViewDTO coreToViewDTO;
    private ViewToCoreDTO viewToCoreDTO;
    private GroovyMDA groovyMDA;
    private static final String FS = System.getProperty("file.separator");
    private static final String zk_webxml_path = "com/choranet/badr/core/zk/web.xml";
    private static final String grails_webxml_path = "src/templates/war/web.xml";
    private static final String zk_lib_zipfile_path = "com/choranet/badr/core/zk/zklib.zip";
    private static final String super_service_file_path = "com/choranet/badr/core/grails/SuperService.groovy";
    private static final String images_zipfile_path = "com/choranet/badr/core/images/images.zip";
    private static final String zk_macros_zipfile_path = "com/choranet/badr/core/zk/macros.zip";
    private static final Logger log = Logger.getLogger(Controlleur.class.toString());
    private ThreadRunGrailsApp threadRunGrailsApp = null;

    public Controlleur(ViewToCoreDTO viewToCoreDTO) throws Exception {
        if (viewToCoreDTO == null) {
            throw new Exception("View To Core DTO cannot be null");
        }
        this.viewToCoreDTO = viewToCoreDTO;
        if (viewToCoreDTO.getXmiFile() == null) {
            throw new Exception("Xmi File cannot be null");
        }
        groovyMDA = new GroovyMDA(viewToCoreDTO.getXmiFile().getAbsolutePath());
        List<Clazz> classes = groovyMDA.getClasses();
        if (classes == null || classes.isEmpty()) {
            throw new Exception("Aucune classes trouve dans ton fichier xmi");
        }
        coreToViewDTO = new CoreToViewDTO();
        coreToViewDTO.getClazzs().addAll(classes);
    }

    public void creerGrailsProjet() throws Exception {
        try {
            String commandCreateProjet = CommandesDatabase.generalGrailsCommandesDb.get(GeneralGrailsCommandes.CREATEPROJECT)
                    + viewToCoreDTO.getProjectName();
            RunCommandStatusEnum status = CommandRunner.run(commandCreateProjet, viewToCoreDTO.getOutputDirectory());
            if (status == RunCommandStatusEnum.ECHEC) {
                throw new Exception("Echec de la creation du projet");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void installerLesBriquesTechniques() throws Exception {
        try {
            List<BriquesTechniquesCommandes> bts = viewToCoreDTO.getBriquesTechniques();
            String commandes[] = new String[bts.size()];
            int compteur = 0;
            for (Iterator<BriquesTechniquesCommandes> it = bts.iterator(); it.hasNext();) {
                BriquesTechniquesCommandes bt = it.next();
                commandes[compteur++] = CommandesDatabase.briquesTechniquesCommandesDb.get(bt);
            }
            File projectDirectory = new File(viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le repertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'ecriture sur le repertoire du projet");
            }
            RunCommandStatusEnum status = CommandRunner.run(commandes, projectDirectory);
            if (status == RunCommandStatusEnum.ECHEC) {
                throw new Exception("Echec de l'installation des briques techniques");
            }
        } catch (Exception exception) {
            throw exception;
        }

    }

    public void genererGormDomainClasses() throws Exception {
        try {
            File projectDirectory = new File(viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le repertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'ecriture sur le repertoire du projet");
            }
            groovyMDA.lunchProcessor(projectDirectory, ProcessorEnum.GORM);
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void lancerScaffoldingGrails() throws Exception {
        try {
            File projectDirectory = new File(viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le repertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'ecriture sur le repertoire du projet");
            }
            List<Clazz> classToScaffold = viewToCoreDTO.getClassToScaffold();
            if (classToScaffold.size() > 0) {
                String commandes[] = new String[classToScaffold.size()];
                int compt = 0;
                for (Iterator<Clazz> it = classToScaffold.iterator(); it.hasNext();) {
                    Clazz clazz = it.next();
                    commandes[compt++] = CommandesDatabase.scaffoldingCommandesDb.get(ScaffoldingCommandes.GENERATEALLGRAILS)
                            + clazz.getPackageName()
                            + "." + clazz.getNom();
                }
                RunCommandStatusEnum status = CommandRunner.run(commandes, projectDirectory);
                if (status == RunCommandStatusEnum.ECHEC) {
                    throw new Exception("Echec de l'installation des briques techniques");
                }
            } else {
                log.warning("Aucune class n a ete slectionnee pour le scaffolding");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void lancerScaffoldingOpenLaszlo() throws Exception {
        try {
            File projectDirectory = new File(viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le repertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'ecriture sur le repertoire du projet");
            }
            List<Clazz> classToScaffold = viewToCoreDTO.getClassToScaffold();
            if (classToScaffold.size() > 0) {
                String commandes[] = new String[classToScaffold.size()];
                int compt = 0;
                for (Iterator<Clazz> it = classToScaffold.iterator(); it.hasNext();) {
                    Clazz clazz = it.next();
                    commandes[compt++] = CommandesDatabase.scaffoldingCommandesDb.get(ScaffoldingCommandes.GENERATELASZLO)
                            + clazz.getPackageName()
                            + "." + clazz.getNom();
                }
                RunCommandStatusEnum status = CommandRunner.run(commandes, projectDirectory);
                if (status == RunCommandStatusEnum.ECHEC) {
                    throw new Exception("Echec de l'installation des briques techniques");
                }
            } else {
                log.warning("Aucune class n a ete slectionnee pour le scaffolding");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void lancerScaffoldingZk() throws Exception {
        //TODO controler les classes qu'on veut generer leurs interfaces.
        try {
            String cheminRepertoireProjet = viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName();
            File projectDirectory = new File(cheminRepertoireProjet);
            if (!projectDirectory.exists()) {
                throw new Exception("Le repertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'ecriture sur le repertoire du projet");
            }
            List<Clazz> classToScaffold = viewToCoreDTO.getClassToScaffold();
            if (classToScaffold.size() > 0) {
                //install grails templatee to change web.xml
                String commande =CommandesDatabase.generalGrailsCommandesDb.
                        get(GeneralGrailsCommandes.INSTALLTEMPLATES);
                RunCommandStatusEnum status = CommandRunner.run(commande, projectDirectory);
                if (status == RunCommandStatusEnum.ECHEC) {
                    throw new Exception("Echec de l'execution de la commande :"
                            + commande);
                }
                //copy du fichier web.xml qui correspond a zk
                StringBuilder sb = FileTransformer.readFileAsString(getClass(), zk_webxml_path);
                File grails_webxml = new File(cheminRepertoireProjet + FS + grails_webxml_path);
                FileTransformer.writeStringToFile(sb.toString(), grails_webxml);

                //copy des libraries zk dans le nouveau projet
                File grails_lib_directory = new File(cheminRepertoireProjet + FS + "lib");
                ZipTransformer.extraireFichierZip(getClass(), zk_lib_zipfile_path, grails_lib_directory);

                //Copy du la classe SuperService dans les services grails
                String super_service_string = FileTransformer
                        .readFileAsString(getClass(), super_service_file_path).toString();
                String packageName = viewToCoreDTO.getClassToScaffold().get(0).getPackageName();
                super_service_string =
                        super_service_string.replace("_PACKAGE_", packageName);
                String packagepath = "";
                try {
                    packagepath = packageName.replaceAll("\\.", FS); 
                } catch (Exception e) {
                    //pour windows
                    packagepath = packageName.replaceAll("\\.", "\\" + FS); 
                }
                String super_service_directory_path = cheminRepertoireProjet
                        + FS + "grails-app" + FS + "services" + FS +
                        packagepath;                
                FileTransformer.createFileAndWriteString(super_service_directory_path
                        , "SuperService.groovy", super_service_string);
                
                //generation des class et vu zk
                groovyMDA.lunchProcessor(projectDirectory, ProcessorEnum.ZK);

                //generation des services grails
                groovyMDA.lunchProcessor(projectDirectory, ProcessorEnum.GRAILSSERVICE);

                //copie de quelque images qui peuvent aider
                File images_directory = new File(cheminRepertoireProjet + FS
                        + "web-app"+ FS + "images" + FS + "skin");
                ZipTransformer.extraireFichierZip(getClass(), images_zipfile_path, images_directory);

                //changement de l icone grails par celle de chora
                String outputIconFile = cheminRepertoireProjet + FS
                        + "web-app"+ FS + "images" + FS + "favicon.ico";
                String inputIconFile = cheminRepertoireProjet + FS + "web-app"
                        + FS + "images" + FS + "skin" + FS + "logo_small.png";
                FileTransformer.copyFileToFile(inputIconFile, outputIconFile);

                
            } else {
                log.warning("Aucune class n a ete slectionnee pour le scaffolding");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void lancerScaffoldingFlex() throws Exception {
        try {
            File projectDirectory = new File(viewToCoreDTO.getOutputDirectory()
                    + FS
                    + viewToCoreDTO.getProjectName());
            if (!projectDirectory.exists()) {
                throw new Exception("Le répertoire du projet n'exite pas");
            }
            if (!projectDirectory.canWrite()) {
                throw new Exception("Vous n'avez pas les droits d'écriture sur le répértoire du projet");
            }
            List<Clazz> classToScaffold = viewToCoreDTO.getClassToScaffold();
            String commandes[] = new String[classToScaffold.size() + 1];
            commandes[0] = CommandesDatabase.briquesTechniquesCommandesDb.get(BriquesTechniquesCommandes.FLEX);
            int compt = 1;
            for (Iterator<Clazz> it = classToScaffold.iterator(); it.hasNext();) {
                Clazz clazz = it.next();
                commandes[compt++] = CommandesDatabase.scaffoldingCommandesDb.get(ScaffoldingCommandes.GENERATEALLFLEX)
                        + clazz.getPackageName()
                        + "." + clazz.getNom();
            }
            RunCommandStatusEnum status = CommandRunner.run(commandes, projectDirectory);
            if (status == RunCommandStatusEnum.ECHEC) {
                throw new Exception("Echec de l'installation des briques techniques");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void lancerScaffoldingJSF() throws Exception {
        //TODO soufiane
    }

    public void ajouterGroovyContraintesClassDomaine() throws Exception {
        List<Clazz> classes = coreToViewDTO.getClazzs();
        String cheminRepertoireProjet = viewToCoreDTO.getOutputDirectory()
                + FS + viewToCoreDTO.getProjectName();
        for (Iterator<Clazz> it = classes.iterator(); it.hasNext();) {
            Clazz clazz = it.next();
            String packagepath = "";
            try {
                packagepath = clazz.getPackageName().replaceAll("\\.", FS);
            } catch (Exception e) {
                //pour windows
                packagepath = clazz.getPackageName().replaceAll("\\.", "\\" + FS);
            }
            
            String cheminClass = cheminRepertoireProjet + FS + "grails-app" + FS
                    + "domain" + FS + packagepath
                    + FS + clazz.getNom() + ".groovy";
            File domainClass = new File(cheminClass);

            List<Attribut> attributs = clazz.getAttributs();
            for (Iterator<Attribut> it1 = attributs.iterator(); it1.hasNext();) {
                Attribut attribut = it1.next();
                String attributNom = attribut.getNom();
                String contraintes = attribut.getContraintesAsString();
                if (contraintes.length() > 0) {
                    FileTransformer.addContraintesToAttribut(domainClass, attributNom, contraintes);
                }
            }
        }
    }

    public void lancerApplication() throws Exception {
        try {
            threadRunGrailsApp = new ThreadRunGrailsApp(this);
            threadRunGrailsApp.start();
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void stoperApplication() throws Exception {
        try {
            if(threadRunGrailsApp == null) {
                throw new Exception("L'application n'a pas été lancé au paravant");
            } else {
                threadRunGrailsApp.interrupt();
            }
        } catch(Exception ex) {
            throw ex;
        }
    }

    public CoreToViewDTO getCoreToViewDTO() {
        return coreToViewDTO;
    }

    public ViewToCoreDTO getViewToCoreDTO() {
        return viewToCoreDTO;
    }
}
