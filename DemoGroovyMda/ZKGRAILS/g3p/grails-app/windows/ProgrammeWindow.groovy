import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Programme Window Object
 **/
class ProgrammeWindow extends Window{
	/**
	* Logger de la class ProgrammeWindow
	**/
	private Log logger = LogFactory.getLog(ProgrammeWindow.class)
	
	/**
	* liste de programme
	**/
	def programmes
	/**
	* programme selectionné
	**/
	def programmeSelected
	/**
	* un nouveau element de programme  
	**/
	def programme
	
	
	/**
	* liste de portefeuille
	**/	
	def portefeuilles	
	/**
	* portefeuille  selectionné
	**/
	def portefeuilleSelected
	
	/**
	* liste de projets
	**/
	def projets	
	/**
	* projets  selectionné
	**/
	def projetsSelected
	
	/**
	* Constructeur
	**/
	public ProgrammeWindow () {
        programmes = Programme.list()
        programmeSelected = null
        programme = new Programme()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        programme = programmeSelected	
		afficherValeurAssociation()
        //programme.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de programme
	**/
    def add() {
		actualiserValeurAssociation()
        programme.validate()        
        if(!programme.hasErrors()) {			
			try {
				programme.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				programme = new Programme()
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
        programme = new Programme()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de programme
	**/
    def update() {
		actualiserValeurAssociation()
        programme.validate()
        if(!programme.hasErrors()) {
            try {
                //programme.save()
                programme.merge(flush:true)
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
                programme = new Programme()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de programme
	**/
    def delete() {
        programme.delete(flush:true)
        activerBoutons(false)
        programme = new Programme()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau programme
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
	* Rafrichier la liste des programme
	**/
    def rafraichirList() {
        programmes = Programme.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstProgramme"))
        binder.loadAll()		
				
		
		projets = Projet.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstProgramme").clearSelection()
        programmeSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		portefeuilles = Portefeuille.list()		
		if(portefeuilles.size() > 0)
			portefeuilleSelected = portefeuilles.get(0)
		else
			portefeuilleSelected = null
		
		projets = Projet.list()
		projetsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			portefeuilles = Portefeuille.list()
		}	
		if(portefeuilles.size() > 0)
			portefeuilleSelected = portefeuilles.get(0)
		else
			portefeuilleSelected = null
		
		if(del) {
			projets = Projet.list()
		}
		this.getFellow("lstprojets").clearSelection()
		projetsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
				
		programme.portefeuille = portefeuilleSelected
		if(portefeuilles.size() > 0) {
			def binderportefeuille = new AnnotateDataBinder(this.getFellow("coportefeuilles"))
			portefeuilleSelected = portefeuilles.get(0)
			binderportefeuille.loadAll()
		}
		else
			portefeuilleSelected = null
		
		programme.projets = projetsSelected
		this.getFellow("lstprojets").clearSelection()
		projetsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
				
		def binderportefeuille = new AnnotateDataBinder(this.getFellow("coportefeuilles"))
		portefeuilleSelected = portefeuilles.find{ it.id == Programme.findById(programme.id).portefeuille.id }
        binderportefeuille.loadAll()
		
		def binderprojets = new AnnotateDataBinder(this.getFellow("lstprojets"))
		projetsSelected = programmeSelected.projets
        binderprojets.loadAll()
		
	}
}

