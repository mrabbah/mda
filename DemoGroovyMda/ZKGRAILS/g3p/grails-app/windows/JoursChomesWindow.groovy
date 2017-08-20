import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * JoursChomes Window Object
 **/
class JoursChomesWindow extends Window{
	/**
	* Logger de la class JoursChomesWindow
	**/
	private Log logger = LogFactory.getLog(JoursChomesWindow.class)
	
	/**
	* liste de joursChomes
	**/
	def joursChomess
	/**
	* joursChomes selectionn�
	**/
	def joursChomesSelected
	/**
	* un nouveau element de joursChomes  
	**/
	def joursChomes
	
	
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
	public JoursChomesWindow () {
        joursChomess = JoursChomes.list()
        joursChomesSelected = null
        joursChomes = new JoursChomes()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        joursChomes = joursChomesSelected	
		afficherValeurAssociation()
        //joursChomes.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de joursChomes
	**/
    def add() {
		actualiserValeurAssociation()
        joursChomes.validate()        
        if(!joursChomes.hasErrors()) {			
			try {
				joursChomes.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				joursChomes = new JoursChomes()
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
        joursChomes = new JoursChomes()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de joursChomes
	**/
    def update() {
		actualiserValeurAssociation()
        joursChomes.validate()
        if(!joursChomes.hasErrors()) {
            try {
                //joursChomes.save()
                joursChomes.merge(flush:true)
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
                joursChomes = new JoursChomes()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de joursChomes
	**/
    def delete() {
        joursChomes.delete(flush:true)
        activerBoutons(false)
        joursChomes = new JoursChomes()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau joursChomes
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
	* Rafrichier la liste des joursChomes
	**/
    def rafraichirList() {
        joursChomess = JoursChomes.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstJoursChomes"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstJoursChomes").clearSelection()
        joursChomesSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
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
				
		joursChomes.calendrier = calendrierSelected
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
				
		def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
		calendrierSelected = calendriers.find{ it.id == JoursChomes.findById(joursChomes.id).calendrier.id }
        bindercalendrier.loadAll()
		
	}
}

