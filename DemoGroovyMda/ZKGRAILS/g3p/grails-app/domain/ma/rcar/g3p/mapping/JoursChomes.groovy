

package ma.rcar.g3p.mapping

/**
 * JoursChomes Domain Object
 */
class JoursChomes{
    	
    Date dateDebut	
    	
    Date dateFin	
    	
    String intitule	
       		   
    
    static belongsTo = [calendrier : Calendrier]
	
    static mapping = {
    }
    static constraints = {
		
        dateDebut()
    	
        dateFin()
    	
        intitule()
       	
    }
}

