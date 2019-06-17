/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@RestController
public class AdministratorRestController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bcpe;
    
    @RequestMapping(value = "/adaugare_user", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> adaugareUser(@RequestParam(value = "numeUtilizator") String numeUtilizator, 
            @RequestParam(value = "parola") String parola, 
            @RequestParam(value = "enabled") int enabled, 
            @RequestParam(value = "tipUser") String tipUser) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        User user = new User();
        user.setUserName(numeUtilizator);
        user.setPassword(bcpe.encode(parola));
        user.setEnabled((enabled == 1));
        switch (tipUser) {
            case "ROLE_CETATEAN":
                user.setRole(RoluriUtilizatori.CETATEAN);
                break;
            case "ROLE_ADMINISTRATIE_PUBLICA":
                user.setRole(RoluriUtilizatori.ADMINISTRATIE_PUBLICA);
                break;
            case "ROLE_ADMINISTRATOR":
                user.setRole(RoluriUtilizatori.ADMINISTRATOR);
                break;
        }   
        
        boolean ok = userDao.insertUser(user);
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea userului!");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/activare_dezactivare_user", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> activareDezactivareUser(@RequestParam(value = "enabled") int enabled, 
            @RequestParam(value = "id") int idUser) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        boolean ok = userDao.activareDezactivareUser(enabled, idUser);
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la activarea/dezactivarea userului!");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/modificare_user", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> modificareUser(@RequestParam(value = "numeUtilizator") String numeUtilizator, 
            @RequestParam(value = "parola") String parola, 
            @RequestParam(value = "tipUser") String tipUser, 
            @RequestParam(value = "idUser") int idUser) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        User user = new User();
        user.setId(idUser);
        user.setUserName(numeUtilizator);
        if (!parola.isEmpty()) {
            user.setPassword(bcpe.encode(parola));
        }
        switch (tipUser) {
            case "ROLE_CETATEAN":
                user.setRole(RoluriUtilizatori.CETATEAN);
                break;
            case "ROLE_ADMINISTRATIE_PUBLICA":
                user.setRole(RoluriUtilizatori.ADMINISTRATIE_PUBLICA);
                break;
            case "ROLE_ADMINISTRATOR":
                user.setRole(RoluriUtilizatori.ADMINISTRATOR);
                break;
        }   
        
        boolean ok = true;
        
        if (parola.isEmpty()) {
            ok = userDao.updateUserFaraParola(user);
        } else {
            ok = userDao.updateUserCuParola(user);
        }
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la modificarea userului!");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/stergere_user", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> stergereUser(@RequestParam(value = "id") int idUser) {
        RestResponse<Object> raspuns = new RestResponse<>();
                
        boolean ok = userDao.deleteUser(idUser);
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la stergerea userului!");
        }
        
        return raspuns;
    }
}
