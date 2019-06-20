/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.beans;

import java.util.List;
import ro.interconnect.db.Stire;

/**
 *
 * @author Alexis
 */
public class NotificariResponse {
    private int notificariNoi;
    private List<Stire> listaNotificari;

    public NotificariResponse() {
    }

    public int getNotificariNoi() {
        return notificariNoi;
    }

    public void setNotificariNoi(int notificariNoi) {
        this.notificariNoi = notificariNoi;
    }

    public List<Stire> getListaNotificari() {
        return listaNotificari;
    }

    public void setListaNotificari(List<Stire> listaNotificari) {
        this.listaNotificari = listaNotificari;
    }
}
