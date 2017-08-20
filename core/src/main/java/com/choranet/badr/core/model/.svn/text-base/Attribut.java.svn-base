/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model;

import com.choranet.badr.core.model.contrainte.AbstractContrainte;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @Description Attribut d un class donnee
 * @version 1.0-SNAPSHOT
 * @date 17 févr. 2011
 * @author rabbah
 */
public class Attribut {

    private String nom;
    private String type;
    private List<AbstractContrainte> contraintes;

    public static TypeAttributEnum getCategorieTypeAttribut(Attribut attribut) {
        if (attribut.getType().toLowerCase(Locale.ENGLISH).equals("string")) {
            return TypeAttributEnum.STRING;
        }
        if (attribut.getType().toLowerCase(Locale.ENGLISH).equals("int")
                || attribut.getType().toLowerCase(Locale.ENGLISH).equals("integer")
                || attribut.getType().toLowerCase(Locale.ENGLISH).equals("short")) {
            return TypeAttributEnum.ENTIER;
        }
        if (attribut.getType().toLowerCase(Locale.ENGLISH).equals("float")
                || attribut.getType().toLowerCase(Locale.ENGLISH).equals("double")
                || attribut.getType().toLowerCase(Locale.ENGLISH).equals("long")) {
            return TypeAttributEnum.REEL;
        }
        return TypeAttributEnum.OBJET;
    }
    /**
     * obtenir les contraintes possible pour un type donné
     * @param type pour le quel on veut savoir les contraintes possibles
     * @return les contraintes qui peuvent s'appliquer pour un type donné
     */
    public static String[] getContraintesPossibleForType(String type) {

        if (type.toLowerCase(Locale.ENGLISH).equals("string")) {
            return AbstractContrainte.stringContraintes;
        }
        if (type.toLowerCase(Locale.ENGLISH).equals("int")
                || type.toLowerCase(Locale.ENGLISH).equals("integer")
                || type.toLowerCase(Locale.ENGLISH).equals("short")) {
            return AbstractContrainte.integerContraintes;
        }
        if (type.toLowerCase(Locale.ENGLISH).equals("float")
                || type.toLowerCase(Locale.ENGLISH).equals("double")
                || type.toLowerCase(Locale.ENGLISH).equals("long")) {
            return AbstractContrainte.integerContraintes;
        }
        return AbstractContrainte.objectContraintes;
    }

    /**
     * getter des contraintes
     * @return liste des contraintes
     */
    public List<AbstractContrainte> getContraintes() {
        if (contraintes == null) {
            contraintes = new ArrayList<AbstractContrainte>();
        }
        return contraintes;
    }

    /**
     * Obtenir la liste des contraintes sous form de chaine de caractere
     * exemple : "min : 10, max : 1000"
     * @return liste de contrainte sous forme de chaine de caractere
     */
    public String getContraintesAsString() {
        if (contraintes == null || contraintes.isEmpty()) {
            return "";
        }
        String response = contraintes.get(0).toString();
        Iterator<AbstractContrainte> it = contraintes.iterator();
        it.next();
        while (it.hasNext()) {
            AbstractContrainte contrainte = it.next();
            response += ", " + contrainte.toString();
        }
        return response;
    }

    /**
     * getter du nom de l attribut
     * @return nom de l attribut
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom de l attribut
     * @param nom de l attribut
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter du typ de l attribut
     * @return type de l attribut
     */
    public String getType() {
        return type;
    }

    /**
     * setter du type de l attribut
     * @param type de l attribut
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return (nom + " : " + type);
    }
}
