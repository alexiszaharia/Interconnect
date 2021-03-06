/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import ro.interconnect.beans.Atasament;

/**
 *
 * @author Alexis
 */
public class Stire {
    private int id;
    private String continutStire;
    private Timestamp dataPublicare;
    private int userPublicare;
    private String tipStire;
    private String previewStire;
    private String titluStire;
    private String dataPublicareFormatata;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private int anunt;
    private List<Atasament> listaAtasamente = new ArrayList<>();

    public Stire() {
    }

    public Stire(int id, String continutStire, Timestamp dataPublicare, int userPublicare, 
            String tipStire, String previewStire, String titluStire, int anunt) {
        this.id = id;
        this.continutStire = continutStire;
        this.dataPublicare = dataPublicare;
        this.userPublicare = userPublicare;
        this.tipStire = tipStire;
        this.previewStire = previewStire;
        this.titluStire = titluStire;
        this.dataPublicareFormatata = sdf.format(dataPublicare);
        this.anunt = anunt;
    }

    public Stire(String continutStire, Timestamp dataPublicare, int userPublicare, 
            String tipStire, String previewStire, String titluStire, int anunt) {
        this.continutStire = continutStire;
        this.dataPublicare = dataPublicare;
        this.userPublicare = userPublicare;
        this.tipStire = tipStire;
        this.previewStire = previewStire;
        this.titluStire = titluStire;
        this.dataPublicareFormatata = sdf.format(dataPublicare);
        this.anunt = anunt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContinutStire() {
        return continutStire;
    }

    public void setContinutStire(String continutStire) {
        this.continutStire = continutStire;
    }

    public Timestamp getDataPublicare() {
        return dataPublicare;
    }

    public void setDataPublicare(Timestamp dataPublicare) {
        this.dataPublicare = dataPublicare;
    }

    public int getUserPublicare() {
        return userPublicare;
    }

    public void setUserPublicare(int userPublicare) {
        this.userPublicare = userPublicare;
    }

    public String getTipStire() {
        return tipStire;
    }

    public void setTipStire(String tipStire) {
        this.tipStire = tipStire;
    }

    public String getPreviewStire() {
        return previewStire;
    }

    public void setPreviewStire(String previewStire) {
        this.previewStire = previewStire;
    }

    public String getTitluStire() {
        return titluStire;
    }

    public void setTitluStire(String titluStire) {
        this.titluStire = titluStire;
    }

    public String getDataPublicareFormatata() {
        return dataPublicareFormatata;
    }

    public void setDataPublicareFormatata(String dataPublicareFormatata) {
        this.dataPublicareFormatata = dataPublicareFormatata;
    }
    
    public void setDataPublicareFormatata() {
        if (this.dataPublicare != null) {
            this.dataPublicareFormatata = sdf.format(this.dataPublicare);
        }
    }

    public int getAnunt() {
        return anunt;
    }

    public void setAnunt(int anunt) {
        this.anunt = anunt;
    }

    public List<Atasament> getListaAtasamente() {
        return listaAtasamente;
    }

    public void setListaAtasamente(List<Atasament> listaAtasamente) {
        this.listaAtasamente = listaAtasamente;
    }
}
