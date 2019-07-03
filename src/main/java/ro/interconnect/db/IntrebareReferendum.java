/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.util.List;

/**
 *
 * @author Alexis
 */
public class IntrebareReferendum {
    private int idIntrebare;
    private String textIntrebare;
    private List<OptiuneReferendum> listaOptiuniReferendum;

    public int getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(int idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getTextIntrebare() {
        return textIntrebare;
    }

    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }

    public List<OptiuneReferendum> getListaOptiuniReferendum() {
        return listaOptiuniReferendum;
    }

    public void setListaOptiuniReferendum(List<OptiuneReferendum> listaOptiuniReferendum) {
        this.listaOptiuniReferendum = listaOptiuniReferendum;
    }
}
