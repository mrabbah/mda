package _PACKAGE_

class DroitUtilisateurService extends SuperService {

    static transactional = true

    def list() throws Exception {
        return super.list(DroitUtilisateur.class)
    }
}
