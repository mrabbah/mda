/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.groovymda;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *  Unit test for GroovyMDA class
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public class GroovyMDATest extends TestCase {

    private final GroovyMDA gmda;

    /**
     *  Constructor for the GroovyMDATest object
     *
     * @param  testName  Description of the Parameter
     */
    public GroovyMDATest(String testName) throws Exception {
        super(testName);
        gmda = new GroovyMDA("jar:file:./target/test-classes/addressbook.zargo!/addressbook.xmi");
    }

    /**
     *  A unit test suite for JUnit
     *
     * @return    The test suite
     */
    public static Test suite() {
        return new TestSuite(GroovyMDATest.class);
    }

    public void testLoadingModelClasses() throws Exception {
        assertNotNull(gmda.getClasses());
        assertEquals(4, gmda.getClasses().size());
    }
    /**
     *  A unit test for JUnit
     */
    public void testJPAProcessor() throws Exception {
        gmda.lunchProcessor("./target/test-classes/JPA/", ProcessorEnum.JPA);
        assertNotNull(PathHelper.pathToStream(
                "./target/test-classes/JPA/com/acme/domain/User.java"));
    }

    /**
     *  A unit test for JUnit
     */
    public void testGormProcessor() throws Exception {
        gmda.lunchProcessor("./target/test-classes/GORM/", ProcessorEnum.GORM);
        assertNotNull(PathHelper.pathToStream(
                "./target/test-classes/GORM/grails-app/domain/com/acme/domain/User.groovy"));
    }

    /**
     *  A unit test for JUnit
     */
    public void testZkProcessor() throws Exception {
        gmda.lunchProcessor("./target/test-classes/ZK/", ProcessorEnum.ZK);
        assertNotNull(PathHelper.pathToStream(
                "./target/test-classes/ZK/grails-app/windows/UserWindow.groovy"));
    }

    /**
     *  A unit test for JUnit
     */
    public void testJAVABEANProcessor() throws Exception {
        gmda.lunchProcessor("./target/test-classes/JAVABEAN/", ProcessorEnum.JAVA_BEAN);
        assertNotNull(PathHelper.pathToStream(
                "./target/test-classes/JAVABEAN/com/acme/domain/User.java"));
    }
}
