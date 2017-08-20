/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

/**
 * @description : Moulinette qui utilise le diagramme uml puis pour chaque
 * class execute la template Jpa
 * @author : rabbah
 * @date : 12/02/2011
 * @version: 1.0
 **/
package com.choranet.badr.core.groovymda.processors;

class JpaProcessor extends GroovyModelProcessor implements IProcessing {
   
    void process(Map context) {
        getAllClasses(context.model).each { modelElement ->

            context.currentModelElement = modelElement

            def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)
            if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {
                def templateName = (context.templateFile != null ?
                    context.templateFile.toString() :
                   "com/choranet/badr/core/groovymda/templates/JpaEntity.gtl"
                )
                def outputName = "${fullyQualifiedName.replace('.','/')}.java"

                processTemplate(templateName, outputName, context)
            }
        }
    }
}
