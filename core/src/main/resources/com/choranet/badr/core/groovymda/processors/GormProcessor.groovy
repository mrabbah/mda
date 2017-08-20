/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

/**
 * @description : Moulinette qui utilise le diagramme uml puis pour chaque
 * class execute la template GORM adequate
 * @author : rabbah
 * @date : 12/02/2011
 * @version: 1.0
 **/
package com.choranet.badr.core.groovymda.processors;

class GormProcessor extends GroovyModelProcessor implements IProcessing {
    
    void process(Map context) {
        // ITERATE THROUGH EACH CLASS IN THE MODEL
        getAllClasses(context.model).each { modelElement ->
            // ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
            context.currentModelElement = modelElement

            // GET THE FULLY QUALIFIED NAME FOR THE CLASS
            def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)

            // ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
            if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {
                // YOU CAN BIND CLOSURES TO THE CONTEXT TO MAKE THEM ACCESSIBLE TO YOU TEMPLATES

                // SET THE TEMPLATE TO USE
                def templateName = "com/choranet/badr/core/groovymda/templates/GORM.gtl"

                // SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
                def outputName = "grails-app/domain/${fullyQualifiedName.replace('.','/')}.groovy"

                // PROCESS THE TEMPLATE
                processTemplate(templateName, outputName, context)

            }
        }
        // ITERATE THROUGH EACH CLASS IN THE MODEL
        getAllClasses(context.model).each { modelElement ->
            // ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
            context.currentModelElement = modelElement

            // GET THE FULLY QUALIFIED NAME FOR THE CLASS
            def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)

            // ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
            if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {

                // SET THE TEMPLATE TO USE
                def templateName = "com/choranet/badr/core/groovymda/templates/GORM_Unit_Tests.gtl"

                // SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
                def outputName = "test/unit/${fullyQualifiedName.replace('.','/')}.groovy"

                // PROCESS THE TEMPLATE
                processTemplate(templateName, outputName, context)

            }
        }
    }
    
}