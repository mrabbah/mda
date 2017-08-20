class MyModelProcessor extends GroovyModelProcessor {

	void process(Map context) {
		def commande = '@echo off\ncolor a\ntitle Generateur Control View\n'
		def commande2 = '@echo off\ncolor a\ntitle Run Application\ncall grails run-app'
		// ITERATE THROUGH EACH CLASS IN THE MODEL
		getAllClasses(context.model).each { modelElement ->
			// ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
			context.currentModelElement = modelElement

			// GET THE FULLY QUALIFIED NAME FOR THE CLASS
			def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)

			// ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
			if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {				
				commande += 'call grails generate-all ' + fullyQualifiedName + ' >> log.txt\necho generation du controleur et vue du domain '+ fullyQualifiedName +' termine avec success!\n'
				// YOU CAN BIND CLOSURES TO THE CONTEXT TO MAKE THEM ACCESSIBLE TO YOU TEMPLATES
				context.helloWorld = { aString ->
					return "Hello ${aString}!"
				}	
				
				// SET THE TEMPLATE TO USE
				def templateName = "GORM.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "grails-app/domain/${fullyQualifiedName.replace('.','/')}.groovy"

				// PROCESS THE TEMPLATE
				processTemplate(templateName, outputName, context)

			}
		}
		def f = new File('runControllerViewGenerator.cmd')
		f << commande
		def f2 = new File('run.cmd')
		f2 << commande2
		println f.absolutePath
		println f.size()
		println f2.absolutePath
		println f2.size()
		// ITERATE THROUGH EACH CLASS IN THE MODEL
		getAllClasses(context.model).each { modelElement ->
			// ADD THE CURRENT MODEL ELEMENT TO THE CONTEXT
			context.currentModelElement = modelElement

			// GET THE FULLY QUALIFIED NAME FOR THE CLASS
			def fullyQualifiedName = getFullyQualifiedName(context.currentModelElement)
			
			// ONLY PROCESS NON JRE CLASSES (java.lang.String does not need to be generated)
			if (!fullyQualifiedName.startsWith("java") && fullyQualifiedName.size() > 0) {

				// YOU CAN BIND CLOSURES TO THE CONTEXT TO MAKE THEM ACCESSIBLE TO YOU TEMPLATES
				context.helloWorld = { aString ->
					return "Hello ${aString}!"
				}	
				
				// SET THE TEMPLATE TO USE
				def templateName = "GORM_Unit_Tests.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "test/unit/${fullyQualifiedName.replace('.','/')}.groovy"

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