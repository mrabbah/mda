

package ma.rcar.g3p.mapping

/**
 * MembreComitePilotage Domain Object
 */
class MembreComitePilotage{
    	
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

