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

package com.choranet.badr.core.transformer;

import java.io.File;
import junit.framework.TestCase;

/**
 * File Transformer test
 * @author rabbah
 */
public class FileTransformerTest extends TestCase {
    
    public FileTransformerTest(String testName) {
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
     * Test of readFileAsString method, of class FileTransformer.
     */
    public void testReadFileAsString() throws Exception {
        System.out.println("readFileAsString");
        File file = new File("./target/test-classes/User.groovy");
        StringBuilder result = FileTransformer.readFileAsString(file);
        assertEquals(true, result.toString().contains("class User {"));
    }

    /**
     * Test of writeStringToFile method, of class FileTransformer.
     */
    public void testWriteStringToFile() throws Exception {
        System.out.println("writeStringToFile");
        String output = "TEST 1 2 3 4 5";
        File file = new File("./target/test-classes/FicherTest");
        FileTransformer.writeStringToFile(output, file);
        StringBuilder result = FileTransformer.readFileAsString(file);
        assertEquals(true, result.toString().trim().equals(output));
    }

    /**
     * Test of addContraintesToAttribut method, of class FileTransformer.
     */
    public void testAddContraintesToAttribut() throws Exception {
        System.out.println("addContraintesToAttribut");
        File domainClass = new File("./target/test-classes/User.groovy");
        String attributName = "username";
        String contraintes = "contraintes";
        FileTransformer.addContraintesToAttribut(domainClass, attributName,
                contraintes);
        StringBuilder userGroovyFile = FileTransformer.readFileAsString(
                domainClass);
        File domainClassContrainte = new File("./target/test-classes/"
                + "UserContrainte.groovy");
        StringBuilder userGroovyFileContrainte = FileTransformer.
                readFileAsString(domainClassContrainte);
        assertEquals(true, userGroovyFile.toString().
                equals(userGroovyFileContrainte.toString()));
    }

    /**
     * Test of sameFileContents method, of class FileTransformer.
     */
    public void testSameFileContents() throws Exception {
        System.out.println("sameFileContents");
        File file1 = new File("./target/test-classes/"
                + "User_2.groovy");
        File file2 = new File("./target/test-classes/"
                + "User_3.groovy");
        boolean result = FileTransformer.sameFileContents(file1, file2);
        assertEquals(true, result);

    }

    /**
     * Test of getBytesFromFile method, of class FileTransformer.
     */
    public void testGetBytesFromFile() throws Exception {
        System.out.println("getBytesFromFile");
        File file1 = new File("./target/test-classes/"
                + "User_2.groovy");
        File file2 = new File("./target/test-classes/"
                + "User_3.groovy");
        byte[] expResult = FileTransformer.getBytesFromFile(file1);
        byte[] result = FileTransformer.getBytesFromFile(file2);
        assertEquals(expResult.length, result.length);
    }

}
