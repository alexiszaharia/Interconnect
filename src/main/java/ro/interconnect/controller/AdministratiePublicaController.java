/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.dao.StireDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.Stire;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@Controller
public class AdministratiePublicaController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private StireDao stireDao;
    @Autowired
    private ConfigurareDetalii configurare;

    @RequestMapping(value = "/page_adaugare_referendum", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public String pageAdaugareReferendum() {
        return "adm_pub/adaugare_referendum.jsp";
    }

    @RequestMapping(value = "/pagina_adaugare_stire", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public String pageAdaugareStire(Model model) {
        return "adm_pub/adaugare_stire.jsp";
    }

//    @RequestMapping(value = "/adaugare_stire", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
//    public String adaugareStire(@RequestParam(value = "nume_user") String numeUser,
//            @RequestParam(value = "titlu_stire") String titluStire,
//            @RequestParam(value = "preview_stire") String previewStire,
//            @RequestParam(value = "tip_stire") String tipStire,
//            @RequestParam(value = "continut_stire") String continutStire,
//            @RequestParam(value = "checkbox_anunt", required = false) String anuntStire,
//            @RequestParam(value = "files", required = false) MultipartFile[] files,
//            Model model) {
//
//        User user = userDao.getUser(numeUser);
//        Stire stire = new Stire();
//        stire.setTitluStire(titluStire);
//        stire.setPreviewStire(previewStire);
//        stire.setTipStire(tipStire);
//        stire.setContinutStire(continutStire);
//        if (anuntStire != null) {
//            stire.setAnunt(1);
//        } else {
//            stire.setAnunt(0);
//        }
//        stire.setUserPublicare(user.getId());
//
//        boolean ok = stireDao.insertStire(stire);
//
//        if (ok) {
//            if (files != null && files.length != 0) {
//                int idStireInserata = stireDao.getIdStire(titluStire, previewStire);
//
//                if (idStireInserata != 0) {
//                    File director = new File(configurare.getCaleFisiere() + "\\news\\" + idStireInserata);
//                    director.mkdirs();
//
//                    for (MultipartFile fileUpload : files) {
//                        File fisierDestinatie = new File(configurare.getCaleFisiere() + "\\news\\"
//                                + idStireInserata + "\\" + fileUpload.getOriginalFilename());
//                        try {
//                            fileUpload.transferTo(fisierDestinatie);
//                        } catch (IOException ex) {
//                            model.addAttribute("cod_retur", -3);
//                            model.addAttribute("mesaj", "Eroare la adaugarea fisierelor!");
//                            return "adm_pub/adaugare_stire.jsp";
//                        }
//                    }
//
//                } else {
//                    model.addAttribute("cod_retur", -2);
//                    model.addAttribute("mesaj", "Eroare la adaugarea fisierelor!");
//                }
//            }
//            model.addAttribute("cod_retur", 0);
//            model.addAttribute("mesaj", "Stire adaugata!");
//        } else {
//            model.addAttribute("cod_retur", -1);
//            model.addAttribute("mesaj", "Eroare la adaugarea stirii!");
//        }
//
//        return "adm_pub/adaugare_stire.jsp";
//    }
}
