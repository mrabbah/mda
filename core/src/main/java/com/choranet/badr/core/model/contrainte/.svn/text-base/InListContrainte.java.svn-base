/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.model.contrainte;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description InListContrainte
 * @version 1.0-SNAPSHOT
 * @date 17 févr. 2011
 * @author rabbah
 */
public class InListContrainte extends AbstractContrainte {

    private List<String> listeChoix;

    public InListContrainte() {
        this.nom="inList";
    }

    public void addChoix(String choix) {
        if(listeChoix == null) {
            listeChoix = new ArrayList<String>();
        }
        listeChoix.add(choix);
        updateValue();
    }

    public void removeChoix(String choix) {
        if(listeChoix != null && !listeChoix.isEmpty()) {
            listeChoix.remove(choix);
        }
        updateValue();
    }

    private void updateValue() {
        this.valeur = "[\"";
         Iterator<String> it = listeChoix.iterator();
        this.valeur += it.next();
        while (it.hasNext()) {
            String choix = it.next();
            this.valeur += "\", \"" + choix;
        }
        this.valeur += "\"]";
    }

    @Override
    public String toString() {
        if(listeChoix == null || listeChoix.isEmpty()) {
            return "";
        }
        
        String response = "inList:[\"";
        Iterator<String> it = listeChoix.iterator();
        response += it.next();
        while (it.hasNext()) {
            String choix = it.next();
            response += "\", \"" + choix;
        }
        response += "\"]";
        return response;
    }

    public List<String> getListeChoix() {
        return listeChoix;
    }

}
