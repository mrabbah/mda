class GormZkProcessor extends GroovyModelProcessor {

	void process(Map context) {
		//Variable qui stock les commandes pour la génération des controleurs grails
		def commande = '@echo off\ncolor a\ntitle Generateur des Controleurs\n'
		//Variable qui stoque les commandes d'exécution de l'application grails
		def commande2 = '@echo off\ncolor a\ntitle Run Application\ncall grails -Dserver.port=9595 run-app'
		//Variable qui va contenir les beans qui seront déclaré au niveau de spring pour l'application grails "resources.groovy"
		def beansDef = '// Place your Spring DSL code here\nbeans = { '
		
		/**************************************************************************/
		// Génération de la couche mapping "GORM"
		/**************************************************************************/
		// ITERATE THROUGH EACH CLASS IN THE MODEL
		getAllClasses(context.model).each { modelElement ->
			// ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
			context.currentModelElement = modelElement

			// GET THE FULLY QUALIFIED NAME FOR THE CLASS
			def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)
			
			// ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
			if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {				
				commande += 'call grails generate-controller ' + fullyQualifiedName + ' >> log.txt\necho generation du controleur et vue du domain '+ fullyQualifiedName +' termine avec success!\n'
				def chars = modelElement.name.toCharArray()
				chars[0] = Character.toLowerCase(chars[0])
				def lowerNomClass = new String(chars)
				beansDef += '\n    ' + lowerNomClass + 'Window(' + modelElement.name + 'Window) { bean ->\n        bean.scope = "prototype"\n        bean.autowire = "byName"\n    }'				
				
				// SET THE TEMPLATE TO USE
				def templateName = "GORM.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "grails-app/domain/${fullyQualifiedName.replace('.','/')}.groovy"

				// PROCESS THE TEMPLATE
				processTemplate(templateName, outputName, context)

			}
		}
		
		/**************************************************************************/
		//Partie génération des fichiers
		/**************************************************************************/
		beansDef += '\n}'
		def f = new File('runControllerGenerator.cmd')
		f << commande
		def f2 = new File('run.cmd')
		f2 << commande2
		def f3 = new File('resources.groovy')
		f3 << beansDef
		println f.absolutePath
		println f.size()
		println f2.absolutePath
		println f2.size()
		println f3.absolutePath
		println f3.size()
		
		/**************************************************************************/
		//Partie génération des Tests unitaire pour la couche mapping "GORM"
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
				def templateName = "GORM_Unit_Tests.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "test/unit/${fullyQualifiedName.replace('.','/')}.groovy"

				// PROCESS THE TEMPLATE
				processTemplate(templateName, outputName, context)

			}
		}
		
		/**************************************************************************/
		//Partie génération des controleurs des vues zk (Window)
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
				def templateName = "WINDOW.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "grails-app/windows/${modelElement.name}Window.groovy"

				// PROCESS THE TEMPLATE
				processTemplate(templateName, outputName, context)

			}
		}
		
		
		/**************************************************************************/
		//Parite génération des vues zk
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
				def templateName = "ZUL.gtl"
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
	
	def lowerRange = { associationEnd ->
       def x = 0
       associationEnd.multiplicity.range.each{ x += it.lower }
       return x
   }
   
	def prepareBinding = { map ->
       def binding = [
           "javaToSql":javaToSql,
           "javaType":javaType,
           "firstCharUpper":firstCharUpper,
           "firstCharLower":firstCharLower,
           "getPackageName":getPackageName,
           "getAttributes":getAttributes,
           "getAssociationEnds":getAssociationEnds,
           "getEndType":getEndType,
           "getEndName":getEndName,
           "taggedValues":taggedValues,
           "isOneToOne":isOneToOne,
           "isOneToMany":isOneToMany,
           "isManyToOne":isManyToOne,
           "isManyToMany":isManyToMany,
           "isOwner":isOwner,
           "isCollection":isCollection,
		   "upperRange":upperRange,
		   "lowerRange":lowerRange
       ]
       if (map) {
           binding.putAll(map)
       }
       return binding
   }
    
}