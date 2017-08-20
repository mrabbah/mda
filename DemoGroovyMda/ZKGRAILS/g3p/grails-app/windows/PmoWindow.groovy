import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Pmo Window Object
 **/
class PmoWindow extends Window{
	/**
	* Logger de la class PmoWindow
	**/
	private Log logger = LogFactory.getLog(PmoWindow.class)
	
	/**
	* liste de pmo
	**/
	def pmos
	/**
	* pmo selectionné
	**/
	def pmoSelected
	/**
	* un nouveau element de pmo  
	**/
	def pmo
	
	
	/**
	* liste de utilisateur
	**/
	def utilisateurs	
	/**
	* utilisateur  selectionné
	**/
	def utilisateurSelected	
	
	/**
	* Constructeur
	**/
	public PmoWindow () {
        pmos = Pmo.list()
        pmoSelected = null
        pmo = new Pmo()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        pmo = pmoSelected	
		afficherValeurAssociation()
        //pmo.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de pmo
	**/
    def add() {
		actualiserValeurAssociation()
        pmo.validate()        
        if(!pmo.hasErrors()) {			
			try {
				pmo.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				pmo = new Pmo()
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
        pmo = new Pmo()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de pmo
	**/
    def update() {
		actualiserValeurAssociation()
        pmo.validate()
        if(!pmo.hasErrors()) {
            try {
                //pmo.save()
                pmo.merge(flush:true)
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
                pmo = new Pmo()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de pmo
	**/
    def delete() {
        pmo.delete(flush:true)
        activerBoutons(false)
        pmo = new Pmo()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau pmo
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
	* Rafrichier la liste des pmo
	**/
    def rafraichirList() {
        pmos = Pmo.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstPmo"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstPmo").clearSelection()
        pmoSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		utilisateurs = Utilisateur.list()		
		if(utilisateurs.size() > 0)
			utilisateurSelected = utilisateurs.get(0)
		else
			utilisateurSelected = null
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			utilisateurs = Utilisateur.list()
		}		
		if(utilisateurs.size() > 0)
			utilisateurSelected = utilisateurs.get(0)
		else
			utilisateurSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
				
		pmo.utilisateur = utilisateurSelected
		if(utilisateurs.size() > 0) {
			def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
			utilisateurSelected = utilisateurs.get(0)
			binderutilisateur.loadAll()
		}
		else
			utilisateurSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
								
		def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
		utilisateurSelected = utilisateurs.find{ it.id == Pmo.findById(pmo.id).utilisateur.id }
        binderutilisateur.loadAll()
		
	}
}

