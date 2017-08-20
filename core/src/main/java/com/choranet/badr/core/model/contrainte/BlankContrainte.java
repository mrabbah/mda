/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.contrainte;

/**
 * @Description BlankContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class BlankContrainte extends AbstractContrainte {

    public BlankContrainte() {
        this.nom = "blank";
    }

    public void isBlank(boolean test) {
        if(test) {
            valeur = "true";
        } else {
            valeur = "false";
        }
    }
}
