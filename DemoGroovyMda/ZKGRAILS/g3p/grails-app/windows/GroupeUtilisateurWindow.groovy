import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * GroupeUtilisateur Window Object
 **/
class GroupeUtilisateurWindow extends Window{
	/**
	* Logger de la class GroupeUtilisateurWindow
	**/
	private Log logger = LogFactory.getLog(GroupeUtilisateurWindow.class)
	
	/**
	* liste de groupeUtilisateur
	**/
	def groupeUtilisateurs
	/**
	* groupeUtilisateur selectionn�
	**/
	def groupeUtilisateurSelected
	/**
	* un nouveau element de groupeUtilisateur  
	**/
	def groupeUtilisateur
	
	
	/**
	* liste de droitUtilisateurs
	**/
	def droitUtilisateurs
	/**
	* droitUtilisateurs selectionn�
	**/
	def droitUtilisateursSelected
	
	/**
	* liste de utilisateurs
	**/
	def utilisateurs	
	/**
	* utilisateurs  selectionn�
	**/
	def utilisateursSelected
	
	/**
	* Constructeur
	**/
	public GroupeUtilisateurWindow () {
        groupeUtilisateurs = GroupeUtilisateur.list()
        groupeUtilisateurSelected = null
        groupeUtilisateur = new GroupeUtilisateur()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        groupeUtilisateur = groupeUtilisateurSelected	
		afficherValeurAssociation()
        //groupeUtilisateur.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de groupeUtilisateur
	**/
    def add() {
		actualiserValeurAssociation()
        groupeUtilisateur.validate()        
        if(!groupeUtilisateur.hasErrors()) {			
			try {
				groupeUtilisateur.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				groupeUtilisateur = new GroupeUtilisateur()
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
        groupeUtilisateur = new GroupeUtilisateur()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de groupeUtilisateur
	**/
    def update() {
		actualiserValeurAssociation()
        groupeUtilisateur.validate()
        if(!groupeUtilisateur.hasErrors()) {
            try {
                //groupeUtilisateur.save()
                groupeUtilisateur.merge(flush:true)
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
                groupeUtilisateur = new GroupeUtilisateur()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de groupeUtilisateur
	**/
    def delete() {
        groupeUtilisateur.delete(flush:true)
        activerBoutons(false)
        groupeUtilisateur = new GroupeUtilisateur()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau groupeUtilisateur
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
	* Rafrichier la liste des groupeUtilisateur
	**/
    def rafraichirList() {
        groupeUtilisateurs = GroupeUtilisateur.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstGroupeUtilisateur"))
        binder.loadAll()		
		
		droitUtilisateurs = DroitUtilisateur.list()
		
		utilisateurs = Utilisateur.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstGroupeUtilisateur").clearSelection()
        groupeUtilisateurSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		droitUtilisateurs = DroitUtilisateur.list()
		droitUtilisateursSelected = null// = new ArrayList()
		
		utilisateurs = Utilisateur.list()
		utilisateursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			droitUtilisateurs = DroitUtilisateur.list()
		}
		this.getFellow("lstdroitUtilisateurs").clearSelection()
		droitUtilisateursSelected = null// = new ArrayList()
		
		if(del) {
			utilisateurs = Utilisateur.list()
		}
		this.getFellow("lstutilisateurs").clearSelection()
		utilisateursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
			
		groupeUtilisateur.droitUtilisateurs = droitUtilisateursSelected
		this.getFellow("lstdroitUtilisateurs").clearSelection()
		droitUtilisateursSelected = null// = new ArrayList()
		
		groupeUtilisateur.utilisateurs = utilisateursSelected
		this.getFellow("lstutilisateurs").clearSelection()
		utilisateursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderdroitUtilisateurs = new AnnotateDataBinder(this.getFellow("lstdroitUtilisateurs"))
		droitUtilisateursSelected = groupeUtilisateurSelected.droitUtilisateurs
        binderdroitUtilisateurs.loadAll()		
		
		def binderutilisateurs = new AnnotateDataBinder(this.getFellow("lstutilisateurs"))
		utilisateursSelected = groupeUtilisateurSelected.utilisateurs
        binderutilisateurs.loadAll()
		
	}
}

