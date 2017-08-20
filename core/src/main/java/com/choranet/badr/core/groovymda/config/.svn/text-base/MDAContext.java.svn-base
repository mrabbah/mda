/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.groovymda.config;

import java.util.Map;

/**
 *  <p>An object that stores MDA configuration information.  Standard properties
 *  (modelPath, outputDir, etc) are provided, and custom key/values can be set
 *  using the {@link Map} interface methods.</p>
 *  
 *  <p>Note: implementing classes should synchronize property 
 *  <code>get</code>/<code>set</code> and {@linkPlain Map} 
 *  <code>get</code>/<code>put</code> results.</p>  
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public interface MDAContext extends Map {

    public static final String MODEL_PATH_KEY = "modelPath";
    public static final String OUTPUT_DIR_KEY = "outputDirectory";
    public static final String SCRIPT_PATH_KEY = "scriptPath";
    public static final String TEMPLATE_FILE_KEY = "templateFile";

    public abstract Object getModelPath();

    public abstract void setModelPath(Object modelPath);

    public abstract Object getOutputDir();

    public abstract void setOutputDir(Object outputDir);

    public abstract Object getScriptPath();

    public abstract void setScriptPath(Object scriptFile);

    public abstract Object getTemplateFile();

    public abstract void setTemplateFile(Object templateFile);
}
