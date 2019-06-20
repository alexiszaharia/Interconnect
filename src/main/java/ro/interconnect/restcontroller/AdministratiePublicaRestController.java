/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

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
import ro.interconnect.beans.RestResponse;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.StireDao;
import ro.interconnect.dao.UserDao;
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
    
    @RequestMapping(value = "/adaugare_referendum", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareReferendum(@RequestParam(value = "nume_user") String numeUser, 
            @RequestParam(value = "intrebare") String intrebare, 
            @RequestParam(value = "data") String data, 
            @RequestParam(value = "optiuni") String optiuni) {
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
        
        User user = userDao.getUser(numeUser);
        
        //definire referendum
        Referendum referendum = new Referendum();
        List<OptiuneReferendum> listaOptiuni = new ArrayList<>();
        
        referendum.setIntrebare(intrebare);
        referendum.setDataReferendumFormatata(dataFormata);
        referendum.setUserCreare(user);
        
        String[] arrayOptiuni = optiuni.split(";");
        for (String optiune: arrayOptiuni) {
            OptiuneReferendum optiuneReferendum = new OptiuneReferendum();
            optiuneReferendum.setTextOptiune(optiune);
            listaOptiuni.add(optiuneReferendum);
        }
        referendum.setListaOptiuni(listaOptiuni);
        
        //definire anunt
        String preview = "Se va desfasura un referendum pe data de " + dataFormata + ".";
        String continut = "Se va desfasura un referendum pe data de " + dataFormata 
                + ". Intrebarea va fi: " + referendum.getIntrebare() 
                + ". Optiunile de raspuns sunt: ";
        boolean prima = true;
        for (OptiuneReferendum optiuneReferendum: referendum.getListaOptiuni()) {
            if (prima) {
                continut += optiuneReferendum.getTextOptiune();
                prima = false;
            } else {
                continut += ", " + optiuneReferendum.getTextOptiune();
            }
        }
        continut += ".";
        
        Stire stire = new Stire();
        stire.setTitluStire("Referendum pe data de " + dataFormata);
        stire.setPreviewStire(preview);
        stire.setTipStire(CategoriiStiri.NEWS_ALERT.getCategorieStiri());
        stire.setContinutStire(continut);
        stire.setAnunt(1);
        stire.setUserPublicare(user.getId());        
              
        
        boolean ok = referendumDao.insertReferendum(referendum);
        ok = stireDao.insertStire(stire);
        
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/adaugare_stire", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareStire(@RequestParam(value = "numeUser") String numeUser, 
            @RequestParam(value = "titluStire") String titluStire, 
            @RequestParam(value = "previewStire") String previewStire, 
            @RequestParam(value = "tipStire") String tipStire, 
            @RequestParam(value = "continutStire") String continutStire, 
            @RequestParam(value = "anuntStire") int anuntStire) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        User user = userDao.getUser(numeUser);
        Stire stire = new Stire();
        stire.setTitluStire(titluStire);
        stire.setPreviewStire(previewStire);
        stire.setTipStire(tipStire);
        stire.setContinutStire(continutStire);
        stire.setAnunt(anuntStire);
        stire.setUserPublicare(user.getId());        
        
        boolean ok = stireDao.insertStire(stire);
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
        }
        
        return raspuns;
    }
}
