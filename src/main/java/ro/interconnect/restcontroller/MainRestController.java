/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@RestController
public class MainRestController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bcpe;

    @RequestMapping(value = "/modificare_parola_user", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    public RestResponse<Object> modificareParolaUser(@RequestParam(value = "parolaInitiala") String parolaInitiala,
            @RequestParam(value = "parolaNoua") String parolaNoua,
            @RequestParam(value = "numeUser") String numeUser) {
        RestResponse<Object> raspuns = new RestResponse<>();

        User user = userDao.getUser(numeUser);

        boolean ok = true;
        if (bcpe.matches(parolaInitiala, user.getPassword())) {
            String parolaEncodata = bcpe.encode(parolaNoua);
            ok = userDao.updatePasswordUser(user, parolaEncodata);
            
            if (ok) {
                raspuns.setCodRetur(0);
            } else {
                raspuns.setCodRetur(-1);
                raspuns.setMesajConsola("Eroare la modificarea parolei!");
            }
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajUtilizator("Parola veche nu este corecta!");
            raspuns.setMesajConsola("Eroare la modificarea parolei! Parola veche nu este corecta");
        }

        return raspuns;
    }
}
