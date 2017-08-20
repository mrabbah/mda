

package ma.rcar.g3p.mapping

/**
 * GroupeRessource Domain Object
 */
class GroupeRessource{
    	
    Double coutUtilisation	
    	
    String intitule	
    	
    Double tauxTravail	
    	
    String type	
       		   
    
    static belongsTo = [calendrier : Calendrier]
	
    static hasMany = [ressources : Ressource]
	
    static mapping = {
        ressources lazy:false
    }
    static constraints = {
		
        coutUtilisation()
    	
        intitule(unique:true)
    	
        tauxTravail()
    	
        type(inList:["Interne", "Externe"])
       	
    }
}

