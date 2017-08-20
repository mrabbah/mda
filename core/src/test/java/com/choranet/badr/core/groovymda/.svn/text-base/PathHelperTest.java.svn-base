/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
 
package com.choranet.badr.core.groovymda;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

/**
 * Tests for PathHelper
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public class PathHelperTest extends TestCase {

	public void testFilesystemRef() throws Exception {
		String file = "./src/test/resources/addressbook.zargo";
		
		assertNotNull(PathHelper.pathToStream(file));
		assertNotNull(PathHelper.pathToStream(
				"file:" + file
		));		
	}

	public void testGuessedFilesystemRefError() throws Exception {
		String file = "./src/test/resources/nozip.zargo";
		assertNull(PathHelper.pathToStream(file));
	}

	public void testJarRefs() throws Exception {				
		String file = "./src/test/resources/addressbook.zargo!/addressbook.xmi"; 
		assertNotNull(PathHelper.pathToStream(file));

		assertNotNull(PathHelper.pathToStream(
				"jar:file:" + file
		));
	}
	
	public void testGuessedJarRefError() throws Exception {
		String file = "./src/test/resources/nozip.zargo!/nofile.xmi";
		try {
			InputStream in = PathHelper.pathToStream(file);
			throw new Exception("Jar should not have been bound: " + file);
		} catch (IOException ioe) {
			assertTrue(ioe.getMessage().contains("cannot find jar"));
		}
	}
	
	public void testJarRefNotFound() throws Exception {
		String file = "./src/test/resources/notfound.zargo!/notfound.xmi";
		try {
			InputStream in = PathHelper.pathToStream(file);
			throw new Exception("Jar should not have been found: " + file);
		} catch (IOException ioe) {
			assertTrue(ioe.getMessage().contains("cannot find jar"));
		}
	}
	
	public void testJarRefEntryNotFound() throws Exception {
		String file = "./src/test/resources/addressbook.zargo!/nothere.xmi";	
		try {
			InputStream in = PathHelper.pathToStream(file);
			throw new Exception("Jar entry should not have been found: " + file);
		} catch (FileNotFoundException fnfe) {
			// Expected result.
		}
	}
	
	public void testContainsProtocol() throws Exception {
		assertTrue(PathHelper.containsProtocol("jar:file:./nofilerequired.xmi"));
		assertTrue(PathHelper.containsProtocol("file:./nofilerequired.xml"));
		assertFalse(PathHelper.containsProtocol("./nofilerequired.xml"));		
	}
	
	public void testIsJarFile() throws Exception {
		assertTrue(PathHelper.isJarFile("jar:file:./nofilerequired.xmi"));
		assertTrue(PathHelper.isJarFile("./nofilerequired.xmi!/zipentry.file"));
		
		assertFalse(PathHelper.isJarFile("file:./blah.xmi"));
		assertFalse(PathHelper.isJarFile("./blah.xmi"));		
	}
	
	public void testResourceRef() throws Exception {
		assertNotNull(PathHelper.pathToStream("/com/choranet/badr/core/groovymda/processors/GroovyModelProcessor.groovy"));
		
		assertNull(PathHelper.pathToStream("/nofilehere.file"));
	}
}
