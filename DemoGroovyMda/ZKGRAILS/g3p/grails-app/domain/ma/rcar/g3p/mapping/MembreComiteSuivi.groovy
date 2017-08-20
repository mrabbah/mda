

package ma.rcar.g3p.mapping

/**
 * MembreComiteSuivi Domain Object
 */
class MembreComiteSuivi{
    	
    String nomPrenom	
    	
    String role	
       		   
    
    static belongsTo = [projet : Projet]
	
    static mapping = {
    }
    static constraints = {
		
        nomPrenom()
    	
        role()
       	
    }
}

