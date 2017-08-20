/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.model.contrainte;

/**
 * @Description RangeContrainte
 * @version 1.0-SNAPSHOT
 * @date 18 févr. 2011
 * @author rabbah
 */
public class RangeContrainte extends AbstractContrainte{

    private String minValue;
    private String maxValue;

    public RangeContrainte() {
        this.nom = "range";
    }



    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
        if (minValue == null || Float.valueOf(minValue) > Float.valueOf(maxValue)) {
            this.minValue = maxValue;
        }
        this.updateValue();
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
        if (maxValue == null || Float.valueOf(maxValue) < Float.valueOf(minValue)) {
            this.maxValue = minValue;
        }
        this.updateValue();
    }

    private void updateValue() {
        valeur = minValue + ".." + maxValue;
    }
}
