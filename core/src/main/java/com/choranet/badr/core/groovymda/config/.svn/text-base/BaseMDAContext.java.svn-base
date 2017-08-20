/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.groovymda.config;

import java.util.HashMap;

public class BaseMDAContext extends HashMap implements MDAContext {

    public BaseMDAContext() {
        
    }

    public Object getModelPath() {
        return get(MODEL_PATH_KEY);
    }

    public void setModelPath(Object modelPath) {
        put(MODEL_PATH_KEY, modelPath);
    }

    public Object getOutputDir() {
        Object value = get(OUTPUT_DIR_KEY);
        if (value == null) return ".";
        
        return get(OUTPUT_DIR_KEY);
    }

    public void setOutputDir(Object outputDir) {                
        this.put(OUTPUT_DIR_KEY, outputDir);
    }

    public Object getScriptPath() {
        return get(SCRIPT_PATH_KEY);
    }

    public void setScriptPath(Object scriptFile) {        
        put(SCRIPT_PATH_KEY, scriptFile);
    }

    public Object getTemplateFile() {
        return get(TEMPLATE_FILE_KEY);
    }
    
    public void setTemplateFile(Object templateFile) {
        put(TEMPLATE_FILE_KEY, templateFile);
    }
}
