import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import ma.rcar.g3p.mapping.*

/**
 * Projet Window Object
 **/
class ProjetWindow extends Window{
	/**
	* Logger de la class ProjetWindow
	**/
	private Log logger = LogFactory.getLog(ProjetWindow.class)
	
	/**
	* liste de projet
	**/
	def projets
	/**
	* projet selectionné
	**/
	def projetSelected
	/**
	* un nouveau element de projet  
	**/
	def projet
	
	
	/**
	* liste de ressources
	**/
	def ressources
	/**
	* ressources selectionné
	**/
	def ressourcesSelected
	
	/**
	* liste de chefProjet
	**/	
	def chefProjets	
	/**
	* chefProjet  selectionné
	**/
	def chefProjetSelected
	
	/**
	* liste de phases
	**/
	def phases	
	/**
	* phases  selectionné
	**/
	def phasesSelected
	
	/**
	* liste de membreComiteSuivis
	**/
	def membreComiteSuivis	
	/**
	* membreComiteSuivis  selectionné
	**/
	def membreComiteSuivisSelected
	
	/**
	* liste de composants
	**/
	def composants	
	/**
	* composants  selectionné
	**/
	def composantsSelected
	
	/**
	* liste de portefeuille
	**/	
	def portefeuilles	
	/**
	* portefeuille  selectionné
	**/
	def portefeuilleSelected
	
	/**
	* liste de calendrier
	**/
	def calendriers	
	/**
	* calendrier  selectionné
	**/
	def calendrierSelected	
	
	/**
	* liste de membreMoas
	**/
	def membreMoas	
	/**
	* membreMoas  selectionné
	**/
	def membreMoasSelected
	
	/**
	* liste de rubriqueBudgetaires
	**/
	def rubriqueBudgetaires	
	/**
	* rubriqueBudgetaires  selectionné
	**/
	def rubriqueBudgetairesSelected
	
	/**
	* liste de membreComitePilotages
	**/
	def membreComitePilotages	
	/**
	* membreComitePilotages  selectionné
	**/
	def membreComitePilotagesSelected
	
	/**
	* liste de opportunites
	**/
	def opportunites	
	/**
	* opportunites  selectionné
	**/
	def opportunitesSelected
	
	/**
	* liste de programme
	**/	
	def programmes	
	/**
	* programme  selectionné
	**/
	def programmeSelected
	
	/**
	* liste de livrables
	**/
	def livrables	
	/**
	* livrables  selectionné
	**/
	def livrablesSelected
	
	/**
	* liste de membreMoes
	**/
	def membreMoes	
	/**
	* membreMoes  selectionné
	**/
	def membreMoesSelected
	
	/**
	* liste de opportunites
	**/
	def opportunites
	/**
	* opportunites selectionné
	**/
	def opportunitesSelected
	
	/**
	* liste de risques
	**/
	def risques	
	/**
	* risques  selectionné
	**/
	def risquesSelected
	
