

package ma.rcar.g3p.mapping

/**
 * ChefProjet Domain Object
 */
class ChefProjet{
    	
    Double tauxTravail	
       		   
                       
    Utilisateur utilisateur
    
    static hasMany = [taches : Tache]
	
    static mapping = {
        utilisateur cascade:"all"
        taches lazy:false
    }
    static constraints = {
		
        tauxTravail(min:0f)
       	
    }
}

