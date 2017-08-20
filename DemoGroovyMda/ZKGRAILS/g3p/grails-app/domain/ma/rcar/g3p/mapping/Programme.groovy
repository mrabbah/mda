

package ma.rcar.g3p.mapping

/**
 * Programme Domain Object
 */
class Programme{
    	
    String code	
    	
    String intitule	
       		   
    
    static belongsTo = [portefeuille : Portefeuille]
	
    static hasMany = [projets : Projet]
	
    static mapping = {
        projets lazy:false
    }
    static constraints = {
		
        code(unique:true)
    	
        intitule()
       	
    }
}

