import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * JourSemaineOuvre Window Object
 **/
class JourSemaineOuvreWindow extends Window{
	/**
	* Logger de la class JourSemaineOuvreWindow
	**/
	private Log logger = LogFactory.getLog(JourSemaineOuvreWindow.class)
	
	/**
	* liste de jourSemaineOuvre
	**/
	def jourSemaineOuvres
	/**
	* jourSemaineOuvre selectionn�
	**/
	def jourSemaineOuvreSelected
	/**
	* un nouveau element de jourSemaineOuvre  
	**/
	def jourSemaineOuvre
	
	
	/**
	* liste de heuresTravails
	**/
	def heuresTravails	
	/**
	* heuresTravails  selectionn�
	**/
	def heuresTravailsSelected
	
	/**
	* liste de calendrier
	**/	
	def calendriers	
	/**
	* calendrier  selectionn�
	**/
	def calendrierSelected
	
	/**
	* Constructeur
	**/
	public JourSemaineOuvreWindow () {
        jourSemaineOuvres = JourSemaineOuvre.list()
        jourSemaineOuvreSelected = null
        jourSemaineOuvre = new JourSemaineOuvre()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        jourSemaineOuvre = jourSemaineOuvreSelected	
		afficherValeurAssociation()
        //jourSemaineOuvre.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de jourSemaineOuvre
	**/
    def add() {
		actualiserValeurAssociation()
        jourSemaineOuvre.validate()        
        if(!jourSemaineOuvre.hasErrors()) {			
			try {
				jourSemaineOuvre.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				jourSemaineOuvre = new JourSemaineOuvre()
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
        jourSemaineOuvre = new JourSemaineOuvre()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de jourSemaineOuvre
	**/
    def update() {
		actualiserValeurAssociation()
        jourSemaineOuvre.validate()
        if(!jourSemaineOuvre.hasErrors()) {
            try {
                //jourSemaineOuvre.save()
                jourSemaineOuvre.merge(flush:true)
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
                jourSemaineOuvre = new JourSemaineOuvre()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de jourSemaineOuvre
	**/
    def delete() {
        jourSemaineOuvre.delete(flush:true)
        activerBoutons(false)
        jourSemaineOuvre = new JourSemaineOuvre()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau jourSemaineOuvre
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
	* Rafrichier la liste des jourSemaineOuvre
	**/
    def rafraichirList() {
        jourSemaineOuvres = JourSemaineOuvre.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstJourSemaineOuvre"))
        binder.loadAll()		
		
		heuresTravails = HeuresTravail.list()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstJourSemaineOuvre").clearSelection()
        jourSemaineOuvreSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		heuresTravails = HeuresTravail.list()
		heuresTravailsSelected = null// = new ArrayList()
		
		calendriers = Calendrier.list()		
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
		if(del) {
			heuresTravails = HeuresTravail.list()
		}
		this.getFellow("lstheuresTravails").clearSelection()
		heuresTravailsSelected = null// = new ArrayList()
			
		if(del) {
			calendriers = Calendrier.list()
		}	
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
		
		jourSemaineOuvre.heuresTravails = heuresTravailsSelected
		this.getFellow("lstheuresTravails").clearSelection()
		heuresTravailsSelected = null// = new ArrayList()
				
		jourSemaineOuvre.calendrier = calendrierSelected
		if(calendriers.size() > 0) {
			def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
			calendrierSelected = calendriers.get(0)
			bindercalendrier.loadAll()
		}
		else
			calendrierSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderheuresTravails = new AnnotateDataBinder(this.getFellow("lstheuresTravails"))
		heuresTravailsSelected = jourSemaineOuvreSelected.heuresTravails
        binderheuresTravails.loadAll()
				
		def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
		calendrierSelected = calendriers.find{ it.id == JourSemaineOuvre.findById(jourSemaineOuvre.id).calendrier.id }
        bindercalendrier.loadAll()
		
	}
}

