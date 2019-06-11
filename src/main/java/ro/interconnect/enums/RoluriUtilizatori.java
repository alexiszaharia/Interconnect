/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.enums;

/**
 *
 * @author Alexis
 */
public enum RoluriUtilizatori {
    CETATEAN("ROLE_CETATEAN"), ADMINISTRATIE_PUBLICA("ROLE_ADMINISTRATIE_PUBLICA"), 
    ADMINISTRATOR("ROLE_ADMINISTRATOR");
    
    private String rol;

    private RoluriUtilizatori(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
