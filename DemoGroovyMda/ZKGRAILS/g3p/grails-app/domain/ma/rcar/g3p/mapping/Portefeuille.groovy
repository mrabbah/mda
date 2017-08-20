

package ma.rcar.g3p.mapping

/**
 * Portefeuille Domain Object
 */
class Portefeuille{
    	
    String code	
    	
    String intitule	
       		   
    
    static hasMany = [projets : Projet , programmes : Programme]
	
    static mapping = {
        projets lazy:false
        programmes lazy:false
    }
    static constraints = {
		
        code(unique:true)
    	
        intitule()
       	
    }
}

