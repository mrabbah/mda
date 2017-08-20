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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Description ZipTransformer
 * @version 1.0-SNAPSHOT
 * @date 1 avr. 2011
 * @author rabbah
 */
public class ZipTransformer {

    private static final String FS = System.getProperty("file.separator");

    public static void extraireFichierZip(File zipFile, File outputDirectory)
            throws FileNotFoundException, IOException {
        ZipInputStream zipinputstream = null;
        ZipEntry zipentry;
        zipinputstream = new ZipInputStream(
                new FileInputStream(zipFile));
        byte[] buf = new byte[1024];
        zipentry = zipinputstream.getNextEntry();
        while (zipentry != null) {
            int n;
            String entryName = zipentry.getName();
            FileOutputStream fileoutputstream;
            fileoutputstream = new FileOutputStream(
                    outputDirectory.getAbsolutePath() + FS + entryName);

            while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                fileoutputstream.write(buf, 0, n);
            }

            fileoutputstream.close();
            zipinputstream.closeEntry();
            zipentry = zipinputstream.getNextEntry();
        }
        zipinputstream.close();
    }

    public static void extraireFichierZip(InputStream isZipFile, File outputDirectory)
            throws FileNotFoundException, IOException {
        ZipInputStream zipinputstream = null;
        ZipEntry zipentry;
        zipinputstream = new ZipInputStream(isZipFile);
        byte[] buf = new byte[1024];
        zipentry = zipinputstream.getNextEntry();
        while (zipentry != null) {
            int n;
            String entryName = zipentry.getName();
            FileOutputStream fileoutputstream;
            fileoutputstream = new FileOutputStream(
                    outputDirectory.getAbsolutePath() + FS + entryName);

            while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                fileoutputstream.write(buf, 0, n);
            }

            fileoutputstream.close();
            zipinputstream.closeEntry();
            zipentry = zipinputstream.getNextEntry();
        }
        zipinputstream.close();
    }

    public static void extraireFichierZip(Class clazz, String zipFilePath, File outputDirectory)
            throws Exception {
        InputStream is = clazz.getClassLoader().getResourceAsStream(zipFilePath);
        extraireFichierZip(is, outputDirectory);
    }
}
