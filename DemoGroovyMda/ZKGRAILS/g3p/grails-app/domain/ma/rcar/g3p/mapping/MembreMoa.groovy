

package ma.rcar.g3p.mapping

/**
 * MembreMoa Domain Object
 */
class MembreMoa{
    	
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

