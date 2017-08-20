

package ma.rcar.g3p.mapping

/**
 * Projet Domain Object
 */
class Projet{
    	
    String code	
    	
    Date dateDebut	
    	
    Date dateFin	
    	
    String directionBenificiaire	
    	
    String intitule	
    	
    String libelle	
    	
    String oportunites	
    	
    String priorite	
    	
    String status	
       		   
                       
    Calendrier calendrier
    
    static belongsTo = [chefProjet : ChefProjet , portefeuille : Portefeuille , programme : Programme]
	
    static hasMany = [ressources : Ressource , phases : Phase , membreComiteSuivis : MembreComiteSuivi , composants : Composant , membreMoas : MembreMoa , rubriqueBudgetaires : RubriqueBudgetaire , membreComitePilotages : MembreComitePilotage , opportunites : Opportunite , livrables : Livrable , membreMoes : MembreMoe , opportunites : Opportunite , risques : Risque]
	
    static mapping = {
        ressources lazy:false
        phases lazy:false
        membreComiteSuivis lazy:false
        composants lazy:false
        calendrier cascade:"all"
        membreMoas lazy:false
        rubriqueBudgetaires lazy:false
        membreComitePilotages lazy:false
        opportunites lazy:false
        livrables lazy:false
        membreMoes lazy:false
        opportunites lazy:false
        risques lazy:false
    }
    static constraints = {
		
        code(unique:true)
    	
        dateDebut()
    	
        dateFin()
    	
        directionBenificiaire()
    	
        intitule()
    	
        libelle()
    	
        oportunites()
    	
        priorite()
    	
        status()
       	
    }
}

