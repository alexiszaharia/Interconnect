/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class Referendum {
    private int idReferendum;
    private String intrebare;
    private Date dataReferendum;
    private String dataReferendumFormatata;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private User userCreare;
    private List<OptiuneReferendum> listaOptiuni;
    private double procentParticipare;

    public int getIdReferendum() {
        return idReferendum;
    }

    public void setIdReferendum(int idReferendum) {
        this.idReferendum = idReferendum;
    }

    public String getIntrebare() {
        return intrebare;
    }

    public void setIntrebare(String intrebare) {
        this.intrebare = intrebare;
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

    public List<OptiuneReferendum> getListaOptiuni() {
        return listaOptiuni;
    }

    public void setListaOptiuni(List<OptiuneReferendum> listaOptiuni) {
        this.listaOptiuni = listaOptiuni;
    }

    public double getProcentParticipare() {
        return procentParticipare;
    }

    public void setProcentParticipare(double procentParticipare) {
        this.procentParticipare = procentParticipare;
    }
}
