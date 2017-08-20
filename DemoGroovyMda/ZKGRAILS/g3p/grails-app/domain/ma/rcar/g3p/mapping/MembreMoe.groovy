

package ma.rcar.g3p.mapping

/**
 * MembreMoe Domain Object
 */
class MembreMoe{
    	
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

