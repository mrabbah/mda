package _PACKAGE_

//import com.choranet.pmcatalogue.Utilisateur

/**
 * Authority domain class.
 */
class GroupeUtilisateur {

    static hasMany = [people: Utilisateur]
    static belongsTo = Utilisateur
    
    /** description */
    String description
    /** ROLE String */
    String authority

    static constraints = {
        authority(blank: false, unique: true)
        description()
    }
    
    static mapping = {
        people fetch : 'join'
    }
        
    String toString() {
        return description
    }
}
