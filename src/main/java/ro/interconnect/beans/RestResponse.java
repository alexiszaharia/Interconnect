/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.beans;

/**
 *
 * @author Alexis
 */
public class RestResponse<T> {
    private int codRetur;
    private String mesajUtilizator;
    private String mesajConsola;
    private T objectResponse;

    public int getCodRetur() {
        return codRetur;
    }

    public void setCodRetur(int codRetur) {
        this.codRetur = codRetur;
    }

    public String getMesajUtilizator() {
        return mesajUtilizator;
    }

    public void setMesajUtilizator(String mesajUtilizator) {
        this.mesajUtilizator = mesajUtilizator;
    }

    public String getMesajConsola() {
        return mesajConsola;
    }

    public void setMesajConsola(String mesajConsola) {
        this.mesajConsola = mesajConsola;
    }

    public T getObjectResponse() {
        return objectResponse;
    }

    public void setObjectResponse(T objectResponse) {
        this.objectResponse = objectResponse;
    }
}
