package ma.rcar.g3p.mapping

/**
 * Request Map domain class.
 */
class DroitUtilisateur {

	String url
	String configAttribute

	static constraints = {
		url(blank: false, unique: true)
		configAttribute(blank: false)
	}
}
