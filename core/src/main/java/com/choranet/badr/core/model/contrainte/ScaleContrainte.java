/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.model.contrainte;

/**
 * @Description ScaleContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class ScaleContrainte extends AbstractContrainte {

    public void setValeur(Integer valeur) {
        this.valeur = valeur.toString();
    }


    public ScaleContrainte() {
        this.nom = "scale";
    }

}
