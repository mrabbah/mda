/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.contrainte;

/**
 * @Description UrlContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class UrlContrainte extends AbstractContrainte {

    public UrlContrainte() {
        this.nom = "url";
    }

    public void isUrl(boolean test) {
        if (test) {
            valeur = "true";
        } else {
            valeur = "false";
        }
    }
}
