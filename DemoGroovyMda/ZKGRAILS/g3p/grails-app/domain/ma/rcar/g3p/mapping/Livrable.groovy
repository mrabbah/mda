

package ma.rcar.g3p.mapping

/**
 * Livrable Domain Object
 */
class Livrable{
    	
    Date dateLivraison	
    	
    String description	
    	
    String intitule	
       		   
    
    static belongsTo = [projet : Projet]
	
    static mapping = {
    }
    static constraints = {
		
        dateLivraison()
    	
        description()
    	
        intitule()
       	
    }
}

