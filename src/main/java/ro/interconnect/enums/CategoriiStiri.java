/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.enums;

/**
 *
 * @author Alexis
 */
public enum CategoriiStiri {
    GENERAL_NEWS("STIRI GENERALE"), NEWS_ALERT("ALERTA"), 
    ADMINISTRATIE_LOCALA("ADMINISTRATIE LOCALA"), ADMINISTRATIE_NATIONALA("ADMINISTRATIE NATIONALA");
    
    private String categorieStiri;

    private CategoriiStiri(String categorieStiri) {
        this.categorieStiri = categorieStiri;
    }

    public String getCategorieStiri() {
        return categorieStiri;
    }    
    
}
