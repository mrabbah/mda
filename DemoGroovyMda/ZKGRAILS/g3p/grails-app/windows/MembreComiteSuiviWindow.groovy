import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * MembreComiteSuivi Window Object
 **/
class MembreComiteSuiviWindow extends Window{
	/**
	* Logger de la class MembreComiteSuiviWindow
	**/
	private Log logger = LogFactory.getLog(MembreComiteSuiviWindow.class)
	
	/**
	* liste de membreComiteSuivi
	**/
	def membreComiteSuivis
	/**
	* membreComiteSuivi selectionné
	**/
	def membreComiteSuiviSelected
	/**
	* un nouveau element de membreComiteSuivi  
	**/
	def membreComiteSuivi
	
	
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
	public MembreComiteSuiviWindow () {
        membreComiteSuivis = MembreComiteSuivi.list()
        membreComiteSuiviSelected = null
        membreComiteSuivi = new MembreComiteSuivi()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        membreComiteSuivi = membreComiteSuiviSelected	
		afficherValeurAssociation()
        //membreComiteSuivi.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de membreComiteSuivi
	**/
    def add() {
		actualiserValeurAssociation()
        membreComiteSuivi.validate()        
        if(!membreComiteSuivi.hasErrors()) {			
			try {
				membreComiteSuivi.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				membreComiteSuivi = new MembreComiteSuivi()
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
        membreComiteSuivi = new MembreComiteSuivi()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de membreComiteSuivi
	**/
    def update() {
		actualiserValeurAssociation()
        membreComiteSuivi.validate()
        if(!membreComiteSuivi.hasErrors()) {
            try {
                //membreComiteSuivi.save()
                membreComiteSuivi.merge(flush:true)
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
                membreComiteSuivi = new MembreComiteSuivi()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de membreComiteSuivi
	**/
    def delete() {
        membreComiteSuivi.delete(flush:true)
        activerBoutons(false)
        membreComiteSuivi = new MembreComiteSuivi()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau membreComiteSuivi
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
	* Rafrichier la liste des membreComiteSuivi
	**/
    def rafraichirList() {
        membreComiteSuivis = MembreComiteSuivi.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstMembreComiteSuivi"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstMembreComiteSuivi").clearSelection()
        membreComiteSuiviSelected = null
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
				
		membreComiteSuivi.projet = projetSelected
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
		projetSelected = projets.find{ it.id == MembreComiteSuivi.findById(membreComiteSuivi.id).projet.id }
        binderprojet.loadAll()
		
	}
}

