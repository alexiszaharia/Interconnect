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
}
