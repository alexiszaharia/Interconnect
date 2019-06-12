/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class Initiativa {
    private int id;
    private String titluInitiativa;
    private String textInitiativa;
    private User userPublicare;
    private Timestamp dataPublicare;
    private String dataPublicareFormatata;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private List<Opinie> listaOpinii;

    public Initiativa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitluInitiativa() {
        return titluInitiativa;
    }

    public void setTitluInitiativa(String titluInitiativa) {
        this.titluInitiativa = titluInitiativa;
    }

    public String getTextInitiativa() {
        return textInitiativa;
    }

    public void setTextInitiativa(String textInitiativa) {
        this.textInitiativa = textInitiativa;
    }

    public User getUserPublicare() {
        return userPublicare;
    }

    public void setUserPublicare(User userPublicare) {
        this.userPublicare = userPublicare;
    }

    public Timestamp getDataPublicare() {
        return dataPublicare;
    }

    public void setDataPublicare(Timestamp dataPublicare) {
        this.dataPublicare = dataPublicare;
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

    public List<Opinie> getListaOpinii() {
        return listaOpinii;
    }

    public void setListaOpinii(List<Opinie> listaOpinii) {
        this.listaOpinii = listaOpinii;
    }
    
    
}
