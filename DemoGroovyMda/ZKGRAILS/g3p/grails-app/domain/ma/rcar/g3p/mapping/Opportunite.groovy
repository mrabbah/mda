

package ma.rcar.g3p.mapping

/**
 * Opportunite Domain Object
 */
class Opportunite{
    	
    String description	
    	
    String intitule	
       		   
    
    static belongsTo = [projet : Projet]
	
    static mapping = {
    }
    static constraints = {
		
        description()
    	
        intitule()
       	
    }
}

