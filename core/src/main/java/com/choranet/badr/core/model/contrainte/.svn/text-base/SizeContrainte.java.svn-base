/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.model.contrainte;

/**
 * @Description SizeContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class SizeContrainte extends AbstractContrainte {

    private Integer minValue;
    private Integer maxValue;

    public SizeContrainte() {
        this.nom = "size";
    }


    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
        if (minValue == null || minValue > maxValue) {
            this.minValue = maxValue;
        }
        this.updateValue();
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
        if (maxValue == null || maxValue < minValue) {
            this.maxValue = minValue;
        }
        this.updateValue();
    }

    private void updateValue() {
        valeur = minValue.toString() + ".." + maxValue.toString();
    }
}
