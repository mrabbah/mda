/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.groovymda.config;

import java.util.logging.Logger;

import junit.framework.TestCase;

/**
 *  <p>Base class for unit testing configuration file interpretation.</p>
 *  <p>Extending classes provide a {@link #readConfig} method that 
 *  initializes a context that mirrors the information
 *  found in the constants of this class (<code>Model_FILE_VALUE</code>, 
 *  etc.)</p>
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public abstract class AbstractConfigTest extends TestCase {

    /* Basic test values for standard properties. */
    protected static final String MODEL_PATH_VALUE    = "jar:file:./src/test/resources/addressbook.zargo!/addressbook.xmi";
    protected static final String OUTPUT_DIR_VALUE    = "./target/test-classes/config-generation";
    protected static final String TEMPLATE_FILE_VALUE = "./templates/JpaEntity.gtl";
    protected static final String SCRIPT_FILE_VALUE   = "./src/main/resources/processors/GroovyModelProcessor.groovy";
    
    /* Values for custom context data. */
    protected static final String TABLE_PREFIX_VALUE  = "my_";
    protected static final String COLUMN_PREFIX_VALUE = "col_";
    
    private static final Logger log = Logger.getLogger(AbstractConfigTest.class.toString());
    
    /**
     * A read-only context for testing.
     */
    protected MDAContext context;
    
    /**
     * Creates a read-only context for testing.  
     * Methods requiring a modifiable context should create
     * a new one using <code>readConfig</code>.
     * 
     * @throws Exception
     */
    public void setUp() throws Exception {
        context = ReadOnlyMDAContextProxy.newInstance(readConfig());
    }
    
    /**
     * <p>Reads a configuration from an external source.</p>  
     * <p>The content of the source should be based on the constants 
     * found in this class (MODEL_PATH_VALUE, etc.)</p>
     * 
     * @return an MDAContext populated with information from an
     * external source.
     * @throws Exception
     */
    public abstract MDAContext readConfig() throws Exception;

    /**
     * <p>Ensures nested information is correctly interpreted.</p>
     * <p>This task cannot be generalized because alternative 
     * configuration types are represented by implementation-specific 
     * structures.</p> 
     * <p>For example, XML provides <code>Node</code>s (with attributes), while
     * YAML results in simple <code>Map</code>s with <code>String</code> 
     * values.</p> 
     * @throws Exception
     */
    public abstract void testNestedContent() throws Exception;
    
    // TODO: test nested data updates.


    /**
     * <p>Ensures standard context properties match the input file's 
     * information.</p>
     * 
     * <p>Addition/removal of properties in MDAContext should result 
     * in this method being updated.</p>
     * 
     */
    public void testStandardConfigValues() throws Exception {
        //MDAContext context = readConfig();
        
        assertTrue(compareValues(context.getModelPath   (), MODEL_PATH_VALUE   ));
        assertTrue(compareValues(context.getOutputDir   (), OUTPUT_DIR_VALUE   ));        
        assertTrue(compareValues(context.getScriptPath  (), SCRIPT_FILE_VALUE  ));
        assertTrue(compareValues(context.getTemplateFile(), TEMPLATE_FILE_VALUE));
    }

    /**
     * Ensures custom configuration options match the input file's 
     * information.
     * @param context
     */
    public void testCustomConfigValues() throws Exception {
        //MDAContext context = readConfig();
        
        assertTrue(compareValues(context.get("tablePrefix" ), TABLE_PREFIX_VALUE ));
        assertTrue(compareValues(context.get("columnPrefix"), COLUMN_PREFIX_VALUE));        
    }

    /**
     * <p>Ensures the base context's property values match the context's  
     * Map values.</p>
     * 
     * <p>Classes that extend MDAContext with extra properties should 
     * provide their own implementation.</p>
     * 
     * @param context a context for testing.
     */
    public void testConfigInternalConsistency() throws Exception {
        //MDAContext context = readConfig();
        
        assertTrue(compareValues(context.getModelPath   (), context.get(MDAContext.MODEL_PATH_KEY)));
        assertTrue(compareValues(context.getOutputDir   (), context.get(MDAContext.OUTPUT_DIR_KEY)));
        assertTrue(compareValues(context.getTemplateFile(), context.get(MDAContext.TEMPLATE_FILE_KEY)));
        assertTrue(compareValues(context.getScriptPath  (), context.get(MDAContext.SCRIPT_PATH_KEY)));
    }   
    
    /**
     * Ensures data is updated consistently.
     * 
     * @param context a context for update testing.
     */
    public void testDataUpdates() throws Exception {
        MDAContext context = readConfig();
        
        // The relationship between getModelPath and get(MODEL_PATH_KEY) is 
        // already tested, but let's be thorough.
        assertTrue(
                compareValues(context.getModelPath() , MODEL_PATH_VALUE)
        );
        assertTrue(
                compareValues(context.get(MDAContext.MODEL_PATH_KEY), MODEL_PATH_VALUE)
        );
        
        // Make sure updates via setModelPath take hold *and* are reflected in 
        // data returned by the "get" method.
        String newModelPath = "new model path";
        context.setModelPath(newModelPath);
        
        assertTrue(
                compareValues(context.getModelPath(), newModelPath)
        );
        assertTrue(
                compareValues(context.get(MDAContext.MODEL_PATH_KEY), newModelPath)
        );
        
        // Make sure updates via the "put" method take hold *and* are 
        // reflected in data returned by the getMoelPath method.         
        String newModelPath2 = "another model path";
        context.put(MDAContext.MODEL_PATH_KEY, newModelPath2);
        
        assertTrue(
                compareValues(context.getModelPath(), newModelPath2)
        );
        assertTrue(
                compareValues(context.get(MDAContext.MODEL_PATH_KEY), newModelPath2)
        );        
    }
    
    /**
     * <p>Preferred method for comparing context values.  This is 
     * necessitated by XML configuration storing information in 
     * <code>Node</code>s, and YAML storing simple <code>String</code>s.  As 
     * both return identical <code>toString</code> results, this method acts allows 
     * some generalization of test code.
     * 
     * @param contextValue a value from a context property or <code>get</code> 
     * method.
     * @param expectedValue the expected value of the context property, or the 
     * expected <code>toString</code> value of the property. 
     * @return whether the contextValue's value (or <code>toString</code> 
     * value) equals the expectedValue (or expectedValue's 
     * <code>toString</code> value.) 
     */
    protected boolean compareValues(Object contextValue, Object expectedValue) {
        log.finer(contextValue + " vs. " + expectedValue);
        if (contextValue == null && expectedValue == null) return true;
        if (contextValue == null || expectedValue == null) return false;
        
        if (contextValue.equals(expectedValue)) return true;
        
        return contextValue.toString().equals(expectedValue.toString());
    }
}
