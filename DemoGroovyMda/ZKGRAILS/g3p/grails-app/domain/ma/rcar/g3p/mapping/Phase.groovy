

package ma.rcar.g3p.mapping

/**
 * Phase Domain Object
 */
class Phase{
    	
    Double chargeExterne	
    	
    Double chargeInterne	
    	
    Date dateDebut	
    	
    Date dateFin	
    	
    String intitule	
       		   
    
    static belongsTo = [projet : Projet]
	
    static hasMany = [taches : Tache]
	
    static mapping = {
        taches lazy:false
    }
    static constraints = {
		
        chargeExterne()
    	
        chargeInterne()
    	
        dateDebut()
    	
        dateFin()
    	
        intitule()
       	
    }
}

