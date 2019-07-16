/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.restcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.interconnect.beans.RestResponse;
import ro.interconnect.config.ConfigurareDetalii;
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
    @Autowired
    private ConfigurareDetalii configurare;

    @RequestMapping(value = "/adaugare_user", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> adaugareUser(@RequestParam(value = "numeUtilizator") String numeUtilizator,
            @RequestParam(value = "parola") String parola,
            @RequestParam(value = "enabled") int enabled,
            @RequestParam(value = "tipUser") String tipUser,
            @RequestParam(value = "numePersoana") String numePersoana,
            @RequestParam(value = "prenumePersoana") String prenumePersoana,
            @RequestParam(value = "varstaPersoana") int varstaPersoana,
            @RequestParam(value = "judet") String judet,
            @RequestParam(value = "localitate") String localitate,
            @RequestParam(value = "adresa") String adresa,
            @RequestParam(value = "sex") String sex) {
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
        user.setNumePersoana(numePersoana);
        user.setPrenumePersoana(prenumePersoana);
        user.setVarsta(varstaPersoana);
        user.setJudet(judet);
        user.setLocalitate(localitate);
        user.setAdresa(adresa);
        user.setSex(sex);

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
            @RequestParam(value = "idUser") int idUser,
            @RequestParam(value = "numePersoana") String numePersoana,
            @RequestParam(value = "prenumePersoana") String prenumePersoana,
            @RequestParam(value = "varstaPersoana") int varstaPersoana,
            @RequestParam(value = "judet") String judet,
            @RequestParam(value = "localitate") String localitate,
            @RequestParam(value = "adresa") String adresa,
            @RequestParam(value = "sex") String sex) {
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
        user.setNumePersoana(numePersoana);
        user.setPrenumePersoana(prenumePersoana);
        user.setVarsta(varstaPersoana);
        user.setJudet(judet);
        user.setLocalitate(localitate);
        user.setAdresa(adresa);
        user.setSex(sex);

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

    @RequestMapping(value = "/import_utilizatori", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RestResponse<Object> importUtilizatori(
            @RequestParam(value = "files_csv", required = false) MultipartFile[] files) {
        RestResponse<Object> raspuns = new RestResponse<>();

        boolean okGeneral = true;

        if (files != null && files.length != 0) {

            for (MultipartFile fileUpload : files) {
                String caleFisier = configurare.getCaleFisiere() + "\\import\\"
                        + fileUpload.getOriginalFilename();

                File fisierDestinatie = new File(caleFisier);
                try {
                    fileUpload.transferTo(fisierDestinatie);
                } catch (IOException ex) {
                    raspuns.setCodRetur(-1);
                    raspuns.setMesajUtilizator("Eroare la adaugarea fisierelor in storage!");
                    return raspuns;
                }

                String line = "";

                try (BufferedReader br = new BufferedReader(new FileReader(caleFisier))) {

                    while ((line = br.readLine()) != null) {
                        String[] splitUser = line.split(",");
                        if (splitUser.length != 11) {
                            okGeneral = false;
                            continue;
                        }

                        User user = new User();
                        user.setUserName(splitUser[0]);
                        user.setPassword(bcpe.encode(splitUser[1]));
                        user.setEnabled((Integer.parseInt(splitUser[2]) == 1));
                        switch (splitUser[3]) {
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
                        user.setNumePersoana(splitUser[4]);
                        user.setPrenumePersoana(splitUser[5]);
                        user.setVarsta(Integer.parseInt(splitUser[6]));
                        user.setJudet(splitUser[7]);
                        user.setLocalitate(splitUser[8]);
                        user.setAdresa(splitUser[9]);
                        user.setSex(splitUser[10]);

                        boolean ok = userDao.insertUser(user);
                        if (ok == false) {
                            okGeneral = false;
                        }
                    }

                } catch (IOException ex) {
                    raspuns.setCodRetur(-2);
                    raspuns.setMesajUtilizator("Eroare la citirea fisierelor de import!");
                    return raspuns;
                }
            }
        }
        
        if (okGeneral) {
            raspuns.setCodRetur(0);
            raspuns.setMesajUtilizator("Utilizatori adaugati!");
        } else {
            raspuns.setCodRetur(-3);
            raspuns.setMesajUtilizator("Probleme la adaugarea utilizatorilor!");
        }

        return raspuns;
    }
}
