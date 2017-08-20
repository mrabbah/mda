/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.contrainte;

/**
 * @Description CreditCardContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class CreditCardContrainte extends AbstractContrainte {

    public CreditCardContrainte() {
        this.nom = "creditCard";
    }

    public void isCreditCard(boolean test) {
        if (test) {
            valeur = "true";
        } else {
            valeur = "false";
        }
    }
}
