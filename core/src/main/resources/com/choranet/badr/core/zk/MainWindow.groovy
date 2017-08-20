package _PACKAGE_

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory
import org.zkoss.zksandbox.Category
import org.springframework.web.context.request.RequestContextHolder


/**
 *
 * @author RABBAH
 */
class MainWindow extends Window {
    
    /**
     * Logger de la class ArticleWindow
     **/
    private Log logger = LogFactory.getLog(MainWindow.class)
    
    def authenticateService
    
    def session
    
    //Page inclue par défaut pour l'utilisateur selon son role
    def currentinclude
    
    /**
     * liste categories pour le menu principal
     **/
    def categories =  []
    
    public MainWindow(authenticateService) {                
        if(!this.authenticateService) {
            this.authenticateService = authenticateService
            session = RequestContextHolder.currentRequestAttributes().getSession()
        
            if(!session.commande) {
                def utilisateur = authenticateService.userDomain()
                session.utilisateur = utilisateur
            }
        }
    }
   
    def getCategories() {
        if(this.categories.size() == 0) {
            if(session) {
                def currentUser = session.utilisateur 
                def gp_utilisateurs = false;
                def gp_gestionnaires = false;
                def gp_admin = false;
                if(currentUser) {
                    for(GroupeUtilisateur gu : currentUser.authorities) {
                        if(gu.authority == 'ROLE_UTILISATEUR') {
                            gp_utilisateurs = true;
                            
                        }  else if (gu.authority == 'ROLE_GESTION') {
                            gp_gestionnaires = true;
                            
                        } else if (gu.authority == 'ROLE_ADMIN') {
                            gp_admin = true;
                        }
                    } 
                    if(gp_utilisateurs) {
                        this.categories += [new Category("cg_rechercher", "/images/skin/ReadingGlass-128x128.png", "Rechercher", "/zul/recherche/recherche.zul"),
                            new Category("cg_commande", "/images/skin/cart.png", "Commande", "/zul/commande/commande.zul")]
                    }
                    if (gp_gestionnaires) {
                        this.categories += [new Category("cg_article", "/images/skin/product-icon-header.png", "Articles", "/zul/article/article.zul"),
                            new Category("cg_sousfamille", "/images/skin/cubes.png", "Sous familles", "/zul/sousfamille/sousFamille.zul")]
                    }
                    if(gp_admin) {
                        this.categories += [new Category("cg_gressource", "/images/skin/icon_users.png", "Utilisateurs", "/zul/root/utilisateur.zul"), 
                            new Category("cg_droits", "/images/skin/39px-Administrateur.png", "Droits Utilisateurs", "/zul/root/droitUtilisateur.zul")]
                    }
                }
            }
            this.categories += [new Category("cg_logout", "/images/skin/48px-Vista-logout.png", "Se déconnecter", "/logout.zul")]
        }
        return this.categories
    }
    
    def getCurrentinclude() {
        if(session) {
            def currentUser = session.utilisateur 
            def gp_utilisateurs = false;
            def gp_gestionnaires = false;
            def gp_admin = false;
            if(currentUser) {
                for(GroupeUtilisateur gu : currentUser.authorities) {
                    if(gu.authority == 'ROLE_UTILISATEUR') {
                        gp_utilisateurs = true;
                    }  else if (gu.authority == 'ROLE_GESTION') {
                        gp_gestionnaires = true;
                    } else if (gu.authority == 'ROLE_ADMIN') {
                        gp_admin = true;
                    }
                } 
                if(gp_utilisateurs) {
                    this.currentinclude = "/zul/recherche/recherche.zul"
                } else if (gp_gestionnaires) {
                     this.currentinclude = "/zul/article/article.zul"
                } else if (gp_admin) {
                     this.currentinclude = "/zul/root/utilisateur.zul"
                }
            }
        }
        return this.currentinclude
    }
}

