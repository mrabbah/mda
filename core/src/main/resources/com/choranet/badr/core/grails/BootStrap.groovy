class BootStrap {

    def authenticateService
    
    def init = { servletContext ->
        environments {
            development {
                def roleAdmin = new _PACKAGE_.GroupeUtilisateur(authority:'ROLE_ADMIN', description:'Administrateur').save() 
                def roleGestion = new _PACKAGE_.GroupeUtilisateur(authority:'ROLE_GESTION', description:'Gestion d application').save() 
                def roleUtilisateur = new _PACKAGE_.GroupeUtilisateur(authority:'ROLE_UTILISATEUR', description:'Utilisateur final').save()
        
                def userRoot = new _PACKAGE_.Utilisateur(username:'root',
                    userRealName:'root',
                    enabled: true,
                    emailShow: true,
                    email: 'admin@choranet.com',
                    passwd: authenticateService.encodePassword('root')).save()
                
                def userAdmin = new _PACKAGE_.Utilisateur(username:'admin',
                    userRealName:'admin',
                    enabled: true,
                    emailShow: true,
                    email: 'admin@choranet.com',
                    passwd: authenticateService.encodePassword('admin')).save()
        
                def userGestion = new _PACKAGE_.Utilisateur(username:'gestion',
                    userRealName:'gestion',
                    enabled: true,
                    emailShow: true,
                    email: 'gestion@choranet.com',
                    passwd: authenticateService.encodePassword('gestion')).save()
               
                def userUtilisateur = new _PACKAGE_.Utilisateur(username:'utilisateur',
                    userRealName:'utilisateur',
                    enabled: true,
                    emailShow: true,
                    email: 'user@choranet.com',
                    passwd: authenticateService.encodePassword('utilisateur')).save()
         
                def secureAdmin = new _PACKAGE_.DroitUtilisateur(url: '/zul/**',
                    configAttribute:'ROLE_ADMIN').save()
                def secureGestion1 = new _PACKAGE_.DroitUtilisateur(url: '/zul/marque/**',
                    configAttribute:'ROLE_GESTION').save()
                def secureGestion2 = new _PACKAGE_.DroitUtilisateur(url: '/zul/article/**',
                    configAttribute:'ROLE_GESTION').save()
                def secureGestion3 = new _PACKAGE_.DroitUtilisateur(url: '/zul/famille/**',
                    configAttribute:'ROLE_GESTION').save()
                def secureGestion4 = new _PACKAGE_.DroitUtilisateur(url: '/zul/sousfamille/**',
                    configAttribute:'ROLE_GESTION').save()
                
                def secureUtilisateur1 = new _PACKAGE_.DroitUtilisateur(url: '/zul/recherche/**',
                    configAttribute:'ROLE_UTILISATEUR').save()
                def secureUtilisateur2 = new _PACKAGE_.DroitUtilisateur(url: '/zul/commande/**',
                    configAttribute:'ROLE_UTILISATEUR').save()
                userRoot.addToAuthorities(roleAdmin)    
                userRoot.addToAuthorities(roleGestion)
                userRoot.addToAuthorities(roleUtilisateur)
                userAdmin.addToAuthorities(roleAdmin)  
                userGestion.addToAuthorities(roleGestion)
                userUtilisateur.addToAuthorities(roleUtilisateur)
      
                
            }
            test {
        
            }
            production {
        
            }
        }
                
    }
    def destroy = {
    }
}
