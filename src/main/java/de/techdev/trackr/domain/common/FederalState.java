package de.techdev.trackr.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Moritz Schulze
 */
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum FederalState {

    BADEN_WUERTTEMBERG("Baden-Württemberg"), BAYERN("Bayern"), BERLIN("Berlin"), BRANDENBURG("Brandenburg"), BREMEN("Bremen"),
    HAMBURG("Hamburg"), HESSEN("Hessen"), MECKLENBURG_VORPOMMERN("Mecklenburg-Vorpommern"), NIEDERSACHSEN("Niedersachsen"), NORDRHEIN_WESTFALEN("Nordrhein-Westfalen"),
    RHEINLAND_PFALZ("Rheinland-Pfalz"), SAARLAND("Saarland"), SACHSEN("Sachsen"), SACHSEN_ANHALT("Sachsen-Anhalt"), SCHLESWIG_HOLSTEIN("Schleswig-Holstein"), THUERINGEN("Thüringen");

    private String state;

    FederalState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return this.toString();
    }
}
