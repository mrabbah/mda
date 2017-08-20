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
	* droitUtilisateur selectionné
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
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        droitUtilisateur = droitUtilisateurSelected	
		afficherValeurAssociation()
        //droitUtilisateur.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de droitUtilisateur
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
	* Pour annuler la modification ou la supression et pour basculer en mode ajout d'un nouveau élément
	**/
    def cancel() {        
        droitUtilisateur = new DroitUtilisateur()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de droitUtilisateur
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
	* Fonction qui se charge de supprimer un élément selectinné de droitUtilisateur
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
	* Activer ou désactiver les boutons d'ajout, suppression, modfication
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
	* Réinitialiser les champs du formulaire
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
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstDroitUtilisateur").clearSelection()
        droitUtilisateurSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
	}
}

