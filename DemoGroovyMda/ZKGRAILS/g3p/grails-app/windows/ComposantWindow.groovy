import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Composant Window Object
 **/
class ComposantWindow extends Window{
	/**
	* Logger de la class ComposantWindow
	**/
	private Log logger = LogFactory.getLog(ComposantWindow.class)
	
	/**
	* liste de composant
	**/
	def composants
	/**
	* composant selectionné
	**/
	def composantSelected
	/**
	* un nouveau element de composant  
	**/
	def composant
	
	
	/**
	* liste de projet
	**/	
	def projets	
	/**
	* projet  selectionné
	**/
	def projetSelected
	
	/**
	* liste de composant
	**/	
	def composants	
	/**
	* composant  selectionné
	**/
	def composantSelected
	
	/**
	* liste de composants
	**/
	def composants	
	/**
	* composants  selectionné
	**/
	def composantsSelected
	
	/**
	* Constructeur
	**/
	public ComposantWindow () {
        composants = Composant.list()
        composantSelected = null
        composant = new Composant()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        composant = composantSelected	
		afficherValeurAssociation()
        //composant.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de composant
	**/
    def add() {
		actualiserValeurAssociation()
        composant.validate()        
        if(!composant.hasErrors()) {			
			try {
				composant.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				composant = new Composant()
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
        composant = new Composant()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de composant
	**/
    def update() {
		actualiserValeurAssociation()
        composant.validate()
        if(!composant.hasErrors()) {
            try {
                //composant.save()
                composant.merge(flush:true)
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
                composant = new Composant()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de composant
	**/
    def delete() {
        composant.delete(flush:true)
        activerBoutons(false)
        composant = new Composant()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau composant
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
	* Rafrichier la liste des composant
	**/
    def rafraichirList() {
        composants = Composant.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstComposant"))
        binder.loadAll()		
				
				
		
		composants = Composant.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstComposant").clearSelection()
        composantSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		projets = Projet.list()		
		if(projets.size() > 0)
			projetSelected = projets.get(0)
		else
			projetSelected = null
		
		composants = Composant.list()		
		if(composants.size() > 0)
			composantSelected = composants.get(0)
		else
			composantSelected = null
		
		composants = Composant.list()
		composantsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			projets = Projet.list()
		}	
		if(projets.size() > 0)
			projetSelected = projets.get(0)
		else
			projetSelected = null
			
		if(del) {
			composants = Composant.list()
		}	
		if(composants.size() > 0)
			composantSelected = composants.get(0)
		else
			composantSelected = null
		
		if(del) {
			composants = Composant.list()
		}
		this.getFellow("lstcomposants").clearSelection()
		composantsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
				
		composant.projet = projetSelected
		if(projets.size() > 0) {
			def binderprojet = new AnnotateDataBinder(this.getFellow("coprojets"))
			projetSelected = projets.get(0)
			binderprojet.loadAll()
		}
		else
			projetSelected = null
				
		composant.composant = composantSelected
		if(composants.size() > 0) {
			def bindercomposant = new AnnotateDataBinder(this.getFellow("cocomposants"))
			composantSelected = composants.get(0)
			bindercomposant.loadAll()
		}
		else
			composantSelected = null
		
		composant.composants = composantsSelected
		this.getFellow("lstcomposants").clearSelection()
		composantsSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
				
		def binderprojet = new AnnotateDataBinder(this.getFellow("coprojets"))
		projetSelected = projets.find{ it.id == Composant.findById(composant.id).projet.id }
        binderprojet.loadAll()
				
		def bindercomposant = new AnnotateDataBinder(this.getFellow("cocomposants"))
		composantSelected = composants.find{ it.id == Composant.findById(composant.id).composant.id }
        bindercomposant.loadAll()
		
		def bindercomposants = new AnnotateDataBinder(this.getFellow("lstcomposants"))
		composantsSelected = composantSelected.composants
        bindercomposants.loadAll()
		
	}
}

