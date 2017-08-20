security {

    // see DefaultSecurityConfig.groovy for all settable/overridable properties

    active = true

    loginUserDomainClass = "_PACKAGE_.Utilisateur"
    authorityDomainClass = "_PACKAGE_.GroupeUtilisateur"
    requestMapClass = "_PACKAGE_.DroitUtilisateur"
        
    authenticationFailureUrl = '/login.zul?login_error=true'
    loginFormUrl = '/login.zul'
    cookieName = '_APPNAME__remember_me'
    errorPage = '/denied.zul'
    defaultTargetUrl = '/zul/main.zul'
}
