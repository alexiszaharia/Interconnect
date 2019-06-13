/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.dao.UserDao;
import ro.interconnect.dao.VoturiReferendumDao;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@RestController
public class CetateanRestController {
    @Autowired
    private VoturiReferendumDao voturiReferendumDao;
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value = "/votare_referendum", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('CETATEAN')")
    public RestResponse<Object> votareReferendum(@RequestParam(value = "nume_user") String numeUser, 
            @RequestParam(value = "id_referendum") int idReferendum, 
            @RequestParam(value = "valoare_optiune") int idOptiune) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        User user = userDao.getUser(numeUser);
              
        boolean ok = voturiReferendumDao.insertOptiune(user, idReferendum, idOptiune);
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea referendumului!");
        }
        
        return raspuns;
    }
}