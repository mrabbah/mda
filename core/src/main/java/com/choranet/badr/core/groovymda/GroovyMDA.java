/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.groovymda;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.jmi.model.ModelElement;
import javax.jmi.model.ModelPackage;
import javax.jmi.reflect.RefPackage;

import com.choranet.badr.core.groovymda.config.BaseMDAContext;
import com.choranet.badr.core.groovymda.config.MDAContext;
import com.choranet.badr.core.model.Attribut;
import com.choranet.badr.core.model.Clazz;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.netbeans.api.mdr.MDRManager;
import org.netbeans.api.mdr.MDRepository;
import org.netbeans.api.xmi.XMIReaderFactory;
import org.omg.uml.UmlPackage;
import org.omg.uml.foundation.core.Attribute;
import org.omg.uml.foundation.core.Namespace;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.modelmanagement.Model;
import org.omg.uml.foundation.core.Classifier;

/**
 *  The main class for the GroovyMDA tool
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public class GroovyMDA {

    private static final Logger log = Logger.getLogger(GroovyMDA.class.toString());
    private MDRepository repository;
    private ModelElement metaModelElement;
    private MDAContext context;
    private GroovyObject modelProcessor;
    private List<Clazz> classes;

    public GroovyMDA(String xmiPathFile) throws Exception {
        context = new BaseMDAContext();
        context.setModelPath(xmiPathFile);
        try {
            initialize();
            loadAllClasses();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *  Initializes the MDR
     *
     * @exception  Exception
     */
    private void initialize() throws Exception {
        System.setProperty(
                STORAGE_FACTORY_NAME,
                STORAGE_FACTORY_IMPL);

        repository = MDRManager.getDefault().getDefaultRepository();
        repository.beginTrans(true);

        String metaModelName = getClass().getResource(META_MODEL_PATH).toExternalForm();
        RefPackage metaModelExtent = repository.getExtent(metaModelName);
        if (metaModelExtent == null) {
            metaModelExtent = (ModelPackage) repository.createExtent(metaModelName);
        }

        metaModelElement = findModelElement(UML_MM, (ModelPackage) metaModelExtent);
        if (metaModelElement == null) {
            XMIReaderFactory.getDefault().createXMIReader().read(metaModelName, metaModelExtent);
            metaModelElement = findModelElement(UML_MM, (ModelPackage) metaModelExtent);
        }

    }

    private void loadAllClasses() throws Exception {
        classes = new ArrayList<Clazz>();
        RefPackage model = loadModel();
        if (model instanceof UmlPackage) {
            UmlPackage umlPackage = (UmlPackage) model;
            Collection<UmlClass> modelClasses = umlPackage.getCore().getUmlClass().refAllOfType();
            for (Iterator<UmlClass> it = modelClasses.iterator(); it.hasNext();) {
                UmlClass umlClass = it.next();
                Clazz clazz = new Clazz();
                String packageName = getPackageName(umlClass);
                if (packageName.startsWith("java")) {
                    continue;
                }
                clazz.setPackageName(packageName);
                clazz.setNom(umlClass.getName());
                Collection<Attribute> attributes =
                        findModelElementByClassType(umlClass, Attribute.class);
                for (Iterator<Attribute> it1 = attributes.iterator(); it1.hasNext();) {
                    Attribute attribute = it1.next();
                    Attribut attribut = new Attribut();
                    attribut.setNom(attribute.getName());
                    attribut.setType(attribute.getType().getName());
                    clazz.getAttributs().add(attribut);
                }
                classes.add(clazz);
            }
        }
    }

    private String getPackageName(org.omg.uml.foundation.core.ModelElement modelElement) {
        StringBuilder sb = new StringBuilder();
        Namespace namespace = modelElement.getNamespace();
        while (true) {
            if (namespace instanceof Model) {
                break;
            }
            if (sb.length() > 0) {
                sb.insert(0, ".");
            }
            sb.insert(0, namespace.getName());
            namespace = namespace.getNamespace();
        }
        return sb.toString().trim();
    }

    private <T> Collection<T> findModelElementByClassType(Classifier classifier, Class<T> type) {
        List<T> result = new ArrayList<T>();
        List features = classifier.getFeature();
        for (Iterator it = features.iterator(); it.hasNext();) {
            Object object = it.next();
            if (type.isAssignableFrom(object.getClass())) {
                result.add((T) object);
            }
        }
        return result;
    }

    /**
     *  Loads the model
     *
     * @exception  Exception
     * @return model
     */
    private RefPackage loadModel() throws Exception {
        InputStream in = PathHelper.pathToStream(context.getModelPath().toString());

        /* TODO: decide if we still require  modelName.  The docs say it's required for
        resolving relative URIs in the file.
        String modelName = modelUrl.toExternalForm();*/
        RefPackage modelExtent = repository.getExtent(MODEL_NAME);
        if (modelExtent != null) {
            modelExtent.refDelete();
        }
        modelExtent = repository.createExtent(MODEL_NAME, metaModelElement);
        XMIReaderFactory.getDefault().createXMIReader().read(in, null, modelExtent);
        return modelExtent;

    }

    /**
     *  Cleans up the MDR
     *
     * @exception  Exception
     */
    private void destroy() throws Exception {
        if (repository != null) {
            repository.endTrans();
        }
    }

    /**
     * lunch a processor for specific xmi file and generate source into output
     * directory
     * @param outputDirectory output directory
     * @param processor processor to lunch
     * @throws Exception exception
     */
    public void lunchProcessor(String outputDirectory, ProcessorEnum processor) throws Exception {
        try {
            context.setOutputDir(outputDirectory);
            switch (processor) {
                case GORM: {
                    context.setScriptPath(GORM_PROCESSOR_PATH);
                    break;
                }
                case JAVA_BEAN: {
                    context.setScriptPath(JAVABEAN_PROCESSOR_PATH);
                    break;
                }
                case JPA: {
                    context.setScriptPath(JPA_PROCESSOR_PATH);
                    break;
                }
                case ZK: {
                    context.setScriptPath(ZK_PROCESSOR_PATH);
                    break;
                }
                case GRAILSSERVICE: {
                    context.setScriptPath(GRAILSSERVICE_PROCESSOR_PATH);
                    break;
                }
            }

            ClassLoader parent = getClass().getClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parent);
            Class modelProcessorClass = loader.parseClass(getClass().getResourceAsStream((String) context.getScriptPath()));

            modelProcessor = (GroovyObject) modelProcessorClass.newInstance();
            RefPackage model = loadModel();
            context.put("model", model);
            modelProcessor.invokeMethod("process", context);
        } catch (Exception e) {
            throw e;
        }
