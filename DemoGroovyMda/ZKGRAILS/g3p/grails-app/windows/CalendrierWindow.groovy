import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Calendrier Window Object
 **/
class CalendrierWindow extends Window{
	/**
	* Logger de la class CalendrierWindow
	**/
	private Log logger = LogFactory.getLog(CalendrierWindow.class)
	
	/**
	* liste de calendrier
	**/
	def calendriers
	/**
	* calendrier selectionn�
	**/
	def calendrierSelected
	/**
	* un nouveau element de calendrier  
	**/
	def calendrier
	
	
	/**
	* liste de ressources
	**/
	def ressources	
	/**
	* ressources  selectionn�
	**/
	def ressourcesSelected
	
	/**
	* liste de joursChomess
	**/
	def joursChomess	
	/**
	* joursChomess  selectionn�
	**/
	def joursChomessSelected
	
	/**
	* liste de jourSemaineOuvres
	**/
	def jourSemaineOuvres	
	/**
	* jourSemaineOuvres  selectionn�
	**/
	def jourSemaineOuvresSelected
	
	/**
	* liste de groupeRessources
	**/
	def groupeRessources	
	/**
	* groupeRessources  selectionn�
	**/
	def groupeRessourcesSelected
	
	/**
	* Constructeur
	**/
	public CalendrierWindow () {
        calendriers = Calendrier.list()
        calendrierSelected = null
        calendrier = new Calendrier()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        calendrier = calendrierSelected	
		afficherValeurAssociation()
        //calendrier.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de calendrier
	**/
    def add() {
		actualiserValeurAssociation()
        calendrier.validate()        
        if(!calendrier.hasErrors()) {			
			try {
				calendrier.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				calendrier = new Calendrier()
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
        calendrier = new Calendrier()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de calendrier
	**/
    def update() {
		actualiserValeurAssociation()
        calendrier.validate()
        if(!calendrier.hasErrors()) {
            try {
                //calendrier.save()
                calendrier.merge(flush:true)
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
                calendrier = new Calendrier()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de calendrier
	**/
    def delete() {
        calendrier.delete(flush:true)
        activerBoutons(false)
        calendrier = new Calendrier()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau calendrier
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
	* Rafrichier la liste des calendrier
	**/
    def rafraichirList() {
        calendriers = Calendrier.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstCalendrier"))
        binder.loadAll()		
		
		ressources = Ressource.list()		
		
		joursChomess = JoursChomes.list()		
		
		jourSemaineOuvres = JourSemaineOuvre.list()		
		
		groupeRessources = GroupeRessource.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstCalendrier").clearSelection()
        calendrierSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		ressources = Ressource.list()
		ressourcesSelected = null// = new ArrayList()
		
		joursChomess = JoursChomes.list()
		joursChomessSelected = null// = new ArrayList()
		
		jourSemaineOuvres = JourSemaineOuvre.list()
		jourSemaineOuvresSelected = null// = new ArrayList()
		
		groupeRessources = GroupeRessource.list()
		groupeRessourcesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			ressources = Ressource.list()
		}
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
		
		if(del) {
			joursChomess = JoursChomes.list()
		}
		this.getFellow("lstjoursChomess").clearSelection()
		joursChomessSelected = null// = new ArrayList()
		
		if(del) {
			jourSemaineOuvres = JourSemaineOuvre.list()
		}
		this.getFellow("lstjourSemaineOuvres").clearSelection()
		jourSemaineOuvresSelected = null// = new ArrayList()
		
		if(del) {
			groupeRessources = GroupeRessource.list()
		}
		this.getFellow("lstgroupeRessources").clearSelection()
		groupeRessourcesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
		
		calendrier.ressources = ressourcesSelected
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
		
		calendrier.joursChomess = joursChomessSelected
		this.getFellow("lstjoursChomess").clearSelection()
		joursChomessSelected = null// = new ArrayList()
		
		calendrier.jourSemaineOuvres = jourSemaineOuvresSelected
		this.getFellow("lstjourSemaineOuvres").clearSelection()
		jourSemaineOuvresSelected = null// = new ArrayList()
		
		calendrier.groupeRessources = groupeRessourcesSelected
		this.getFellow("lstgroupeRessources").clearSelection()
		groupeRessourcesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderressources = new AnnotateDataBinder(this.getFellow("lstressources"))
		ressourcesSelected = calendrierSelected.ressources
        binderressources.loadAll()
		
		def binderjoursChomess = new AnnotateDataBinder(this.getFellow("lstjoursChomess"))
		joursChomessSelected = calendrierSelected.joursChomess
        binderjoursChomess.loadAll()
		
		def binderjourSemaineOuvres = new AnnotateDataBinder(this.getFellow("lstjourSemaineOuvres"))
		jourSemaineOuvresSelected = calendrierSelected.jourSemaineOuvres
        binderjourSemaineOuvres.loadAll()
		
		def bindergroupeRessources = new AnnotateDataBinder(this.getFellow("lstgroupeRessources"))
		groupeRessourcesSelected = calendrierSelected.groupeRessources
        bindergroupeRessources.loadAll()
		
	}
}

