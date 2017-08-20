/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.commandrunner;

import java.io.File;
import junit.framework.TestCase;

/**
 *
 * @author rabbah
 */
public class CommandRunnerTest extends TestCase {
    
    public CommandRunnerTest(String testName) {
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
     * Test of run method, of class CommandRunner.
     */
    public void testRun_String() {
        String command = "help";
        RunCommandStatusEnum result = CommandRunner.run(command);
        assertEquals(RunCommandStatusEnum.SUCCEE, result);
        
    }

    /**
     * Test of run method, of class CommandRunner.
     */
    public void testRun_StringArr() {
        String[] commands = {"help", "dir"};
        RunCommandStatusEnum result = CommandRunner.run(commands);
        assertEquals(RunCommandStatusEnum.SUCCEE, result);
        
    }

    /**
     * Test of run method, of class CommandRunner.
     */
    public void testRun_String_File() {
        String command = "help";
        File directory = new File(".");
        RunCommandStatusEnum result = CommandRunner.run(command, directory);
        assertEquals(RunCommandStatusEnum.SUCCEE, result);
        
    }

    /**
     * Test of run method, of class CommandRunner.
     */
    public void testRun_StringArr_File() {
        String[] commands = {"help","dir"};
        File directory = new File(".");
        RunCommandStatusEnum result = CommandRunner.run(commands, directory);
        assertEquals(RunCommandStatusEnum.SUCCEE, result);
        
    }

}
