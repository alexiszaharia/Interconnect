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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.OptiuneReferendum;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.User;

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
        try {
            dataRef = formatRequest.parse(data);
        } catch (ParseException ex) {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
            return raspuns;
        }
        
        User user = userDao.getUser(numeUser);
        Referendum referendum = new Referendum();
        List<OptiuneReferendum> listaOptiuni = new ArrayList<>();
        
        referendum.setIntrebare(intrebare);
        referendum.setDataReferendumFormatata(formatReferendum.format(dataRef));
        referendum.setUserCreare(user);
        
        String[] arrayOptiuni = optiuni.split(";");
        for (String optiune: arrayOptiuni) {
            OptiuneReferendum optiuneReferendum = new OptiuneReferendum();
            optiuneReferendum.setTextOptiune(optiune);
            listaOptiuni.add(optiuneReferendum);
        }
        referendum.setListaOptiuni(listaOptiuni);
        
        
        boolean ok = referendumDao.insertReferendum(referendum);
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
        }
        
        return raspuns;
    }
}
