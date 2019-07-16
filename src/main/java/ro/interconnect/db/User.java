/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.db;

import java.sql.Timestamp;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
public class User {
    private int id;
    private String userName;
    private String password;
    private boolean enabled;
    private RoluriUtilizatori role;
    private Timestamp ultimaNotificare;
    private String numePersoana;
    private String prenumePersoana;
    private int varsta;
    private String judet;
    private String localitate;
    private String adresa;
    private String sex;

    public User() {
    }

    public User(int id, String userName, String password, boolean enabled, RoluriUtilizatori role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RoluriUtilizatori getRole() {
        return role;
    }

    public void setRole(RoluriUtilizatori role) {
        this.role = role;
    }

    public Timestamp getUltimaNotificare() {
        return ultimaNotificare;
    }

    public void setUltimaNotificare(Timestamp ultimaNotificare) {
        this.ultimaNotificare = ultimaNotificare;
    }

    public String getNumePersoana() {
        return numePersoana;
    }

    public void setNumePersoana(String numePersoana) {
        this.numePersoana = numePersoana;
    }

    public String getPrenumePersoana() {
        return prenumePersoana;
    }

    public void setPrenumePersoana(String prenumePersoana) {
        this.prenumePersoana = prenumePersoana;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
