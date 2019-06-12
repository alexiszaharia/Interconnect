/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Alexis
 */
public class Opinie {
    private int id;
    private User userOpinie;
    private Timestamp data;
    private String dataFormatata;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private String comentariu;

    public Opinie() {
    }

    public Opinie(int id, User user, Timestamp data, String comentariu) {
        this.id = id;
        this.userOpinie = user;
        this.data = data;
        this.comentariu = comentariu;
        this.dataFormatata = sdf.format(data);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserOpinie() {
        return userOpinie;
    }

    public void setUserOpinie(User userOpinie) {
        this.userOpinie = userOpinie;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getDataFormatata() {
        return dataFormatata;
    }

    public void setDataFormatata(String dataFormatata) {
        this.dataFormatata = dataFormatata;
    }
    
    public void setDataFormatata() {
        if (this.data != null) {
            this.dataFormatata = sdf.format(this.data);
        }
    }

    public String getComentariu() {
        return comentariu;
    }

    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }
    
    
}
