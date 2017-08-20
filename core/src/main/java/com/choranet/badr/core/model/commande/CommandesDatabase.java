/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.commande;

import java.util.EnumMap;
import java.util.Map;

/**
 * @Description CommandesDatabase
 * @version 1.0-SNAPSHOT
 * @date 20 févr. 2011
 * @author rabbah
 */
public class CommandesDatabase {

    public final static Map<BriquesTechniquesCommandes, String> briquesTechniquesCommandesDb =
            new EnumMap<BriquesTechniquesCommandes, String>(BriquesTechniquesCommandes.class);
    public final static Map<ScaffoldingCommandes, String> scaffoldingCommandesDb =
            new EnumMap<ScaffoldingCommandes, String>(ScaffoldingCommandes.class);
    public final static Map<GeneralGrailsCommandes, String> generalGrailsCommandesDb =
            new EnumMap<GeneralGrailsCommandes, String>(GeneralGrailsCommandes.class);

    static {
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.ACEGI, "grails install-plugin acegi");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.ACTIVEMQ, "grails install-plugin activemq");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.ACTIVITIBPM, "grails install-plugin activiti");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.AJAXFLOW, "grails install-plugin ajaxflow");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.APACHEAXIS2, "grails install-plugin axis2");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.APPLET, "grails install-plugin applet");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.APPLICATIONINFO, "grails install-plugin app-info");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.AUDITLOGGIN, "grails install-plugin audit-logging");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.AUDITTRAIL, "grails install-plugin audit-trail");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.CLAMAV, "grails install-plugin clamav");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.CLASSDIAGRAM, "grails install-plugin class-diagram");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.CREATEDOMAINUML, "grails install-plugin create-domain-uml");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.CXF, "grails install-plugin cxf");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.DBUNIT, "grails install-plugin http://dbunit-plugin.googlecode.com/files/grails-dbunit-plugin-0.1.zip");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.DBUTIL, "grails install-plugin db-util");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.DYNAMICDOMAINCLASS, "grails install-plugin dynamic-domain-class");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.DYNAMICJASPERREPORT, "grails install-plugin dynamic-jasper");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.ENCRYPTION, "grails install-plugin crypto");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.EXPORT, "grails install-plugin export");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FCKEDITOR, "grails install-plugin fckeditor");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FILEUPLOADER, "grails install-plugin http://cloud.github.com/downloads/lucastex/grails-file-uploader/grails-file-uploader-1.1.zip");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FITENESSE, "grails install-plugin fitnesse");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FLEX, "grails install-plugin flex-scaffold");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FORMHELPER, "grails install-plugin form-helper");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.FUNCTIONALTESTING, "grails install-plugin functional-test");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.GOOGLECHART, "grails install-plugin google-chart");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.GRAILSFLOW, "grails install-plugin grails-flow");//TODO verfication
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.JASPER, "grails install-plugin jasper");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.JFREECHART, "grails install-plugin eastwood-chart");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.JMS, "grails install-plugin jms");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.JMX, "grails install-plugin jmx");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.JSECURITY, "grails install-plugin jsecurity");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.LDAP, "grails install-plugin ldap");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.LICENSE, "grails install-plugin license");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.MAIL, "grails install-plugin mail");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.OPENFLASHCHART, "grails install-plugin ofchart");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.OPENLASZLO, "grails install-plugin laszlo");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.OSGI, "grails install-plugin osgi");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.OSWORKFLOW, "grails install-plugin osworkflow");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.QUARTZ, "grails install-plugin quartz");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.RESTCLIENT, "grails install-plugin restclient");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.REVERSEENGENIEERING, "grails install-plugin reverseengenieering");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.RICHUI, "grails install-plugin richui");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.SEARCHABLE, "grails install-plugin searchable");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.SELENIUM, "grails install-plugin selenium");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.SIMPLECAPTCHA, "grails install-plugin simplecaptcha");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.SPRINGSECURITYCORE, "grails install-plugin springsecuritycore");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.SPRINGWS, "grails install-plugin sprigws");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.WEBSEARCH, "grails install-plugin websearch");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.WEBTEST, "grails install-plugin webtest");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.WSCLIENT, "grails install-plugin wsclient");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.YUI, "grails install-plugin yui");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.ZK, "grails install-plugin zk");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.WEBSNAPS, "grails install-plugin web-snaps");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.IMAGEZOOM, "grails install-plugin imagezoom");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.CSVPARSER, "grails install-plugin csv");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.EXCELIMPORT, "grails install-plugin excel-import");
        briquesTechniquesCommandesDb.put(BriquesTechniquesCommandes.I18, "grails install-plugin i18n-templates");

        scaffoldingCommandesDb.put(ScaffoldingCommandes.GENERATEALLGRAILS, "grails generate-all ");
        scaffoldingCommandesDb.put(ScaffoldingCommandes.GENERATELASZLO, "grails generate-laszlo ");
        scaffoldingCommandesDb.put(ScaffoldingCommandes.GENERATEALLFLEX, "grails generate-all-flex ");

        generalGrailsCommandesDb.put(GeneralGrailsCommandes.CREATEPROJECT, "grails create-app ");
        generalGrailsCommandesDb.put(GeneralGrailsCommandes.RUNAPPLICATION, "grails run-app");
        generalGrailsCommandesDb.put(GeneralGrailsCommandes.INSTALLTEMPLATES, "grails install-templates");

    }
}
