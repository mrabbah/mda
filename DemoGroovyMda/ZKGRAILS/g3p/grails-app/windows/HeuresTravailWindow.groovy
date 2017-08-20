import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * HeuresTravail Window Object
 **/
class HeuresTravailWindow extends Window{
	/**
	* Logger de la class HeuresTravailWindow
	**/
	private Log logger = LogFactory.getLog(HeuresTravailWindow.class)
	
	/**
	* liste de heuresTravail
	**/
	def heuresTravails
	/**
	* heuresTravail selectionn�
	**/
	def heuresTravailSelected
	/**
	* un nouveau element de heuresTravail  
	**/
	def heuresTravail
	
	
	/**
	* liste de jourSemaineOuvre
	**/	
	def jourSemaineOuvres	
	/**
	* jourSemaineOuvre  selectionn�
	**/
	def jourSemaineOuvreSelected
	
	/**
	* Constructeur
	**/
	public HeuresTravailWindow () {
        heuresTravails = HeuresTravail.list()
        heuresTravailSelected = null
        heuresTravail = new HeuresTravail()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        heuresTravail = heuresTravailSelected	
		afficherValeurAssociation()
        //heuresTravail.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de heuresTravail
	**/
    def add() {
		actualiserValeurAssociation()
        heuresTravail.validate()        
        if(!heuresTravail.hasErrors()) {			
			try {
				heuresTravail.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				heuresTravail = new HeuresTravail()
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
        heuresTravail = new HeuresTravail()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de heuresTravail
	**/
    def update() {
		actualiserValeurAssociation()
        heuresTravail.validate()
        if(!heuresTravail.hasErrors()) {
            try {
                //heuresTravail.save()
                heuresTravail.merge(flush:true)
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
                heuresTravail = new HeuresTravail()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de heuresTravail
	**/
    def delete() {
        heuresTravail.delete(flush:true)
        activerBoutons(false)
        heuresTravail = new HeuresTravail()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau heuresTravail
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
	* Rafrichier la liste des heuresTravail
	**/
    def rafraichirList() {
        heuresTravails = HeuresTravail.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstHeuresTravail"))
        binder.loadAll()		
				
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstHeuresTravail").clearSelection()
        heuresTravailSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		jourSemaineOuvres = JourSemaineOuvre.list()		
		if(jourSemaineOuvres.size() > 0)
			jourSemaineOuvreSelected = jourSemaineOuvres.get(0)
		else
			jourSemaineOuvreSelected = null
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			jourSemaineOuvres = JourSemaineOuvre.list()
		}	
		if(jourSemaineOuvres.size() > 0)
			jourSemaineOuvreSelected = jourSemaineOuvres.get(0)
		else
			jourSemaineOuvreSelected = null
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
				
		heuresTravail.jourSemaineOuvre = jourSemaineOuvreSelected
		if(jourSemaineOuvres.size() > 0) {
			def binderjourSemaineOuvre = new AnnotateDataBinder(this.getFellow("cojourSemaineOuvres"))
			jourSemaineOuvreSelected = jourSemaineOuvres.get(0)
			binderjourSemaineOuvre.loadAll()
		}
		else
			jourSemaineOuvreSelected = null
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
				
		def binderjourSemaineOuvre = new AnnotateDataBinder(this.getFellow("cojourSemaineOuvres"))
		jourSemaineOuvreSelected = jourSemaineOuvres.find{ it.id == HeuresTravail.findById(heuresTravail.id).jourSemaineOuvre.id }
        binderjourSemaineOuvre.loadAll()
		
	}
}

