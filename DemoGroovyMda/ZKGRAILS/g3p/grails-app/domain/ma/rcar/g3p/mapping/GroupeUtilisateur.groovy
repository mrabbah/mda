package ma.rcar.g3p.mapping

import ma.rcar.g3p.mapping.Utilisateur

/**
 * Authority domain class.
 */
class GroupeUtilisateur {

	static hasMany = [people: Utilisateur]

	/** description */
	String description
	/** ROLE String */
	String authority

	static constraints = {
		authority(blank: false, unique: true)
		description()
	}
}
