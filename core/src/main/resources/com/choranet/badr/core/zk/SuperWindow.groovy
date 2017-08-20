package _PACKAGE_


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

//import org.codehaus.groovy.grails.plugins.springsecurity.Secured

/**
 * Super Window Object
 **/
//@Secured(['ROLE_ROOT'])
abstract class SuperWindow extends Window {
    
    /**
     * la classe fille de la super window :)
     * */
    def clazz
    /**
     * Nom de la classe fille
     **/
    def nomClassFille
    /**
     * Objet qui va contenir le filtre a utiliser
     * */
    def filtre
    /**
     * En cas ou on veut appliquer le filtre seulement sur certains attributs
     * */
    def attributsAFiltrer
    /**
     * Logger de la class ArticleWindow
     **/
    private Log logger = LogFactory.getLog(SuperWindow.class)

    /**
     * liste des objets
     **/
    def listeObjets
    /**
     * article selectionn�
     **/
    def objetSelected
    /**
     * un nouveau element objet  
     **/
    def objet

    /**
     * Taille de liste extraite de la BdD
     * */
    def tailleListe
    
    /**
     * offset de la requete
     * */
    def ofs
    
    /**
     * nombre maximum a extraire de la BdD
     * */
    def maxNb
    /**
     * l element sur lequel va s effectuer le trie
     * */
    def sortedHeader = ""
    
    /**
     * direction du trie
     * */
    def sortedDirection = ""
    
    /**
     * Constructeur
     **/
    public SuperWindow (clazz, maxNb, attributsAFiltrer) throws Exception {
        initialisation(clazz, maxNb, attributsAFiltrer)
    }   
    
    /**
     * Constructeur
     **/
    public SuperWindow (clazz, maxNb) throws Exception {
        initialisation(clazz, maxNb, null)
    }   
    
    /**
     * Constructeur
     **/
    public SuperWindow (clazz) throws Exception {
        initialisation(clazz, 14, null)
    }   
    
    private void initialisation(clazz, maxNb, attributsAFiltrer) throws Exception {
        try {
            this.clazz = clazz
            this.maxNb = maxNb
            this.attributsAFiltrer = attributsAFiltrer
            this.nomClassFille = clazz.getSimpleName()
            ofs = 0
            listeObjets = clazz.list(offset:ofs, max:maxNb)
            def criteria = clazz.createCriteria()
            tailleListe = criteria.count{} 
            objetSelected = null
            objet = clazz.newInstance()
            filtre = clazz.newInstance()
            initialiserAssociation()
        } catch(Exception e) {
            logger.error(e)
            throw e
        }
    }
    def filtrer() {
        try {
            def map
            if(attributsAFiltrer == null) {
                map = getService().filtrer(clazz, filtre, sortedHeader, sortedDirection, ofs, maxNb)
            } else {
                map = getService().filtrer(clazz, filtre, attributsAFiltrer, sortedHeader, sortedDirection, ofs, maxNb)
            }
            tailleListe = map["tailleListe"]
            listeObjets = map["listeObjets"]
            def binder = new AnnotateDataBinder(this.getFellow("lstObjet"))
            binder.loadAll()
        
            def binder2 = new AnnotateDataBinder(this.getFellow("paging"))
            binder2.loadAll()    
        } catch (Exception ex) {
            logger.error(ex)
        }        
    }
    
    def sort(event) {
        sortedHeader = event.getTarget().getId()
        def sortDirection =  event.getTarget().getSortDirection()
        sortedDirection = "asc"

        if(sortDirection == "natural" || sortDirection == "descending") {
            sortedDirection = "desc"
        }
        
        filtrer()
    }
    
    def getNextElements(event) {
        def pgno = event.getActivePage()
        ofs = pgno * event.getPageable().getPageSize()
        
        filtrer()
    }
    
