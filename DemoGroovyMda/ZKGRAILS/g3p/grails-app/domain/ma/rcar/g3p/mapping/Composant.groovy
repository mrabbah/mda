

package ma.rcar.g3p.mapping

/**
 * Composant Domain Object
 */
class Composant{
    	
    String intitule	
       		   
    
    static belongsTo = [projet : Projet , composant : Composant]
	
    static hasMany = [composants : Composant]
	
    static mapping = {
        composants lazy:false
    }
    static constraints = {
		
        intitule()
       	
    }
}

