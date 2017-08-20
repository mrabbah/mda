

package ma.rcar.g3p.mapping

/**
 * Ressource Domain Object
 */
class Ressource{
    	
    Double coutUtilisation	
    	
    String nomPrenom	
    	
    String role	
    	
    Double tauxTravail	
    	
    String type	
       		   
                       
    Utilisateur utilisateur
    
    static belongsTo = [calendrier : Calendrier , groupeRessource : GroupeRessource]
	
    static mapping = {
        utilisateur cascade:"all"
    }
    static constraints = {
		
        coutUtilisation()
    	
        nomPrenom()
    	
        role()
    	
        tauxTravail()
    	
        type(inList:["Matériel", "Humaine"])

        utilisateur(nullable: true)
       	
    }
}

