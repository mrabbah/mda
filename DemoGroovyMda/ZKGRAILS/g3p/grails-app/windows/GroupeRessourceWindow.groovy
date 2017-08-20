import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * GroupeRessource Window Object
 **/
class GroupeRessourceWindow extends Window{
	/**
	* Logger de la class GroupeRessourceWindow
	**/
	private Log logger = LogFactory.getLog(GroupeRessourceWindow.class)
	
	/**
	* liste de groupeRessource
	**/
	def groupeRessources
	/**
	* groupeRessource selectionné
	**/
	def groupeRessourceSelected
	/**
	* un nouveau element de groupeRessource  
	**/
	def groupeRessource
	
	
	/**
	* liste de ressources
	**/
	def ressources	
	/**
	* ressources  selectionné
	**/
	def ressourcesSelected
	
	/**
	* liste de calendrier
	**/	
	def calendriers	
	/**
	* calendrier  selectionné
	**/
	def calendrierSelected
	
	/**
	* Constructeur
	**/
	public GroupeRessourceWindow () {
        groupeRessources = GroupeRessource.list()
        groupeRessourceSelected = null
        groupeRessource = new GroupeRessource()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        groupeRessource = groupeRessourceSelected	
		afficherValeurAssociation()
        //groupeRessource.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de groupeRessource
	**/
    def add() {
		actualiserValeurAssociation()
        groupeRessource.validate()        
        if(!groupeRessource.hasErrors()) {			
			try {
				groupeRessource.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				groupeRessource = new GroupeRessource()
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
        groupeRessource = new GroupeRessource()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de groupeRessource
	**/
    def update() {
		actualiserValeurAssociation()
        groupeRessource.validate()
        if(!groupeRessource.hasErrors()) {
            try {
                //groupeRessource.save()
                groupeRessource.merge(flush:true)
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
                groupeRessource = new GroupeRessource()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de groupeRessource
	**/
    def delete() {
        groupeRessource.delete(flush:true)
        activerBoutons(false)
        groupeRessource = new GroupeRessource()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau groupeRessource
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
	* Rafrichier la liste des groupeRessource
	**/
    def rafraichirList() {
        groupeRessources = GroupeRessource.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstGroupeRessource"))
        binder.loadAll()		
		
		ressources = Ressource.list()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstGroupeRessource").clearSelection()
        groupeRessourceSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		ressources = Ressource.list()
		ressourcesSelected = null// = new ArrayList()
		
		calendriers = Calendrier.list()		
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			ressources = Ressource.list()
		}
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
			
		if(del) {
			calendriers = Calendrier.list()
		}	
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
		
		groupeRessource.ressources = ressourcesSelected
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
				
		groupeRessource.calendrier = calendrierSelected
		if(calendriers.size() > 0) {
			def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
			calendrierSelected = calendriers.get(0)
			bindercalendrier.loadAll()
		}
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderressources = new AnnotateDataBinder(this.getFellow("lstressources"))
		ressourcesSelected = groupeRessourceSelected.ressources
        binderressources.loadAll()
				
		def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
		calendrierSelected = calendriers.find{ it.id == GroupeRessource.findById(groupeRessource.id).calendrier.id }
        bindercalendrier.loadAll()
		
	}
}

