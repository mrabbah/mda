import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Tache Window Object
 **/
class TacheWindow extends Window{
	/**
	* Logger de la class TacheWindow
	**/
	private Log logger = LogFactory.getLog(TacheWindow.class)
	
	/**
	* liste de tache
	**/
	def taches
	/**
	* tache selectionné
	**/
	def tacheSelected
	/**
	* un nouveau element de tache  
	**/
	def tache
	
	
	/**
	* liste de ressources
	**/
	def ressources
	/**
	* ressources selectionné
	**/
	def ressourcesSelected
	
	/**
	* liste de chefProjet
	**/	
	def chefProjets	
	/**
	* chefProjet  selectionné
	**/
	def chefProjetSelected
	
	/**
	* liste de phase
	**/	
	def phases	
	/**
	* phase  selectionné
	**/
	def phaseSelected
	
	/**
	* liste de tache
	**/	
	def taches	
	/**
	* tache  selectionné
	**/
	def tacheSelected
	
	/**
	* liste de taches
	**/
	def taches	
	/**
	* taches  selectionné
	**/
	def tachesSelected
	
	/**
	* liste de taches
	**/
	def taches
	/**
	* taches selectionné
	**/
	def tachesSelected
	
	/**
	* Constructeur
	**/
	public TacheWindow () {
        taches = Tache.list()
        tacheSelected = null
        tache = new Tache()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        tache = tacheSelected	
		afficherValeurAssociation()
        //tache.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de tache
	**/
    def add() {
		actualiserValeurAssociation()
        tache.validate()        
        if(!tache.hasErrors()) {			
			try {
				tache.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				tache = new Tache()
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
        tache = new Tache()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de tache
	**/
    def update() {
		actualiserValeurAssociation()
        tache.validate()
        if(!tache.hasErrors()) {
            try {
                //tache.save()
                tache.merge(flush:true)
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
                tache = new Tache()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de tache
	**/
    def delete() {
        tache.delete(flush:true)
        activerBoutons(false)
        tache = new Tache()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau tache
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
	* Rafrichier la liste des tache
	**/
    def rafraichirList() {
        taches = Tache.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstTache"))
        binder.loadAll()		
		
		ressources = Ressource.list()
				
				
				
		
		taches = Tache.list()		
		
		taches = Tache.list()
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstTache").clearSelection()
        tacheSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		ressources = Ressource.list()
		ressourcesSelected = null// = new ArrayList()
		
		chefProjets = ChefProjet.list()		
		if(chefProjets.size() > 0)
			chefProjetSelected = chefProjets.get(0)
		else
			chefProjetSelected = null
		
		phases = Phase.list()		
		if(phases.size() > 0)
			phaseSelected = phases.get(0)
		else
			phaseSelected = null
		
		taches = Tache.list()		
		if(taches.size() > 0)
			tacheSelected = taches.get(0)
		else
			tacheSelected = null
		
		taches = Tache.list()
		tachesSelected = null// = new ArrayList()
		
		taches = Tache.list()
		tachesSelected = null// = new ArrayList()
		
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
			chefProjets = ChefProjet.list()
		}	
		if(chefProjets.size() > 0)
			chefProjetSelected = chefProjets.get(0)
		else
			chefProjetSelected = null
			
		if(del) {
			phases = Phase.list()
		}	
		if(phases.size() > 0)
			phaseSelected = phases.get(0)
		else
			phaseSelected = null
			
		if(del) {
			taches = Tache.list()
		}	
		if(taches.size() > 0)
			tacheSelected = taches.get(0)
		else
			tacheSelected = null
		
		if(del) {
			taches = Tache.list()
		}
		this.getFellow("lsttaches").clearSelection()
		tachesSelected = null// = new ArrayList()
			
		if(del) {
			taches = Tache.list()
		}
		this.getFellow("lsttaches").clearSelection()
		tachesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
			
		tache.ressources = ressourcesSelected
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
				
		tache.chefProjet = chefProjetSelected
		if(chefProjets.size() > 0) {
			def binderchefProjet = new AnnotateDataBinder(this.getFellow("cochefProjets"))
			chefProjetSelected = chefProjets.get(0)
			binderchefProjet.loadAll()
		}
		else
			chefProjetSelected = null
				
		tache.phase = phaseSelected
		if(phases.size() > 0) {
			def binderphase = new AnnotateDataBinder(this.getFellow("cophases"))
			phaseSelected = phases.get(0)
			binderphase.loadAll()
		}
		else
			phaseSelected = null
				
		tache.tache = tacheSelected
		if(taches.size() > 0) {
			def bindertache = new AnnotateDataBinder(this.getFellow("cotaches"))
			tacheSelected = taches.get(0)
			bindertache.loadAll()
		}
		else
			tacheSelected = null
		
		tache.taches = tachesSelected
		this.getFellow("lsttaches").clearSelection()
		tachesSelected = null// = new ArrayList()
			
		tache.taches = tachesSelected
		this.getFellow("lsttaches").clearSelection()
		tachesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderressources = new AnnotateDataBinder(this.getFellow("lstressources"))
		ressourcesSelected = tacheSelected.ressources
        binderressources.loadAll()		
				
		def binderchefProjet = new AnnotateDataBinder(this.getFellow("cochefProjets"))
		chefProjetSelected = chefProjets.find{ it.id == Tache.findById(tache.id).chefProjet.id }
        binderchefProjet.loadAll()
				
		def binderphase = new AnnotateDataBinder(this.getFellow("cophases"))
		phaseSelected = phases.find{ it.id == Tache.findById(tache.id).phase.id }
        binderphase.loadAll()
				
		def bindertache = new AnnotateDataBinder(this.getFellow("cotaches"))
		tacheSelected = taches.find{ it.id == Tache.findById(tache.id).tache.id }
        bindertache.loadAll()
		
		def bindertaches = new AnnotateDataBinder(this.getFellow("lsttaches"))
		tachesSelected = tacheSelected.taches
        bindertaches.loadAll()
		
		def bindertaches = new AnnotateDataBinder(this.getFellow("lsttaches"))
		tachesSelected = tacheSelected.taches
        bindertaches.loadAll()		
		
	}
}

