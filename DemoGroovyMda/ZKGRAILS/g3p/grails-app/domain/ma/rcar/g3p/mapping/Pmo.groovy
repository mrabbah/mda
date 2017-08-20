

package ma.rcar.g3p.mapping

/**
 * Pmo Domain Object
 */
class Pmo{
       		   
                       
    Utilisateur utilisateur
    
    static mapping = {
        utilisateur cascade:"all"
    }
    static constraints = {
	   	
    }
}

