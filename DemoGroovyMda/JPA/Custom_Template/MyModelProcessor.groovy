class MyModelProcessor extends GroovyModelProcessor {

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
				context.helloWorld = { aString ->
					return "Hello ${aString}!"
				}	
				
				// SET THE TEMPLATE TO USE
				def templateName = "MyJpaEntity.gtl"

				// SET THE OUTPUT FILE NAME FOR THE FULLY QUALIFIED NAME
				def outputName = "${fullyQualifiedName.replace('.','/')}.java"

				// PROCESS THE TEMPLATE
				processTemplate(templateName, outputName, context)

			}
		}
	}
    
}