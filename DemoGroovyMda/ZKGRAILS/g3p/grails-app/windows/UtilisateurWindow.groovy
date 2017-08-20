import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Utilisateur Window Object
 **/
class UtilisateurWindow extends Window{
	/**
	* Logger de la class UtilisateurWindow
	**/
	private Log logger = LogFactory.getLog(UtilisateurWindow.class)
	
	/**
	* liste de utilisateur
	**/
	def utilisateurs
	/**
	* utilisateur selectionn�
	**/
	def utilisateurSelected
	/**
	* un nouveau element de utilisateur  
	**/
	def utilisateur
	
	
	/**
	* liste de groupeUtilisateur
	**/	
	def groupeUtilisateurs	
	/**
	* groupeUtilisateur  selectionn�
	**/
	def groupeUtilisateurSelected
	
	/**
	* Constructeur
	**/
	public UtilisateurWindow () {
        utilisateurs = Utilisateur.list()
        utilisateurSelected = null
        utilisateur = new Utilisateur()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        utilisateur = utilisateurSelected	
		afficherValeurAssociation()
        //utilisateur.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de utilisateur
	**/
    def add() {
		actualiserValeurAssociation()
        utilisateur.validate()        
        if(!utilisateur.hasErrors()) {			
			try {
				utilisateur.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				utilisateur = new Utilisateur()
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
        utilisateur = new Utilisateur()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de utilisateur
	**/
    def update() {
		actualiserValeurAssociation()
        utilisateur.validate()
        if(!utilisateur.hasErrors()) {
            try {
                //utilisateur.save()
                utilisateur.merge(flush:true)
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
                utilisateur = new Utilisateur()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de utilisateur
	**/
    def delete() {
        utilisateur.delete(flush:true)
        activerBoutons(false)
        utilisateur = new Utilisateur()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau utilisateur
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
	* Rafrichier la liste des utilisateur
	**/
    def rafraichirList() {
        utilisateurs = Utilisateur.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstUtilisateur"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstUtilisateur").clearSelection()
        utilisateurSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		groupeUtilisateurs = GroupeUtilisateur.list()		
		if(groupeUtilisateurs.size() > 0)
			groupeUtilisateurSelected = groupeUtilisateurs.get(0)
		else
			groupeUtilisateurSelected = null
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			groupeUtilisateurs = GroupeUtilisateur.list()
		}	
		if(groupeUtilisateurs.size() > 0)
			groupeUtilisateurSelected = groupeUtilisateurs.get(0)
		else
			groupeUtilisateurSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
				
		utilisateur.groupeUtilisateur = groupeUtilisateurSelected
		if(groupeUtilisateurs.size() > 0) {
			def bindergroupeUtilisateur = new AnnotateDataBinder(this.getFellow("cogroupeUtilisateurs"))
			groupeUtilisateurSelected = groupeUtilisateurs.get(0)
			bindergroupeUtilisateur.loadAll()
		}
		else
			groupeUtilisateurSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
				
		def bindergroupeUtilisateur = new AnnotateDataBinder(this.getFellow("cogroupeUtilisateurs"))
		groupeUtilisateurSelected = groupeUtilisateurs.find{ it.id == Utilisateur.findById(utilisateur.id).groupeUtilisateur.id }
        bindergroupeUtilisateur.loadAll()
		
	}
}