//        finally {
//            try {
//                destroy();
//            } catch (Exception ee) {
//                throw ee;
//            }
//        }
    }

    /**
     * @see com.choranet.badr.core.groovymda.GroovyMDA#lunchProcessor(java.lang.String, com.choranet.badr.core.groovymda.ProcessorEnum) 
     */
    public void lunchProcessor(File outputDirectory, ProcessorEnum processor) throws Exception {
        lunchProcessor(outputDirectory.getAbsolutePath(), processor);
    }

    /**
     *  Helper method for finding a model element by name in a given package
     *
     * @param  name          the name of the model element.
     * @param  modelPackage  a UML model package for querying.
     * @return               the named element, or null.
     */
    private ModelElement findModelElement(String name, ModelPackage modelPackage) {
        ModelElement modelElement = null;
        for (Iterator i = modelPackage.getMofPackage().refAllOfClass().iterator(); i.hasNext();) {
            modelElement = (ModelElement) i.next();
            if (modelElement.getName().equals(name)) {
                return modelElement;
            }
        }
        return null;
    }

    public List<Clazz> getClasses() {
        return classes;
    }
    private static final String GROOVY_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/GroovyModelProcessor.groovy";
    private static final String GORM_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/GormProcessor.groovy";
    private static final String JPA_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/JpaProcessor.groovy";
    private static final String ZK_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/ZkProcessor.groovy";
    private static final String GRAILSSERVICE_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/GrailsServiceProcessor.groovy";
    private static final String JAVABEAN_PROCESSOR_PATH =
            "/com/choranet/badr/core/groovymda/processors/JavaBeanProcessor.groovy";
    private static final String STORAGE_FACTORY_NAME =
            "org.netbeans.mdr.storagemodel.StorageFactoryClassName";
    private static final String STORAGE_FACTORY_IMPL =
            "org.netbeans.mdr.persistence.memoryimpl.StorageFactoryImpl";
    private static final String META_MODEL_PATH =
            "/org/omg/uml/resources/01-02-15_Diff.xml";
    // name of a MOF extent that will contain definition of UML metamodel
    private static final String UML_MM = "UML";
    private static final String MODEL_NAME = "MODEL";
}
