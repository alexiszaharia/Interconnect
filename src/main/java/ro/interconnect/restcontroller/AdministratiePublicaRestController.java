/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.StireDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.IntrebareReferendum;
import ro.interconnect.db.OptiuneReferendum;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.Stire;
import ro.interconnect.db.User;
import ro.interconnect.enums.CategoriiStiri;

/**
 *
 * @author Alexis
 */
@RestController
public class AdministratiePublicaRestController {

    @Autowired
    private ReferendumDao referendumDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StireDao stireDao;
    @Autowired
    private ConfigurareDetalii configurare;

    @RequestMapping(value = "/adaugare_referendum", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareReferendum(@RequestParam(value = "nume_user") String numeUser,
            @RequestParam(value = "intrebari") String intrebari,
            @RequestParam(value = "data_referendum") String data,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "prezentare_referendum") String prezentare) {
        RestResponse<Object> raspuns = new RestResponse<>();

        SimpleDateFormat formatRequest = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatReferendum = new SimpleDateFormat("dd.MM.yyyy");
        Date dataRef;
        String dataFormata;
        try {
            dataRef = formatRequest.parse(data);
        } catch (ParseException ex) {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
            return raspuns;
        }
        dataFormata = formatReferendum.format(dataRef);
        
        if (referendumDao.existaReferendum(dataFormata)) {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Exista deja referendum la data furnizata!");
            raspuns.setMesajUtilizator("Exista deja referendum la data furnizata!");
            
            return raspuns;
        }

        User user = userDao.getUser(numeUser);

        //definire referendum
        Referendum referendum = new Referendum();
        referendum.setPrezentare(prezentare);
        referendum.setDataReferendumFormatata(dataFormata);
        referendum.setUserCreare(user);

        //pregatire intrebari
        List<IntrebareReferendum> listaIntrebari = new ArrayList<>();
        String[] arrayIntrebari = intrebari.split("<>");
        for (String intrebare : arrayIntrebari) {
            IntrebareReferendum intrebareReferendum = new IntrebareReferendum();
            List<OptiuneReferendum> listaOptiuni = new ArrayList<>();
            String[] splitIntrebare = intrebare.split(";");
            for (int i = 0; i < splitIntrebare.length; i++) {
                if (i == 0) {
                    intrebareReferendum.setTextIntrebare(splitIntrebare[i]);
                } else {
                    OptiuneReferendum optiuneReferendum = new OptiuneReferendum();
                    optiuneReferendum.setTextOptiune(splitIntrebare[i]);
                    listaOptiuni.add(optiuneReferendum);
                }
            }
            intrebareReferendum.setListaOptiuniReferendum(listaOptiuni);
            listaIntrebari.add(intrebareReferendum);
        }

        referendum.setListaIntrebari(listaIntrebari);

        //definire anunt
        String preview = "Se va desfasura un referendum pe data de " + dataFormata + ".";
        String continut = "Se va desfasura un referendum pe data de " + dataFormata
                + " cu urmatoarea lista de intrebari:";

        for (IntrebareReferendum intrebareReferendum : referendum.getListaIntrebari()) {
            continut += "<br/><br/>" + intrebareReferendum.getTextIntrebare()
                    + " cu optiunile: ";
            for (int i = 0; i < intrebareReferendum.getListaOptiuniReferendum().size(); i++) {
                if (i == 0) {
                    continut += intrebareReferendum.getListaOptiuniReferendum().get(i).getTextOptiune();
                } else {
                    continut += ", " + intrebareReferendum.getListaOptiuniReferendum().get(i).getTextOptiune();
                }
            }
        }

        continut += "<br/><br/>" + referendum.getPrezentare();

        Stire stire = new Stire();
        stire.setTitluStire("Referendum pe data de " + dataFormata);
        stire.setPreviewStire(preview);
        stire.setTipStire(CategoriiStiri.NEWS_ALERT.getCategorieStiri());
        stire.setContinutStire(continut);
        stire.setAnunt(1);
        stire.setUserPublicare(user.getId());

        boolean ok = referendumDao.insertReferendum(referendum, files);
        if (ok) {
            ok = stireDao.insertStire(stire);
        }

        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
        }

        return raspuns;
    }

//    @RequestMapping(value = "/adaugare_stire", method = RequestMethod.POST, 
//            produces = "application/json; charset=UTF-8")
//    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
//    public RestResponse<Object> adaugareStire(@RequestParam(value = "numeUser") String numeUser, 
//            @RequestParam(value = "titluStire") String titluStire, 
//            @RequestParam(value = "previewStire") String previewStire, 
//            @RequestParam(value = "tipStire") String tipStire, 
//            @RequestParam(value = "continutStire") String continutStire, 
//            @RequestParam(value = "anuntStire") int anuntStire) {
//        RestResponse<Object> raspuns = new RestResponse<>();
//                
//        User user = userDao.getUser(numeUser);
//        Stire stire = new Stire();
//        stire.setTitluStire(titluStire);
//        stire.setPreviewStire(previewStire);
//        stire.setTipStire(tipStire);
//        stire.setContinutStire(continutStire);
//        stire.setAnunt(anuntStire);
//        stire.setUserPublicare(user.getId());        
//        
//        boolean ok = stireDao.insertStire(stire);
//        
//        if (ok) {
//            raspuns.setCodRetur(0);
//        } else {
//            raspuns.setCodRetur(-1);
//            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
//        }
//        
//        return raspuns;
//    }
    @RequestMapping(value = "/adaugare_stire", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareStire(@RequestParam(value = "nume_user") String numeUser,
            @RequestParam(value = "titlu_stire") String titluStire,
            @RequestParam(value = "preview_stire") String previewStire,
            @RequestParam(value = "tip_stire") String tipStire,
            @RequestParam(value = "continut_stire") String continutStire,
            @RequestParam(value = "checkbox_anunt", required = false) String anuntStire,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        RestResponse<Object> raspuns = new RestResponse<>();

        User user = userDao.getUser(numeUser);
        Stire stire = new Stire();
        stire.setTitluStire(titluStire);
        stire.setPreviewStire(previewStire);
        stire.setTipStire(tipStire);
        stire.setContinutStire(continutStire);
        if (anuntStire != null) {
            stire.setAnunt(1);
        } else {
            stire.setAnunt(0);
        }
        stire.setUserPublicare(user.getId());

        boolean ok = stireDao.insertStire(stire);

        if (ok) {
            if (files != null && files.length != 0) {
                int idStireInserata = stireDao.getIdStire(titluStire, previewStire);

                if (idStireInserata != 0) {
                    File director = new File(configurare.getCaleFisiere() + "\\news\\" + idStireInserata);
                    director.mkdirs();

                    for (MultipartFile fileUpload : files) {
                        File fisierDestinatie = new File(configurare.getCaleFisiere() + "\\news\\"
                                + idStireInserata + "\\" + fileUpload.getOriginalFilename());
                        try {
                            fileUpload.transferTo(fisierDestinatie);
                        } catch (IOException ex) {
                            raspuns.setCodRetur(-3);
                            raspuns.setMesajUtilizator("Eroare la adaugarea fisierelor!");
                            return raspuns;
                        }
                    }

                } else {
                    raspuns.setCodRetur(-2);
                    raspuns.setMesajUtilizator("Eroare la adaugarea fisierelor!");
                }
            }
            raspuns.setCodRetur(0);
            raspuns.setMesajUtilizator("Stire adaugata!");
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajUtilizator("Eroare la adaugarea stirii!");
        }

        return raspuns;
    }
}
