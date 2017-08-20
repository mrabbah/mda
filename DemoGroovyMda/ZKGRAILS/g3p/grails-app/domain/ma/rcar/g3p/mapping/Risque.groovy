

package ma.rcar.g3p.mapping

/**
 * Risque Domain Object
 */
class Risque{
    	
    String approcheMitigation	
    	
    String categorie	
    	
    String intitule	
    	
    String probabilite	
       		   
    
    static belongsTo = [projet : Projet]
	
    static mapping = {
    }
    static constraints = {
		
        approcheMitigation()
    	
        categorie()
    	
        intitule()
    	
        probabilite(inList:["Faible", "Moyenne", "Elevée"])
       	
    }
}

