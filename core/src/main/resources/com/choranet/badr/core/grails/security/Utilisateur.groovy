package _PACKAGE_


/**
 * User domain class.
 */
class Utilisateur {
    static transients = ['pass']
    static hasMany = [authorities: GroupeUtilisateur]

    /** Username */
    String username
    /** User Real Name*/
    String userRealName
    /** MD5 Password */
    String passwd
    /** enabled */
    boolean enabled

    String email
    boolean emailShow

    /** description */
    String description = ''

    /** plain password to create a MD5 password */
    String pass = '[secret]'

    static constraints = {
        username(blank: false, unique: true)
        userRealName(blank: false)
        passwd(blank: false)
        enabled()
    }

    static mapping = {
        authorities lazy : false
        batchSize: 14
    }
    String toString() {
        return username
    }
    
}
