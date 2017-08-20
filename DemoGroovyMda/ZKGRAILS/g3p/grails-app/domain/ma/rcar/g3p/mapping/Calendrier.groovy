
package ma.rcar.g3p.mapping

/**
 * Calendrier Domain Object
 */
class Calendrier{
    	
    Integer heuresParJour	
    	
    Integer heuresParSemaines	
    	
    String intitule	
    	
    Integer joursParMois	
       		   
    
    static hasMany = [ressources : Ressource , joursChomess : JoursChomes , jourSemaineOuvres : JourSemaineOuvre , groupeRessources : GroupeRessource]
	
    static mapping = {
        ressources lazy:false
        joursChomess lazy:false
        jourSemaineOuvres lazy:false
        groupeRessources lazy:false
    }
    static constraints = {
		
        heuresParJour(range:1..24)
    	
        heuresParSemaines(range:8..100)
    	
        intitule(blank:false, unique:true)
    	
        joursParMois(range:1..31)
       	
    }
}

