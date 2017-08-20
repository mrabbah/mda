import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import demo.*

/**
 * Livre Window Object
 **/
class LivreWindow extends Window{
	/**
	* Logger de la class LivreWindow
	**/
	private Log logger = LogFactory.getLog(LivreWindow.class)
	
	/**
	* liste de livre
	**/
	def livres
	/**
	* livre selectionné
	**/
	def livreSelected
	/**
	* un nouveau element de livre  
	**/
	def livre
	
	
	/**
	* liste de auteurs
	**/
	def auteurs
	/**
	* auteurs selectionné
	**/
	def auteursSelected
	
	/**
	* Constructeur
	**/
	public LivreWindow () {
        livres = Livre.list()
        livreSelected = null
        livre = new Livre()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        livre = livreSelected	
		afficherValeurAssociation()
        //livre.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de livre
	**/
    def add() {
		actualiserValeurAssociation()
        livre.validate()        
        if(!livre.hasErrors()) {			
			try {
				livre.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				livre = new Livre()
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
        livre = new Livre()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de livre
	**/
    def update() {
		actualiserValeurAssociation()
        livre.validate()
        if(!livre.hasErrors()) {
            try {
                //livre.save()
                livre.merge(flush:true)
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
                livre = new Livre()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de livre
	**/
    def delete() {
        livre.delete(flush:true)
        activerBoutons(false)
        livre = new Livre()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau livre
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
	* Rafrichier la liste des livre
	**/
    def rafraichirList() {
        livres = Livre.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstLivre"))
        binder.loadAll()		
		
		auteurs = Auteur.list()
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstLivre").clearSelection()
        livreSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		auteurs = Auteur.list()
		auteursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			auteurs = Auteur.list()
		}
		this.getFellow("lstauteurs").clearSelection()
		auteursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
			
		livre.auteurs = auteursSelected
		this.getFellow("lstauteurs").clearSelection()
		auteursSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderauteurs = new AnnotateDataBinder(this.getFellow("lstauteurs"))
		auteursSelected = livreSelected.auteurs
        binderauteurs.loadAll()		
		
	}
}

