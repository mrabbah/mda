import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import demo.*

/**
 * Etudiant Window Object
 **/
class EtudiantWindow extends Window{
	/**
	* Logger de la class EtudiantWindow
	**/
	private Log logger = LogFactory.getLog(EtudiantWindow.class)
	
	/**
	* liste de etudiant
	**/
	def etudiants
	/**
	* etudiant selectionn�
	**/
	def etudiantSelected
	/**
	* un nouveau element de etudiant  
	**/
	def etudiant
	
	
	/**
	* Constructeur
	**/
	public EtudiantWindow () {
        etudiants = Etudiant.list()
        etudiantSelected = null
        etudiant = new Etudiant()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
	**/
	def select() {                    
        etudiant = etudiantSelected	
		afficherValeurAssociation()
        //etudiant.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau �l�ment de etudiant
	**/
    def add() {
		actualiserValeurAssociation()
        etudiant.validate()        
        if(!etudiant.hasErrors()) {			
			try {
				etudiant.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				etudiant = new Etudiant()
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
        etudiant = new Etudiant()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre � jour un �l�ment selectionn� de etudiant
	**/
    def update() {
		actualiserValeurAssociation()
        etudiant.validate()
        if(!etudiant.hasErrors()) {
            try {
                //etudiant.save()
                etudiant.merge(flush:true)
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
                etudiant = new Etudiant()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un �l�ment selectinn� de etudiant
	**/
    def delete() {
        etudiant.delete(flush:true)
        activerBoutons(false)
        etudiant = new Etudiant()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau etudiant
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
	* Rafrichier la liste des etudiant
	**/
    def rafraichirList() {
        etudiants = Etudiant.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstEtudiant"))
        binder.loadAll()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau �l�ment
	**/
    def annulerSelection() {
        this.getFellow("lstEtudiant").clearSelection()
        etudiantSelected = null
    }
	
	/**
	* Fonction qui g�re l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
	}
	/**
	* Fonction qui permet de r�-initaliser l'association au niveau de l'interface
	* @param del si c'est une r�initionalisation apr�s une suppression ou non
	**/
	def reinitialiserAssociation(del) {
		
	}
	/**
	* Fonction qui copie la valeur de l'association � l'�l�ment courant
	**/
	def actualiserValeurAssociation() {
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'�l�ment selectionn� et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
	}
}

