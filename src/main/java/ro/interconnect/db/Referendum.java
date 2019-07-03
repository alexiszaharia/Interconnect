/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import ro.interconnect.beans.Atasament;

/**
 *
 * @author Alexis
 */
public class Referendum {
    private int idReferendum;
    private String prezentare;
    private Date dataReferendum;
    private String dataReferendumFormatata;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private User userCreare;
    private List<IntrebareReferendum> listaIntrebari;
    private double procentParticipare;
    private List<Atasament> listaAtasamente = new ArrayList<>();

    public int getIdReferendum() {
        return idReferendum;
    }

    public void setIdReferendum(int idReferendum) {
        this.idReferendum = idReferendum;
    }

    public String getPrezentare() {
        return prezentare;
    }

    public void setPrezentare(String prezentare) {
        this.prezentare = prezentare;
    }

    public Date getDataReferendum() {
        return dataReferendum;
    }

    public void setDataReferendum(Date dataReferendum) {
        this.dataReferendum = dataReferendum;
    }

    public String getDataReferendumFormatata() {
        return dataReferendumFormatata;
    }

    public void setDataReferendumFormatata(String dataReferendumFormatata) {
        this.dataReferendumFormatata = dataReferendumFormatata;
    }
    
    public void setDataReferendumFormatata() {
        if(this.dataReferendum != null) {
            this.dataReferendumFormatata = sdf.format(this.dataReferendum);
        }
    }

    public User getUserCreare() {
        return userCreare;
    }

    public void setUserCreare(User userCreare) {
        this.userCreare = userCreare;
    }

    public List<IntrebareReferendum> getListaIntrebari() {
        return listaIntrebari;
    }

    public void setListaIntrebari(List<IntrebareReferendum> listaIntrebari) {
        this.listaIntrebari = listaIntrebari;
    }

    public double getProcentParticipare() {
        return procentParticipare;
    }

    public void setProcentParticipare(double procentParticipare) {
        this.procentParticipare = procentParticipare;
    }

    public List<Atasament> getListaAtasamente() {
        return listaAtasamente;
    }

    public void setListaAtasamente(List<Atasament> listaAtasamente) {
        this.listaAtasamente = listaAtasamente;
    }
}
