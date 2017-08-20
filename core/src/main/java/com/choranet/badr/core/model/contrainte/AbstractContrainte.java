/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.contrainte;

/**
 * @Description AbstractContrainte
 * @version 1.0-SNAPSHOT
 * @date 17 févr. 2011
 * @author rabbah
 */
public abstract class AbstractContrainte {

    public static String[] stringContraintes = {"blank", "creditCard", "email",
        "inList", "matches", "maxSize", "minSize", "notEqual", "nullable",
        "size", "unique", "url"};
    public static String[] integerContraintes = {"max", "min", "nullable",
        "range", "unique"};
    public static String[] floatContraintes = {"max", "min", "nullable",
        "range", "scale", "unique"};
    public static String[] objectContraintes = {"max", "min", "nullable",
        "unique"};


    public enum StringContraintesEnum {
        blank, creditCard, email, inList, matches, maxSize, minSize, notEqual,
        nullable, size, unique, url;
    }

    public enum IntegerContraintesEnum {
        max, min, nullable, range, unique;
    }

    public enum FloatContraintesEnum {
        max, min, nullable, range, scale, unique;
    }

    public enum ObjectContraintesEnum {
        max, min, nullable, unique;
    }

    public static Object creerContrainte(Object contrainte) throws Exception {
        String contrainteName = null;
        if(contrainte instanceof StringContraintesEnum) {
            StringContraintesEnum sce = (StringContraintesEnum) contrainte;
            contrainteName = sce.name();
        } else if (contrainte instanceof IntegerContraintesEnum) {
            IntegerContraintesEnum ice = (IntegerContraintesEnum) contrainte;
            contrainteName = ice.name();
        } else if (contrainte instanceof FloatContraintesEnum) {
            FloatContraintesEnum fce = (FloatContraintesEnum) contrainte;
            contrainteName = fce.name();
        } else if (contrainte instanceof ObjectContraintesEnum) {
            ObjectContraintesEnum oce = (ObjectContraintesEnum) contrainte;
            contrainteName = oce.name();
        } else if (contrainte instanceof String) {
            contrainteName = (String) contrainte;
        } else {
            throw new Exception("Impossible d obtenir une contrainte a partire"
                    + "de l objet : " + contrainte);
        }
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        return cl.loadClass("com.choranet.badr.core.model.contrainte."
                + contrainteName.substring(0, 1).toUpperCase()
                + contrainteName.substring(1)
                + "Contrainte").newInstance();
    }

    
    protected String nom;
    protected String valeur;

    public String getNom() {
        return nom;
    }

    public String getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return (nom + " : " + valeur);
    }
}
