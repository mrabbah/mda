import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * DroitUtilisateur Window Object
 **/
class DroitUtilisateurWindow extends Window{
	/**
	* Logger de la class DroitUtilisateurWindow
	**/
	private Log logger = LogFactory.getLog(DroitUtilisateurWindow.class)
	
	/**
	* liste de droitUtilisateur
	**/
	def droitUtilisateurs
	/**
	* droitUtilisateur selectionn�
	**/
	def droitUtilisateurSelected
	/**
	* un nouveau element de droitUtilisateur  
	**/
	def droitUtilisateur
	
	
	/**
	* Constructeur
	**/
	public DroitUtilisateurWindow () {
        droitUtilisateurs = DroitUtilisateur.list()
        droitUtilisateurSelected = null
        droitUtilisateur = new DroitUtilisateur()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        droitUtilisateur = droitUtilisateurSelected	
		afficherValeurAssociation()
        //droitUtilisateur.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de droitUtilisateur
	**/
    def add() {
		actualiserValeurAssociation()
        droitUtilisateur.validate()        
        if(!droitUtilisateur.hasErrors()) {			
			try {
				droitUtilisateur.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				droitUtilisateur = new DroitUtilisateur()
				rafraichirField()
				rafraichirList()
				activerBoutons(false)
			}
        } else {
            Messagebox.show("Les donnees saisies sont erronees", "Erreur", Messagebox.OK, Messagebox.ERROR)
        }
		//reinitialiserAssociation()
    }
	/**
	* Pour annuler la modification ou la supression et pour basculer en mode ajout d'un nouveau �l�ment
	**/
    def cancel() {        
        droitUtilisateur = new DroitUtilisateur()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de droitUtilisateur
	**/
    def update() {
		actualiserValeurAssociation()
        droitUtilisateur.validate()
        if(!droitUtilisateur.hasErrors()) {
            try {
                //droitUtilisateur.save()
                droitUtilisateur.merge(flush:true)
            }
            catch(org.springframework.dao.OptimisticLockingFailureException e) {
			logger.error "Error: ${e.message}", e
                Messagebox.show("Probleme acces concurrent", "Erreur", Messagebox.OK, Messagebox.ERROR)
            }
			catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			}
			finally {
                activerBoutons(false)
                droitUtilisateur = new DroitUtilisateur()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de droitUtilisateur
	**/
    def delete() {
        droitUtilisateur.delete(flush:true)
        activerBoutons(false)
        droitUtilisateur = new DroitUtilisateur()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau droitUtilisateur
	**/
    def newRecord(){
        this.getFellow("westPanel").open = visible
    }
	/**
	* Activer ou d�sactiver les boutons d'ajout, suppression, modfication
	**/
    def activerBoutons(visible) {
        this.getFellow("btnUpdate").visible = visible
        this.getFellow("btnDelete").visible = visible
        this.getFellow("btnCancel").visible = visible
        this.getFellow("btnSave").visible = !visible
        this.getFellow("btnNew").visible = !visible
        this.getFellow("westPanel").open = visible        
    }
	/**
	* R�initialiser les champs du formulaire
	**/
    def rafraichirField() {
        this.getFellows().each { co ->
            if(co.getId() != null && co.getId().startsWith("field")) {
                def binder = new AnnotateDataBinder(co)
                binder.loadAll()
            }
        }
    }
	/**
	* Rafrichier la liste des droitUtilisateur
	**/
    def rafraichirList() {
        droitUtilisateurs = DroitUtilisateur.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstDroitUtilisateur"))
        binder.loadAll()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstDroitUtilisateur").clearSelection()
        droitUtilisateurSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
	}
}

