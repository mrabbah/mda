

package ma.rcar.g3p.mapping

/**
 * HeuresTravail Domain Object
 */
class HeuresTravail{
    	
    Integer heureDebut	
    	
    Integer heureFin	
    	
    Integer minuteDebut	
    	
    Integer minuteFin	
       		   
    
	static belongsTo = [jourSemaineOuvre : JourSemaineOuvre]
	
	static mapping = { 
}
	static constraints = {
		
		heureDebut()	
    	
		heureFin()	
    	
		minuteDebut()	
    	
		minuteFin()	
       	
	}
}

