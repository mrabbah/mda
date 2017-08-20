

package ma.rcar.g3p.mapping

/**
 * Tache Domain Object
 */
class Tache{
    	
    Double avancementP	
    	
    Double avancementR	
    	
    Double chargeP	
    	
    Double chargeR	
    	
    Date dateDebutP	
    	
    Date dateDebutR	
    	
    Date dateFinP	
    	
    Double dateFinR	
    	
    String description	
    	
    String intitule	
    	
    String priorite	
    	
    String status	
       		   
    
    static belongsTo = [chefProjet : ChefProjet , phase : Phase , tache : Tache]
	
    static hasMany = [ressources : Ressource , taches : Tache , taches : Tache]
	
    static mapping = {
        ressources lazy:false
        taches lazy:false
        taches lazy:false
    }
    static constraints = {
		
        avancementP()
    	
        avancementR()
    	
        chargeP()
    	
        chargeR()
    	
        dateDebutP()
    	
        dateDebutR()
    	
        dateFinP()
    	
        dateFinR()
    	
        description()
    	
        intitule()
    	
        priorite()
    	
        status()
       	
    }
}

