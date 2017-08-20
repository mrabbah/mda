/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core;

import com.choranet.badr.core.dto.ViewToCoreDTO;
import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.Clazz;
import com.choranet.badr.core.model.commande.BriquesTechniquesCommandes;
import com.choranet.badr.core.model.contrainte.AbstractContrainte;
import com.choranet.badr.core.model.contrainte.InListContrainte;
import com.choranet.badr.core.transformer.FileTransformer;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author rabbah
 */
public class ControlleurTest extends TestCase {

    public ControlleurTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of creerGrailsProjet method, of class Controlleur.
     */
    public void testCreerGrailsProjet() throws Exception {
        System.out.println("creerGrailsProjet");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo1");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        File grailsProjet = new File("./target/test-classes/demo1");
        assertEquals(true, grailsProjet.exists());
        assertEquals(true, grailsProjet.isDirectory());
        assertEquals(10, grailsProjet.list().length);
    }

    /**
     * Test of genererGormDomainClasses method, of class Controlleur.
     */
    public void testGenererGormDomainClasses() throws Exception {
        System.out.println("genererGormDomainClasses");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo2");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        File grailsDomainClassFolder = new File(
                "./target/test-classes/demo2/grails-app/domain/com/acme/domain");
        assertEquals(true, grailsDomainClassFolder.exists());
        assertEquals(true, grailsDomainClassFolder.isDirectory());
        assertEquals(4, grailsDomainClassFolder.listFiles().length);
        File grailsTestDomainClassFolder = new File(
                "./target/test-classes/demo2/test/unit/com/acme/domain");
        assertEquals(true, grailsTestDomainClassFolder.exists());
        assertEquals(true, grailsTestDomainClassFolder.isDirectory());
        assertEquals(4, grailsTestDomainClassFolder.listFiles().length);

    }

    /**
     * Test of lancerScaffoldingGrails method, of class Controlleur.
     */
    public void testLancerScaffoldingGrails() throws Exception {
        System.out.println("lancerScaffoldingGrails");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo3");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        viewToCoreDTO.getClassToScaffold().addAll(
                instance.getCoreToViewDTO().getClazzs());
        instance.lancerScaffoldingGrails();
        File grailsControllersFolder = new File("./target/test-classes/demo3/"
                + "grails-app/controllers/com/acme/domain/");
        assertEquals(4, grailsControllersFolder.listFiles().length);
        File grailsViewsFolder = new File("./target/test-classes/demo3/grails-app/views");
        assertEquals(7, grailsViewsFolder.list().length);
    }

    /**
     * Test of lancerScaffoldingOpenLaszlo method, of class Controlleur.
     */
    public void testLancerScaffoldingOpenLaszlo() throws Exception {
        System.out.println("lancerScaffoldingOpenLaszlo");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo4");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        viewToCoreDTO.getClassToScaffold().addAll(
                instance.getCoreToViewDTO().getClazzs());
        instance.lancerScaffoldingOpenLaszlo();
        File grailsControllersFolder = new File("./target/test-classes/demo4/"
                + "grails-app/controllers/com/acme/domain/");
        assertEquals(4, grailsControllersFolder.listFiles().length);
        //TODO Tester la reussite du scaffolding
    }

    /**
     * Test of lancerScaffoldingZk method, of class Controlleur.
     */
    public void testLancerScaffoldingZk() throws Exception {
        System.out.println("lancerScaffoldingZk");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo5");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        viewToCoreDTO.getClassToScaffold().addAll(
                instance.getCoreToViewDTO().getClazzs());
        instance.lancerScaffoldingZk();
        File grailsControllersFolder = new File("./target/test-classes/demo5/"
                + "grails-app/controllers/com/acme/domain/");
        assertEquals(4, grailsControllersFolder.listFiles().length);
        //TODO Tester la reussite du scaffolding
    }

    /**
     * Test of lancerScaffoldingFlex method, of class Controlleur.
     */
    public void testLancerScaffoldingFlex() throws Exception {
        System.out.println("lancerScaffoldingFlex");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo6");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        viewToCoreDTO.getClassToScaffold().addAll(
                instance.getCoreToViewDTO().getClazzs());
        instance.lancerScaffoldingFlex();
        File grailsControllersFolder = new File("./target/test-classes/demo6/"
                + "grails-app/controllers/com/acme/domain/");
        assertEquals(4, grailsControllersFolder.listFiles().length);
        //TODO Tester la reussite du scaffolding
    }

    /**
     * Test of lancerScaffoldingJSF method, of class Controlleur.
     */
    public void testLancerScaffoldingJSF() throws Exception {
        System.out.println("lancerScaffoldingJSF");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo7");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        viewToCoreDTO.getClassToScaffold().addAll(
                instance.getCoreToViewDTO().getClazzs());
        instance.lancerScaffoldingZk();
        File grailsControllersFolder = new File("./target/test-classes/demo7/"
                + "grails-app/controllers/com/acme/domain/");
        assertEquals(4, grailsControllersFolder.listFiles().length);
        //TODO Tester la reussite du scaffolding
    }

    /**
     * Test of ajouterGroovyContraintesClassDomaine method, of class Controlleur.
     */
    public void testAjouterGroovyContraintesClassDomaine() throws Exception {
        System.out.println("ajouterGroovyContraintesClassDomaine");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo8");
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.genererGormDomainClasses();
        List<Clazz> clazzs = instance.getCoreToViewDTO().getClazzs();
        Clazz clazz = null;
        for (Iterator<Clazz> it = clazzs.iterator(); it.hasNext();) {
            Clazz clazz1 = it.next();
            if (clazz1.getNom().equals("Region")) {
                clazz = clazz1;
                break;
            }
        }
        assertNotNull(clazz);
        Attribut attribut = clazz.getAttributs().get(0);
        String possibleContraintes[] = Attribut.getContraintesPossibleForType(
                attribut.getType());
        assertEquals(true, (possibleContraintes.length == 12));
        Object contrainte = AbstractContrainte.creerContrainte(
                AbstractContrainte.StringContraintesEnum.inList);
        assertEquals(true, (contrainte instanceof InListContrainte));
        InListContrainte inListContrainte = (InListContrainte) contrainte;
        inListContrainte.addChoix("A");
        inListContrainte.addChoix("B");
        attribut.getContraintes().add(inListContrainte);
        instance.ajouterGroovyContraintesClassDomaine();
        File regionDomainClassFile = new File("./target/test-classes/demo8/"
                + "grails-app/domain/com/acme/domain/Region.groovy");
        assertEquals(true, regionDomainClassFile.exists());
        String regionDomainClassAsString = FileTransformer.
                readFileAsString(regionDomainClassFile).toString();
        assertEquals(true, regionDomainClassAsString.contains("inList"));
    }

    /**
     * Test of installerLesBriquesTechniques method, of class Controlleur.
     */
    public void testInstallerLesBriquesTechniques() throws Exception {
        System.out.println("installerLesBriquesTechniques");
        ViewToCoreDTO viewToCoreDTO = new ViewToCoreDTO();
        File outputDirectory = new File("./target/test-classes/");
        viewToCoreDTO.setOutputDirectory(outputDirectory);
        File xmiFile = new File("./target/test-classes/addressbook.zargo!/addressbook.xmi");
        viewToCoreDTO.setXmiFile(xmiFile);
        viewToCoreDTO.setProjectName("demo9");
        viewToCoreDTO.getBriquesTechniques().add(BriquesTechniquesCommandes.ACEGI);
        Controlleur instance = new Controlleur(viewToCoreDTO);
        instance.creerGrailsProjet();
        instance.installerLesBriquesTechniques();
    }
}
