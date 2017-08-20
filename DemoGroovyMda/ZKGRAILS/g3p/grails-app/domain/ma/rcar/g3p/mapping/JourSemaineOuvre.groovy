

package ma.rcar.g3p.mapping

/**
 * JourSemaineOuvre Domain Object
 */
class JourSemaineOuvre{
    	
    String intitule	
       		   
    
    static belongsTo = [calendrier : Calendrier]
	
    static hasMany = [heuresTravails : HeuresTravail]
	
    static mapping = {
        heuresTravails lazy:false
    }
    static constraints = {
		
        intitule(inList:["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"])
       	
    }
}

