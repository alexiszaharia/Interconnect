/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

/**
 *
 * @author Alexis
 */
public class OptiuneReferendum {
    private int idOptiune;
    private String textOptiune;
    private double procentVot;

    public int getIdOptiune() {
        return idOptiune;
    }

    public void setIdOptiune(int idOptiune) {
        this.idOptiune = idOptiune;
    }

    public String getTextOptiune() {
        return textOptiune;
    }

    public void setTextOptiune(String textOptiune) {
        this.textOptiune = textOptiune;
    }

    public double getProcentVot() {
        return procentVot;
    }

    public void setProcentVot(double procentVot) {
        this.procentVot = procentVot;
    }
}
