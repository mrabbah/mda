/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

/**
 * @description : Moulinette qui utilise le diagramme uml puis pour chaque
 * class execute la template ZK adequate
 * @author : rabbah
 * @date : 12/02/2011
 * @version: 1.0
 **/
package com.choranet.badr.core.groovymda.processors;

class ZkProcessor extends GroovyModelProcessor implements IProcessing {

    void process(Map context) {
		
        //Variable qui va contenir les beans qui seront d�clar� au niveau de spring pour l'application grails "resources.groovy"
        def beansDef = """\
           // Place your Spring DSL code here\nbeans = { 
            utilisateurWindow(com.choranet.pmcatalogue.UtilisateurWindow) { bean ->
                bean.scope = "prototype"
                bean.autowire = "byName"
            }
    
            droitUtilisateurWindow(com.choranet.pmcatalogue.DroitUtilisateurWindow) { bean ->
                bean.scope = "prototype"
                bean.autowire = "byName"
            }
    
            loginWindow(com.choranet.pmcatalogue.LoginWindow) { bean ->
                bean.scope = "prototype"
                bean.autowire = "byName"
            }
    
            mainWindow(com.choranet.pmcatalogue.MainWindow, ref("authenticateService")) { bean ->
                bean.scope = "prototype"
                bean.autowire = "byName"
            }
        """
		
        /**************************************************************************/
        //Partie g�n�ration des controleurs des vues zk (Window)
        /**************************************************************************/
        // ITERATE THROUGH EACH CLASS IN THE MODEL
        getAllClasses(context.model).each { modelElement ->
            // ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
            context.currentModelElement = modelElement

            // GET THE FULLY QUALIFIED NAME FOR THE CLASS
            def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)
			
            // ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
            if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {

                // SET THE TEMPLATE TO USE
                def templateName = "com/choranet/badr/core/groovymda/templates/WINDOW.gtl"

                // SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
                def outputName = "grails-app/windows/${fullyQualifiedName.replace('.','/')}Window.groovy"

                // PROCESS THE TEMPLATE
                processTemplate(templateName, outputName, context)

                def chars = modelElement.name.toCharArray()
                chars[0] = Character.toLowerCase(chars[0])
                def lowerNomClass = new String(chars)
                beansDef += '\n    ' + lowerNomClass + 'Window(' + fullyQualifiedName + 'Window) { bean ->\n        bean.scope = "prototype"\n        bean.autowire = "byName"\n    }'
            }
        }

        /**************************************************************************/
        //Partie g�n�ration des fichiers
        /**************************************************************************/
        beansDef += '\n}'
        def f3 = new File(context.getOutputDir() + '/grails-app/conf/spring/resources.groovy')
        f3.parentFile.mkdirs()
        f3.delete()
        f3.createNewFile()
        f3 << beansDef

        println f3.absolutePath
		
        /**************************************************************************/
        //Parite g�n�ration des vues zk
        /**************************************************************************/
        // ITERATE THROUGH EACH CLASS IN THE MODEL
        getAllClasses(context.model).each { modelElement ->
            // ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
            context.currentModelElement = modelElement

            // GET THE FULLY QUALIFIED NAME FOR THE CLASS
            def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)
			
            // ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
            if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {

				
                // SET THE TEMPLATE TO USE
                def templateName = "com/choranet/badr/core/groovymda/templates/ZUL.gtl"
                def chars = modelElement.name.toCharArray()
                chars[0] = Character.toLowerCase(chars[0])
                def lowerNomClass = new String(chars)
                // SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
                def outputName = "web-app/${lowerNomClass}.zul"

                // PROCESS THE TEMPLATE
                processTemplate(templateName, outputName, context)

            }
        }
    }
}