import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Ressource Window Object
 **/
class RessourceWindow extends Window{
	/**
	* Logger de la class RessourceWindow
	**/
	private Log logger = LogFactory.getLog(RessourceWindow.class)
	
	/**
	* liste de ressource
	**/
	def ressources
	/**
	* ressource selectionn�
	**/
	def ressourceSelected
	/**
	* un nouveau element de ressource  
	**/
	def ressource
	
	
	/**
	* liste de utilisateur
	**/
	def utilisateurs	
	/**
	* utilisateur  selectionn�
	**/
	def utilisateurSelected	
	
	/**
	* liste de calendrier
	**/	
	def calendriers	
	/**
	* calendrier  selectionn�
	**/
	def calendrierSelected
	
	/**
	* liste de groupeRessource
	**/	
	def groupeRessources	
	/**
	* groupeRessource  selectionn�
	**/
	def groupeRessourceSelected
	
	/**
	* Constructeur
	**/
	public RessourceWindow () {
        ressources = Ressource.list()
        ressourceSelected = null
        ressource = new Ressource()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        ressource = ressourceSelected	
		afficherValeurAssociation()
        //ressource.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de ressource
	**/
    def add() {
		actualiserValeurAssociation()
        ressource.validate()        
        if(!ressource.hasErrors()) {			
			try {
				ressource.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				ressource = new Ressource()
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
        ressource = new Ressource()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de ressource
	**/
    def update() {
		actualiserValeurAssociation()
        ressource.validate()
        if(!ressource.hasErrors()) {
            try {
                //ressource.save()
                ressource.merge(flush:true)
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
                ressource = new Ressource()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de ressource
	**/
    def delete() {
        ressource.delete(flush:true)
        activerBoutons(false)
        ressource = new Ressource()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau ressource
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
	* Rafrichier la liste des ressource
	**/
    def rafraichirList() {
        ressources = Ressource.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstRessource"))
        binder.loadAll()		
				
				
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstRessource").clearSelection()
        ressourceSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		utilisateurs = Utilisateur.list()		
		if(utilisateurs.size() > 0)
			utilisateurSelected = utilisateurs.get(0)
		else
			utilisateurSelected = null
		
		calendriers = Calendrier.list()		
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
		groupeRessources = GroupeRessource.list()		
		if(groupeRessources.size() > 0)
			groupeRessourceSelected = groupeRessources.get(0)
		else
			groupeRessourceSelected = null
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			utilisateurs = Utilisateur.list()
		}		
		if(utilisateurs.size() > 0)
			utilisateurSelected = utilisateurs.get(0)
		else
			utilisateurSelected = null
			
		if(del) {
			calendriers = Calendrier.list()
		}	
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
			
		if(del) {
			groupeRessources = GroupeRessource.list()
		}	
		if(groupeRessources.size() > 0)
			groupeRessourceSelected = groupeRessources.get(0)
		else
			groupeRessourceSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
				
		ressource.utilisateur = utilisateurSelected
		if(utilisateurs.size() > 0) {
			def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
			utilisateurSelected = utilisateurs.get(0)
			binderutilisateur.loadAll()
		}
		else
			utilisateurSelected = null
				
		ressource.calendrier = calendrierSelected
		if(calendriers.size() > 0) {
			def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
			calendrierSelected = calendriers.get(0)
			bindercalendrier.loadAll()
		}
		else
			calendrierSelected = null
				
		ressource.groupeRessource = groupeRessourceSelected
		if(groupeRessources.size() > 0) {
			def bindergroupeRessource = new AnnotateDataBinder(this.getFellow("cogroupeRessources"))
			groupeRessourceSelected = groupeRessources.get(0)
			bindergroupeRessource.loadAll()
		}
		else
			groupeRessourceSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
								
		def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
		utilisateurSelected = utilisateurs.find{ it.id == Ressource.findById(ressource.id).utilisateur.id }
        binderutilisateur.loadAll()
				
		def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
		calendrierSelected = calendriers.find{ it.id == Ressource.findById(ressource.id).calendrier.id }
        bindercalendrier.loadAll()
				
		def bindergroupeRessource = new AnnotateDataBinder(this.getFellow("cogroupeRessources"))
		groupeRessourceSelected = groupeRessources.find{ it.id == Ressource.findById(ressource.id).groupeRessource.id }
        bindergroupeRessource.loadAll()
		
	}
}