	/**
	* Constructeur
	**/
	public ProjetWindow () {
        projets = Projet.list()
        projetSelected = null
        projet = new Projet()
		initialiserAssociation()
    }   
    /**
	*  Cette fonction est appelée lorsque un élément de la liste est selectionné
	**/
	def select() {                    
        projet = projetSelected	
		afficherValeurAssociation()
        //projet.lock()  //Ne peut etre utilisé que pour le base de donnée qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
	/**
	* Fonction qui se charge de sauveguarder un nouveau élément de projet
	**/
    def add() {
		actualiserValeurAssociation()
        projet.validate()        
        if(!projet.hasErrors()) {			
			try {
				projet.save(flush:true)
			} catch(Exception ex) {
				logger.error "Error: ${ex.message}", ex
				Messagebox.show("Echec lors de la transaction", "Erreur", Messagebox.OK, Messagebox.ERROR)
			} finally {
				projet = new Projet()
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
        projet = new Projet()
		reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
	/**
	* Fonction qui se charge de mettre à jour un élément selectionné de projet
	**/
    def update() {
		actualiserValeurAssociation()
        projet.validate()
        if(!projet.hasErrors()) {
            try {
                //projet.save()
                projet.merge(flush:true)
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
                projet = new Projet()                
                rafraichirField()
                rafraichirList()
            }
        } else {
            Messagebox.show("Echec de l'operation", "Erreur", Messagebox.OK, Messagebox.ERROR);
        } 
		//reinitialiserAssociation()
    }
	/**
	* Fonction qui se charge de supprimer un élément selectinné de projet
	**/
    def delete() {
        projet.delete(flush:true)
        activerBoutons(false)
        projet = new Projet()
        rafraichirField()
        rafraichirList()
		reinitialiserAssociation(true)
    }
	/**
	* Permet d'afficher l'anglet d'ajout d'un nouveau projet
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
	* Rafrichier la liste des projet
	**/
    def rafraichirList() {
        projets = Projet.list()
        def binder = new AnnotateDataBinder(this.getFellow("lstProjet"))
        binder.loadAll()		
		
		ressources = Ressource.list()
				
		
		phases = Phase.list()		
		
		membreComiteSuivis = MembreComiteSuivi.list()		
		
		composants = Composant.list()		
				
				
		
		membreMoas = MembreMoa.list()		
		
		rubriqueBudgetaires = RubriqueBudgetaire.list()		
		
		membreComitePilotages = MembreComitePilotage.list()		
		
		opportunites = Opportunite.list()		
				
		
		livrables = Livrable.list()		
		
		membreMoes = MembreMoe.list()		
		
		opportunites = Opportunite.list()
		
		risques = Risque.list()		
				
        annulerSelection()
    }
	/**
	* Basculer en mode saisi d'un nouveau élément
	**/
    def annulerSelection() {
        this.getFellow("lstProjet").clearSelection()
        projetSelected = null
    }
	
	/**
	* Fonction qui gère l'initialisation des listes d'associations au niveau du constructeur
	**/
	def initialiserAssociation() {
		
		ressources = Ressource.list()
		ressourcesSelected = null// = new ArrayList()
		
		chefProjets = ChefProjet.list()		
		if(chefProjets.size() > 0)
			chefProjetSelected = chefProjets.get(0)
		else
			chefProjetSelected = null
		
		phases = Phase.list()
		phasesSelected = null// = new ArrayList()
		
		membreComiteSuivis = MembreComiteSuivi.list()
		membreComiteSuivisSelected = null// = new ArrayList()
		
		composants = Composant.list()
		composantsSelected = null// = new ArrayList()
		
		portefeuilles = Portefeuille.list()		
		if(portefeuilles.size() > 0)
			portefeuilleSelected = portefeuilles.get(0)
		else
			portefeuilleSelected = null
		
		calendriers = Calendrier.list()		
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
		membreMoas = MembreMoa.list()
		membreMoasSelected = null// = new ArrayList()
		
		rubriqueBudgetaires = RubriqueBudgetaire.list()
		rubriqueBudgetairesSelected = null// = new ArrayList()
		
		membreComitePilotages = MembreComitePilotage.list()
		membreComitePilotagesSelected = null// = new ArrayList()
		
		opportunites = Opportunite.list()
		opportunitesSelected = null// = new ArrayList()
		
		programmes = Programme.list()		
		if(programmes.size() > 0)
			programmeSelected = programmes.get(0)
		else
			programmeSelected = null
		
		livrables = Livrable.list()
		livrablesSelected = null// = new ArrayList()
		
		membreMoes = MembreMoe.list()
		membreMoesSelected = null// = new ArrayList()
		
		opportunites = Opportunite.list()
		opportunitesSelected = null// = new ArrayList()
		
		risques = Risque.list()
		risquesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui permet de ré-initaliser l'association au niveau de l'interface
	* @param del si c'est une réinitionalisation après une suppression ou non
	**/
	def reinitialiserAssociation(del) {
			
		if(del) {
			ressources = Ressource.list()
		}
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
			
		if(del) {
			chefProjets = ChefProjet.list()
		}	
		if(chefProjets.size() > 0)
			chefProjetSelected = chefProjets.get(0)
		else
			chefProjetSelected = null
		
		if(del) {
			phases = Phase.list()
		}
		this.getFellow("lstphases").clearSelection()
		phasesSelected = null// = new ArrayList()
		
		if(del) {
			membreComiteSuivis = MembreComiteSuivi.list()
		}
		this.getFellow("lstmembreComiteSuivis").clearSelection()
		membreComiteSuivisSelected = null// = new ArrayList()
		
		if(del) {
			composants = Composant.list()
		}
		this.getFellow("lstcomposants").clearSelection()
		composantsSelected = null// = new ArrayList()
			
		if(del) {
			portefeuilles = Portefeuille.list()
		}	
		if(portefeuilles.size() > 0)
			portefeuilleSelected = portefeuilles.get(0)
		else
			portefeuilleSelected = null
		
		if(del) {
			calendriers = Calendrier.list()
		}		
		if(calendriers.size() > 0)
			calendrierSelected = calendriers.get(0)
		else
			calendrierSelected = null
		
		if(del) {
			membreMoas = MembreMoa.list()
		}
		this.getFellow("lstmembreMoas").clearSelection()
		membreMoasSelected = null// = new ArrayList()
		
		if(del) {
			rubriqueBudgetaires = RubriqueBudgetaire.list()
		}
		this.getFellow("lstrubriqueBudgetaires").clearSelection()
		rubriqueBudgetairesSelected = null// = new ArrayList()
		
		if(del) {
			membreComitePilotages = MembreComitePilotage.list()
		}
		this.getFellow("lstmembreComitePilotages").clearSelection()
		membreComitePilotagesSelected = null// = new ArrayList()
		
		if(del) {
			opportunites = Opportunite.list()
		}
		this.getFellow("lstopportunites").clearSelection()
		opportunitesSelected = null// = new ArrayList()
			
		if(del) {
			programmes = Programme.list()
		}	
		if(programmes.size() > 0)
			programmeSelected = programmes.get(0)
		else
			programmeSelected = null
		
		if(del) {
			livrables = Livrable.list()
		}
		this.getFellow("lstlivrables").clearSelection()
		livrablesSelected = null// = new ArrayList()
		
		if(del) {
			membreMoes = MembreMoe.list()
		}
		this.getFellow("lstmembreMoes").clearSelection()
		membreMoesSelected = null// = new ArrayList()
			
		if(del) {
			opportunites = Opportunite.list()
		}
		this.getFellow("lstopportunites").clearSelection()
		opportunitesSelected = null// = new ArrayList()
		
		if(del) {
			risques = Risque.list()
		}
		this.getFellow("lstrisques").clearSelection()
		risquesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui copie la valeur de l'association à l'élément courant
	**/
	def actualiserValeurAssociation() {
			
		projet.ressources = ressourcesSelected
		this.getFellow("lstressources").clearSelection()
		ressourcesSelected = null// = new ArrayList()
				
		projet.chefProjet = chefProjetSelected
		if(chefProjets.size() > 0) {
			def binderchefProjet = new AnnotateDataBinder(this.getFellow("cochefProjets"))
			chefProjetSelected = chefProjets.get(0)
			binderchefProjet.loadAll()
		}
		else
			chefProjetSelected = null
		
		projet.phases = phasesSelected
		this.getFellow("lstphases").clearSelection()
		phasesSelected = null// = new ArrayList()
		
		projet.membreComiteSuivis = membreComiteSuivisSelected
		this.getFellow("lstmembreComiteSuivis").clearSelection()
		membreComiteSuivisSelected = null// = new ArrayList()
		
		projet.composants = composantsSelected
		this.getFellow("lstcomposants").clearSelection()
		composantsSelected = null// = new ArrayList()
				
		projet.portefeuille = portefeuilleSelected
		if(portefeuilles.size() > 0) {
			def binderportefeuille = new AnnotateDataBinder(this.getFellow("coportefeuilles"))
			portefeuilleSelected = portefeuilles.get(0)
			binderportefeuille.loadAll()
		}
		else
			portefeuilleSelected = null
				
		projet.calendrier = calendrierSelected
		if(calendriers.size() > 0) {
			def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
			calendrierSelected = calendriers.get(0)
			bindercalendrier.loadAll()
		}
		else
			calendrierSelected = null
		
		projet.membreMoas = membreMoasSelected
		this.getFellow("lstmembreMoas").clearSelection()
		membreMoasSelected = null// = new ArrayList()
		
		projet.rubriqueBudgetaires = rubriqueBudgetairesSelected
		this.getFellow("lstrubriqueBudgetaires").clearSelection()
		rubriqueBudgetairesSelected = null// = new ArrayList()
		
		projet.membreComitePilotages = membreComitePilotagesSelected
		this.getFellow("lstmembreComitePilotages").clearSelection()
		membreComitePilotagesSelected = null// = new ArrayList()
		
		projet.opportunites = opportunitesSelected
		this.getFellow("lstopportunites").clearSelection()
		opportunitesSelected = null// = new ArrayList()
				
		projet.programme = programmeSelected
		if(programmes.size() > 0) {
			def binderprogramme = new AnnotateDataBinder(this.getFellow("coprogrammes"))
			programmeSelected = programmes.get(0)
			binderprogramme.loadAll()
		}
		else
			programmeSelected = null
		
		projet.livrables = livrablesSelected
		this.getFellow("lstlivrables").clearSelection()
		livrablesSelected = null// = new ArrayList()
		
		projet.membreMoes = membreMoesSelected
		this.getFellow("lstmembreMoes").clearSelection()
		membreMoesSelected = null// = new ArrayList()
			
		projet.opportunites = opportunitesSelected
		this.getFellow("lstopportunites").clearSelection()
		opportunitesSelected = null// = new ArrayList()
		
		projet.risques = risquesSelected
		this.getFellow("lstrisques").clearSelection()
		risquesSelected = null// = new ArrayList()
		
	}
	/**
	* Fonction qui fait la liaison entre l'association l'élément selectionné et la liste dans le crud
	**/
	def afficherValeurAssociation() {
		
		def binderressources = new AnnotateDataBinder(this.getFellow("lstressources"))
		ressourcesSelected = projetSelected.ressources
        binderressources.loadAll()		
				
		def binderchefProjet = new AnnotateDataBinder(this.getFellow("cochefProjets"))
		chefProjetSelected = chefProjets.find{ it.id == Projet.findById(projet.id).chefProjet.id }
        binderchefProjet.loadAll()
		
		def binderphases = new AnnotateDataBinder(this.getFellow("lstphases"))
		phasesSelected = projetSelected.phases
        binderphases.loadAll()
		
		def bindermembreComiteSuivis = new AnnotateDataBinder(this.getFellow("lstmembreComiteSuivis"))
		membreComiteSuivisSelected = projetSelected.membreComiteSuivis
        bindermembreComiteSuivis.loadAll()
		
		def bindercomposants = new AnnotateDataBinder(this.getFellow("lstcomposants"))
		composantsSelected = projetSelected.composants
        bindercomposants.loadAll()
				
		def binderportefeuille = new AnnotateDataBinder(this.getFellow("coportefeuilles"))
		portefeuilleSelected = portefeuilles.find{ it.id == Projet.findById(projet.id).portefeuille.id }
        binderportefeuille.loadAll()
								
		def bindercalendrier = new AnnotateDataBinder(this.getFellow("cocalendriers"))
		calendrierSelected = calendriers.find{ it.id == Projet.findById(projet.id).calendrier.id }
        bindercalendrier.loadAll()
		
		def bindermembreMoas = new AnnotateDataBinder(this.getFellow("lstmembreMoas"))
		membreMoasSelected = projetSelected.membreMoas
        bindermembreMoas.loadAll()
		
		def binderrubriqueBudgetaires = new AnnotateDataBinder(this.getFellow("lstrubriqueBudgetaires"))
		rubriqueBudgetairesSelected = projetSelected.rubriqueBudgetaires
        binderrubriqueBudgetaires.loadAll()
		
		def bindermembreComitePilotages = new AnnotateDataBinder(this.getFellow("lstmembreComitePilotages"))
		membreComitePilotagesSelected = projetSelected.membreComitePilotages
        bindermembreComitePilotages.loadAll()
		
		def binderopportunites = new AnnotateDataBinder(this.getFellow("lstopportunites"))
		opportunitesSelected = projetSelected.opportunites
        binderopportunites.loadAll()
				
		def binderprogramme = new AnnotateDataBinder(this.getFellow("coprogrammes"))
		programmeSelected = programmes.find{ it.id == Projet.findById(projet.id).programme.id }
        binderprogramme.loadAll()
		
		def binderlivrables = new AnnotateDataBinder(this.getFellow("lstlivrables"))
		livrablesSelected = projetSelected.livrables
        binderlivrables.loadAll()
		
		def bindermembreMoes = new AnnotateDataBinder(this.getFellow("lstmembreMoes"))
		membreMoesSelected = projetSelected.membreMoes
        bindermembreMoes.loadAll()
		
		def binderopportunites = new AnnotateDataBinder(this.getFellow("lstopportunites"))
		opportunitesSelected = projetSelected.opportunites
        binderopportunites.loadAll()		
		
		def binderrisques = new AnnotateDataBinder(this.getFellow("lstrisques"))
		risquesSelected = projetSelected.risques
        binderrisques.loadAll()
		
	}
}

