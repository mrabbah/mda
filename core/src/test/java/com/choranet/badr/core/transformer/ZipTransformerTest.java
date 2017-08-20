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
import java.net.URL;
import junit.framework.TestCase;

/**
 *
 * @author rabbah
 */
public class ZipTransformerTest extends TestCase {
    
    public ZipTransformerTest(String testName) {
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
     * Test of extraireFichierZip method, of class ZipTransformer.
     */
    public void testExtraireFichierZip() throws Exception {
        System.out.println("extraireFichierZip");
        URL url = getClass().getClassLoader().getResource("com/choranet/badr/core/zk/zklib.zip");
        
        File zipFile = new File(url.getPath());
        File outputDirectory = new File("./target/test-classes/zipextract/");
        outputDirectory.mkdirs();
        ZipTransformer.extraireFichierZip(zipFile, outputDirectory);
        int nbZipFile = outputDirectory.listFiles().length;
        assertEquals((nbZipFile == 16), true);
     }

}
