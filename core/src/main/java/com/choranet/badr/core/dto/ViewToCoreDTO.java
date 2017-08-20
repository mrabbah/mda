/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.dto;

import com.choranet.badr.core.groovymda.ProcessorEnum;
import com.choranet.badr.core.model.Clazz;
import com.choranet.badr.core.model.CoucheVueEnum;
import com.choranet.badr.core.model.commande.BriquesTechniquesCommandes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description ViewToCoreDTO data transfert object from view
 * layer to core layer.
 * @version 1.0-SNAPSHOT
 * @date 17 févr. 2011
 * @author rabbah
 */
public class ViewToCoreDTO {

    private File xmiFile;
    private File outputDirectory;
    private String projectName;
    private ProcessorEnum processor;
    private List<BriquesTechniquesCommandes> briquesTechniques;
    private List<Clazz> classToScaffold;
    private CoucheVueEnum coucheVue;

    public List<BriquesTechniquesCommandes> getBriquesTechniques() {
        if(briquesTechniques == null) {
            briquesTechniques = new ArrayList<BriquesTechniquesCommandes>();
        }
        return briquesTechniques;
    }

    public List<Clazz> getClassToScaffold() {
        if(classToScaffold == null) {
            classToScaffold = new ArrayList<Clazz>();
        }
        return classToScaffold;
    }

    public CoucheVueEnum getCoucheVue() {
        return coucheVue;
    }

    public void setCoucheVue(CoucheVueEnum coucheVue) {
        this.coucheVue = coucheVue;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public File getXmiFile() {
        return xmiFile;
    }

    public void setXmiFile(File xmiFile) {
        this.xmiFile = xmiFile;
    }

    public ProcessorEnum getProcessor() {
        return processor;
    }

    public void setProcessor(ProcessorEnum processor) {
        this.processor = processor;
    }

    
}
