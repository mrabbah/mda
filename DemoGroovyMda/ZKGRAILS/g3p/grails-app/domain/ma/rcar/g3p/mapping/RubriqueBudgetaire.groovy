

package ma.rcar.g3p.mapping

/**
 * RubriqueBudgetaire Domain Object
 */
class RubriqueBudgetaire{
    	
    String commentaire	
    	
    Double cout	
    	
    String intitule	
       		   
    
    static belongsTo = [projet : Projet]
	
    static mapping = {
    }
    static constraints = {
		
        commentaire()
    	
        cout()
    	
        intitule()
       	
    }
}

