/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Clazz appartenant au domain de l application generee
 * @version 1.0-SNAPSHOT
 * @date 17 févr. 2011
 * @author rabbah
 */
public class Clazz {

    private String packageName;
    private String nom;
    private List<Attribut> attributs;

    /**
     * Obtenir la liste des attributs de la class
     * @return liste des attributs de la class
     */
    public List<Attribut> getAttributs() {
        if (attributs == null) {
            attributs = new ArrayList<Attribut>();
        }
        return attributs;
    }

    /**
     * getter du nom de la classe
     * @return nom de la classe
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom de la classe
     * @param nom de la classe
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return (packageName + "." + nom);
    }
}
