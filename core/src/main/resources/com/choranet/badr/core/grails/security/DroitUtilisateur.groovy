package _PACKAGE_

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
        
    String toString() {
        return (url . " : " . configAttribute)
    }
}