    /**
     *  Cette fonction est appel�e lorsque un �l�ment de la liste est selectionn�
     **/
    def select() {                    
        objet = objetSelected	
        afficherValeurAssociation()
        //article.lock()  //Ne peut etre utilis� que pour le base de donn�e qui accepte le veruillage des enregisterments
        rafraichirField()
        activerBoutons(true)
    }
    /**
     * Fonction qui se charge de sauveguarder un nouveau �l�ment de article
     **/
    def add() {
	actualiserValeurAssociation()
        try {     
            getService().save(objet)
        } catch(Exception ex) {
            logger.error "Error: ${ex.message}", ex
            Messagebox.show("Echec lors de la transaction\n" + ex.message, "Erreur", Messagebox.OK, Messagebox.ERROR)
        } finally {
            objet = clazz.newInstance()
            rafraichirField()
            rafraichirList()
            activerBoutons(false)
        }
    }
    /**
     * Pour annuler la modification ou la supression et pour basculer en mode ajout d'un nouveau �l�ment
     **/
    def cancel() {        
        objet = clazz.newInstance()
	reinitialiserAssociation(false)
        rafraichirField()
        activerBoutons(false)
        annulerSelection()
    }
    /**
     * Fonction qui se charge de mettre � jour un �l�ment selectionn� de article
     **/
    def update() {
	actualiserValeurAssociation()
        try {
            getService().update(objet)
        }
        catch(Exception e) {
            logger.error "Error: ${e.message}", e
            Messagebox.show("Probleme lors de la mise à jour", "Erreur", Messagebox.OK, Messagebox.ERROR)
        }
        finally {
            activerBoutons(false)
            objet = clazz.newInstance()
            rafraichirField()
            rafraichirList()
        }
    }
    /**
     * Fonction qui se charge de supprimer un �l�ment selectinn� de article
     **/
    def delete() {
        getService().delete(objet)
        activerBoutons(false)
        objet = clazz.newInstance()
        rafraichirField()
        rafraichirList()
        reinitialiserAssociation(true)
    }
    /**
     * Permet d'afficher l'anglet d'ajout d'un nouveau article
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
        //this.getFellow("btnExport").visible = !visible
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
     * Rafrichier la liste des article
     **/
    def rafraichirList() {
        filtrer()		
        annulerSelection()
    }
    /**
     * Basculer en mode saisi d'un nouveau �l�ment
     **/
    def annulerSelection() {
        this.getFellow("lstObjet").clearSelection()
        objetSelected = null
    }
    //	
    //    def exporterRapport() {
    //        def avecimage = false
    //        def articlesACommander = articleService.getArticlesACommander()
    //        
    //        Workbook bk = new HSSFWorkbook(); //or new HSSFWorkbook(); org.​apache.​poi.​ss.​usermodel
    //        Sheet sheet = bk.createSheet("Commande à passer");
    //        
    //        
    //        //TWACHA **************
    //        CellStyle style = bk.createCellStyle();
    //        //style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
    //        //style.setFillPattern(CellStyle.ALIGN_CENTER);
    //        org.apache.poi.ss.usermodel.Font font = bk.createFont();
    //        font.setFontHeightInPoints((short)12);
    //        font.setFontName("Courier New");
    //        font.setItalic(true);
    //        style.setFont(font);
    //        
    //        CellStyle stylebis = bk.createCellStyle();
    //        //style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
    //        //stylebis.setFillPattern(CellStyle.ALIGN_CENTER);
    //        stylebis.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //        //org.apache.poi.ss.usermodel.Font fontbis = bk.createFont();
    //        //fontbis.setFontName("Courier New");
    //        //stylebis.setFont(fontbis);
    //        //FIN TWACHA ************
    //        org.apache.poi.ss.usermodel.Row row0 = sheet.createRow((short)0);
    //        org.apache.poi.ss.usermodel.Cell cell00 = row0.createCell(0);
    //        cell00.setCellValue("Code");
    //        cell00.setCellStyle(style);
    //        org.apache.poi.ss.usermodel.Cell cell01 = row0.createCell(1);
    //        cell01.setCellValue("Désignation fr");
    //        cell01.setCellStyle(style);
    //        org.apache.poi.ss.usermodel.Cell cell02 = row0.createCell(2);
    //        cell02.setCellValue("Désignation en");
    //        cell02.setCellStyle(style);
    //        org.apache.poi.ss.usermodel.Cell cell03 = row0.createCell(3);
    //        cell03.setCellValue("Caractéristiques");
    //        cell03.setCellStyle(style);
    //        org.apache.poi.ss.usermodel.Cell cell04 = row0.createCell(4);
    //        cell04.setCellValue("Quantité disponible");
    //        cell04.setCellStyle(style);
    //        org.apache.poi.ss.usermodel.Cell cell05 = row0.createCell(5);
    //        cell05.setCellValue("Quantité à commander");
    //        cell05.setCellStyle(style);
    //        if(avecimage) {
    //            org.apache.poi.ss.usermodel.Cell cell06 = row0.createCell(6);
    //            cell06.setCellValue("Image produit");
    //            cell06.setCellStyle(style);
    //        }
    //        articlesACommander.eachWithIndex() { article, index ->
    //            org.apache.poi.ss.usermodel.Row row = sheet.createRow((short)(index + 1));
    //            org.apache.poi.ss.usermodel.Cell cell0 = row.createCell(0);
    //            org.apache.poi.ss.usermodel.Cell cell1 = row.createCell(1);
    //            org.apache.poi.ss.usermodel.Cell cell2 = row.createCell(2);
    //            org.apache.poi.ss.usermodel.Cell cell3 = row.createCell(3);
    //            org.apache.poi.ss.usermodel.Cell cell4 = row.createCell(4);
    //            org.apache.poi.ss.usermodel.Cell cell5 = row.createCell(5);
    //            
    //            cell0.setCellStyle(stylebis);
    //            cell1.setCellStyle(stylebis);
    //            cell2.setCellStyle(stylebis);
    //            cell3.setCellStyle(stylebis);
    //            cell4.setCellStyle(stylebis);
    //            cell5.setCellStyle(stylebis);
    //            
    //            cell0.setCellValue(article.code);
    //            cell1.setCellValue(article.designation_fr);
    //            cell2.setCellValue(article.designation_en);
    //            cell3.setCellValue(article.caracteristiques);
    //            cell4.setCellValue(formatter(article.quantiteDisponible));
    //            cell5.setCellValue(article.quantiteACommander);            
    //            if(avecimage) {
    //                def pictureIdx = 0
    //                byte[] imageData = article.image.getByteData()
    //                def format = article.image.format
    //                if(format == "png") {
    //                    pictureIdx = bk.addPicture(imageData,  Workbook.PICTURE_TYPE_PNG)
    //                } else if (format == "jpeg") {
    //                    pictureIdx = bk.addPicture(imageData,  Workbook.PICTURE_TYPE_JPEG)
    //                } else if (format == "gif") {
    //                    pictureIdx = bk.addPicture(imageData,  Workbook.PICTURE_TYPE_PNG)
    //                } else {
    //                    throw new Exception("Format image non supporte :" + article.image.format)
    //                }
    //                
    //                Drawing dw = sheet.createDrawingPatriarch();
    //                CreationHelper ch = bk.getCreationHelper();
    //                ClientAnchor ca = ch.createClientAnchor();
    //                ca.setCol1(6);
    //                ca.setRow1(index + 1);
    //                ca.setCol2(6);
    //                ca.setRow2(index +1);
    //                ca.setDx1(0);
    //                ca.setDy1(0);
    //                ca.setDx2(100);
    //                ca.setDy2(100);
    //   
    //                Picture picture = dw.createPicture(ca, pictureIdx);
    //                picture.resize();
    //                row.setHeightInPoints((short)100)
    //            }
    //        }
    //        sheet.autoSizeColumn(0);
    //        sheet.autoSizeColumn(1);
    //        sheet.autoSizeColumn(2);
    //        sheet.autoSizeColumn(3);
    //        sheet.autoSizeColumn(4);
    //        sheet.autoSizeColumn(5);
    //        if(avecimage) {
    //            sheet.autoSizeColumn(6);
    //        }
    //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //        bk.write(baos)
    //        def nom_fichier = "ArticlesACommander.xls"
    //        Filedownload.save(baos.toByteArray(), "application/file",
    //            nom_fichier);
    //    }


    protected abstract SuperService getService();
    
    def initialiserAssociation() {}
    def afficherValeurAssociation() {}
    def reinitialiserAssociation(test) {}
    def actualiserValeurAssociation() {}
}

