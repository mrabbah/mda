import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Portefeuille Window Object
 **/
class PortefeuilleWindow extends Window{
	/**
	* Logger de la class PortefeuilleWindow
	**/
	private Log logger = LogFactory.getLog(PortefeuilleWindow.class)
	
	/**
	* liste de portefeuille
	**/
	def portefeuilles
	/**
	* portefeuille selectionné
	**/
	def portefeuilleSelected
	/**
	* un nouveau element de portefeuille  
	**/
	def portefeuille
	
	
	/**
	* liste de projets
	**/
	def projets	
	/**
	* projets  selectionné
	**/
	def projetsSelected
	
	/**
	* liste de programmes
	**/
	def programmes	
	/**
	* programmes  selectionné
	**/
	def programmesSelected
	
	/**
	* Constructeur
	**/
	public PortefeuilleWindow () {
        portefeuilles = Portefeuille.list()
        portefeuilleSelected = null
        portefeuille = new Portefeuille()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        portefeuille = portefeuilleSelected	
		afficherValeurAssociation()
        //portefeuille.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de portefeuille
	**/
    def add() {
		actualiserValeurAssociation()
        portefeuille.validate()        
        if(!portefeuille.hasErrors()) {			
			try {
				portefeuille.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				portefeuille = new Portefeuille()
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
        portefeuille = new Portefeuille()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de portefeuille
	**/
    def update() {
		actualiserValeurAssociation()
        portefeuille.validate()
        if(!portefeuille.hasErrors()) {
            try {
                //portefeuille.save()
                portefeuille.merge(flush:true)
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
                portefeuille = new Portefeuille()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de portefeuille
	**/
    def delete() {
        portefeuille.delete(flush:true)
        activerBoutons(false)
        portefeuille = new Portefeuille()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau portefeuille
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
	* Rafrichier la liste des portefeuille
	**/
    def rafraichirList() {
        portefeuilles = Portefeuille.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstPortefeuille"))
        binder.loadAll()		
		
		projets = Projet.list()		
		
		programmes = Programme.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstPortefeuille").clearSelection()
        portefeuilleSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		projets = Projet.list()
		projetsSelected = null// = new ArrayList()
		
		programmes = Programme.list()
		programmesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			projets = Projet.list()
		}
		this.getFellow("lstprojets").clearSelection()
		projetsSelected = null// = new ArrayList()
		
		if(del) {
			programmes = Programme.list()
		}
		this.getFellow("lstprogrammes").clearSelection()
		programmesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
		
		portefeuille.projets = projetsSelected
		this.getFellow("lstprojets").clearSelection()
		projetsSelected = null// = new ArrayList()
		
		portefeuille.programmes = programmesSelected
		this.getFellow("lstprogrammes").clearSelection()
		programmesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderprojets = new AnnotateDataBinder(this.getFellow("lstprojets"))
		projetsSelected = portefeuilleSelected.projets
        binderprojets.loadAll()
		
		def binderprogrammes = new AnnotateDataBinder(this.getFellow("lstprogrammes"))
		programmesSelected = portefeuilleSelected.programmes
        binderprogrammes.loadAll()
		
	}
}

