/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexis
 */
@Controller
public class AdministratiePublicaController {
    @RequestMapping(value = "/page_adaugare_referendum", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public String pageAdaugareReferendum() {
        return "adm_pub/adaugare_referendum.jsp";
    }
}
