/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description FileTransformer
 * @version 1.0-SNAPSHOT
 * @date 19 févr. 2011
 * @author rabbah
 */
public class FileTransformer {

    private static final Logger log = Logger.getLogger(FileTransformer.class.toString());

    public static StringBuilder readFileAsString(File file) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine());
            text.append(NL);
        }

        return text;
    }

    public static StringBuilder readFileAsString(Class clazz, String path) throws Exception {
        InputStream is = clazz.getClassLoader().getResourceAsStream(path);
        return readFileAsString(is);
    }

    public static StringBuilder readFileAsString(InputStream is) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine());
            text.append(NL);
        }

        return text;
    }

    public static void writeStringToFile(String output, File file) throws
            FileNotFoundException, UnsupportedEncodingException, IOException {
        Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        out.write(output);
        out.close();
    }

    public static void copyFileToFile(File fileToCopy, File outputFile) throws IOException {
        int n;
        FileOutputStream fileoutputstream;
        fileoutputstream = new FileOutputStream(outputFile);
        FileInputStream fileinputstream;
        fileinputstream = new FileInputStream(fileToCopy);
        byte[] buf = new byte[1024];
        while ((n = fileinputstream.read(buf, 0, 1024)) > -1) {
            fileoutputstream.write(buf, 0, n);
        }
        fileoutputstream.close();
        fileinputstream.close();
    }

    public static void copyFileToFile(String fileToCopyPath, String outputFilePath) throws IOException {
        File fileToCopy = new File(fileToCopyPath);
        File outputFile = new File(outputFilePath);
        copyFileToFile(fileToCopy, outputFile);
    }

    public static void createFileAndWriteString(String outputDirectoryPath,
            String outputFileName, String output) throws FileNotFoundException,
            UnsupportedEncodingException, IOException {
        File outputDirectory = new File(outputDirectoryPath);
        outputDirectory.mkdirs();
        String FS = System.getProperty("file.separator");
        File outputFile = new File(outputDirectoryPath + FS + outputFileName);
        writeStringToFile(output, outputFile);
    }

    public static void addContraintesToAttribut(File domainClass,
            String attributName, String contraintes) throws Exception {
        StringBuilder sb = readFileAsString(domainClass);
        String string = sb.toString();
        String strings[] = string.split(" " + attributName + "\\(");
        if (strings.length != 2) {
            throw new Exception("Attribut (" + attributName + ") non trouver "
                    + "dans le fichier: " + domainClass.getAbsolutePath());
        }
        String result = strings[0] + " " + attributName + "(" + contraintes + strings[1];
        writeStringToFile(result, domainClass);
    }

    /**
     * Compares the content of two files (as <code>byte</code> arrays.)
     * @param file1 a file for comparison.
     * @param file2 a file for comparison.
     * @return whether the files' contents match.
     * @throws IOException
     */
    public static boolean sameFileContents(File file1, File file2) throws IOException {
        byte[] content1 = getBytesFromFile(file1);
        byte[] content2 = getBytesFromFile(file2);

        if (content1.length != content2.length) {
            return false;
        }

        for (int a = 0; a < content1.length; a++) {
            if (content1[a] != content2[a]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads a file into memory as a <code>byte<code> array.
     * @param file a file for reading.
     * @return the file's content as an array of <code>byte</code>.
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
