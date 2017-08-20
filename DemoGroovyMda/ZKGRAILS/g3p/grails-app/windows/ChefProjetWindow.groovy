import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * ChefProjet Window Object
 **/
class ChefProjetWindow extends Window{
	/**
	* Logger de la class ChefProjetWindow
	**/
	private Log logger = LogFactory.getLog(ChefProjetWindow.class)
	
	/**
	* liste de chefProjet
	**/
	def chefProjets
	/**
	* chefProjet selectionné
	**/
	def chefProjetSelected
	/**
	* un nouveau element de chefProjet  
	**/
	def chefProjet
	
	
	/**
	* liste de utilisateur
	**/
	def utilisateurs	
	/**
	* utilisateur  selectionné
	**/
	def utilisateurSelected	
	
	/**
	* liste de taches
	**/
	def taches	
	/**
	* taches  selectionné
	**/
	def tachesSelected
	
	/**
	* Constructeur
	**/
	public ChefProjetWindow () {
        chefProjets = ChefProjet.list()
        chefProjetSelected = null
        chefProjet = new ChefProjet()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        chefProjet = chefProjetSelected	
		afficherValeurAssociation()
        //chefProjet.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de chefProjet
	**/
    def add() {
		actualiserValeurAssociation()
        chefProjet.validate()        
        if(!chefProjet.hasErrors()) {			
			try {
				chefProjet.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				chefProjet = new ChefProjet()
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
        chefProjet = new ChefProjet()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de chefProjet
	**/
    def update() {
		actualiserValeurAssociation()
        chefProjet.validate()
        if(!chefProjet.hasErrors()) {
            try {
                //chefProjet.save()
                chefProjet.merge(flush:true)
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
                chefProjet = new ChefProjet()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de chefProjet
	**/
    def delete() {
        chefProjet.delete(flush:true)
        activerBoutons(false)
        chefProjet = new ChefProjet()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau chefProjet
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
	* Rafrichier la liste des chefProjet
	**/
    def rafraichirList() {
        chefProjets = ChefProjet.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstChefProjet"))
        binder.loadAll()		
				
		
		taches = Tache.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstChefProjet").clearSelection()
        chefProjetSelected = null
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
		
		taches = Tache.list()
		tachesSelected = null// = new ArrayList()
		
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
				
		chefProjet.utilisateur = utilisateurSelected
		if(utilisateurs.size() > 0) {
			def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
			utilisateurSelected = utilisateurs.get(0)
			binderutilisateur.loadAll()
		}
		else
			utilisateurSelected = null
		
		chefProjet.taches = tachesSelected
		this.getFellow("lsttaches").clearSelection()
		tachesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
								
		def binderutilisateur = new AnnotateDataBinder(this.getFellow("coutilisateurs"))
		utilisateurSelected = utilisateurs.find{ it.id == ChefProjet.findById(chefProjet.id).utilisateur.id }
        binderutilisateur.loadAll()
		
		def bindertaches = new AnnotateDataBinder(this.getFellow("lsttaches"))
		tachesSelected = chefProjetSelected.taches
        bindertaches.loadAll()
		
	}
}

