import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Livrable Window Object
 **/
class LivrableWindow extends Window{
	/**
	* Logger de la class LivrableWindow
	**/
	private Log logger = LogFactory.getLog(LivrableWindow.class)
	
	/**
	* liste de livrable
	**/
	def livrables
	/**
	* livrable selectionné
	**/
	def livrableSelected
	/**
	* un nouveau element de livrable  
	**/
	def livrable
	
	
	/**
	* liste de projet
	**/	
	def projets	
	/**
	* projet  selectionné
	**/
	def projetSelected
	
	/**
	* Constructeur
	**/
	public LivrableWindow () {
        livrables = Livrable.list()
        livrableSelected = null
        livrable = new Livrable()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        livrable = livrableSelected	
		afficherValeurAssociation()
        //livrable.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de livrable
	**/
    def add() {
		actualiserValeurAssociation()
        livrable.validate()        
        if(!livrable.hasErrors()) {			
			try {
				livrable.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				livrable = new Livrable()
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
        livrable = new Livrable()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de livrable
	**/
    def update() {
		actualiserValeurAssociation()
        livrable.validate()
        if(!livrable.hasErrors()) {
            try {
                //livrable.save()
                livrable.merge(flush:true)
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
                livrable = new Livrable()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de livrable
	**/
    def delete() {
        livrable.delete(flush:true)
        activerBoutons(false)
        livrable = new Livrable()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau livrable
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
	* Rafrichier la liste des livrable
	**/
    def rafraichirList() {
        livrables = Livrable.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstLivrable"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstLivrable").clearSelection()
        livrableSelected = null
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
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
				
		livrable.projet = projetSelected
		if(projets.size() > 0) {
			def binderprojet = new AnnotateDataBinder(this.getFellow("coprojets"))
			projetSelected = projets.get(0)
			binderprojet.loadAll()
		}
		else
			projetSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
				
		def binderprojet = new AnnotateDataBinder(this.getFellow("coprojets"))
		projetSelected = projets.find{ it.id == Livrable.findById(livrable.id).projet.id }
        binderprojet.loadAll()
		
	}
}

