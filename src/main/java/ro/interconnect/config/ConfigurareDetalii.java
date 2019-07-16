/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.config;

/**
 *
 * @author Alexis
 */
public class ConfigurareDetalii {
    private int nrElemPePagina;
    private String caleFisiere;

    public ConfigurareDetalii() {
    }

    public int getNrElemPePagina() {
        return nrElemPePagina;
    }

    public void setNrElemPePagina(int nrElemPePagina) {
        this.nrElemPePagina = nrElemPePagina;
    }

    public String getCaleFisiere() {
        return caleFisiere;
    }

    public void setCaleFisiere(String caleFisiere) {
        this.caleFisiere = caleFisiere;
    }
    
    
}
