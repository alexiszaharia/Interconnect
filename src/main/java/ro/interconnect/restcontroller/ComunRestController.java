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
import ro.interconnect.dao.InitiativaDao;
import ro.interconnect.dao.OpinieDao;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.dao.VoturiInitiativeDao;
import ro.interconnect.dao.VoturiReferendumDao;
import ro.interconnect.db.OptiuneReferendum;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@RestController
public class ComunRestController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OpinieDao opinieDao;
    @Autowired
    private VoturiInitiativeDao voturiInitiativeDao;
    @Autowired
    private InitiativaDao initiativaDao;
    @Autowired
    private ReferendumDao referendumDao;
    @Autowired
    private VoturiReferendumDao voturiReferendumDao;
    
    @RequestMapping(value = "/adaugare_opinie_initiativa", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareOpinieInitiativa(@RequestParam(value = "nume_user") String numeUser, 
            @RequestParam(value = "id_initiativa") int idInitiativa, 
            @RequestParam(value = "comentariu") String comentariu) {
        RestResponse<Object> raspuns = new RestResponse<>();
        User user = userDao.getUser(numeUser);
        
        boolean ok = opinieDao.adaugareOpinieLaInitiativa(user.getId(), idInitiativa, comentariu);
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea initiativei");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/modificare_like_initiativa", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> modificareLikeInitiativa(@RequestParam(value = "nume_user") String numeUser, 
            @RequestParam(value = "id_initiativa") int idInitiativa, 
            @RequestParam(value = "like") int like) {
        RestResponse<Object> raspuns = new RestResponse<>();
        User user = userDao.getUser(numeUser);
        
        int statusVotare = voturiInitiativeDao.getModVotareUserPeInitiativa(
                user.getId(), idInitiativa);
        
        boolean ok = false;
        
        switch(statusVotare) {
            case -1: //fara vot
                ok = voturiInitiativeDao.adaugareVotLaInitiativa(user.getId(), idInitiativa, like);
                break;
            case 0: //deja vot dislike
                if (like == 0) {
                    ok = voturiInitiativeDao.scoatereVotLaInitiativa(user.getId(), idInitiativa);
                } else if (like == 1) {
                    ok = voturiInitiativeDao.modificareVotLaInitiativa(user.getId(), idInitiativa, like);
                }
                break;
            case 1: //deja vot like
                if (like == 0) {
                    ok = voturiInitiativeDao.modificareVotLaInitiativa(user.getId(), idInitiativa, like);
                } else if (like == 1) {
                    ok = voturiInitiativeDao.scoatereVotLaInitiativa(user.getId(), idInitiativa);
                }
                break;
        }
        
        
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la modificarea like-ului");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/adaugare_initiativa", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Object> adaugareInitiativa(@RequestParam(value = "nume_user") String numeUser, 
            @RequestParam(value = "titlu") String titlu, 
            @RequestParam(value = "continut") String continut) {
        RestResponse<Object> raspuns = new RestResponse<>();
        User user = userDao.getUser(numeUser);
        
        boolean ok = initiativaDao.adaugareInitiativa(user.getId(), titlu, continut);
        if (ok) {
            raspuns.setCodRetur(0);
        } else {
            raspuns.setCodRetur(-1);
            raspuns.setMesajConsola("Eroare la adaugarea initiativei");
        }
        
        return raspuns;
    }
    
    @RequestMapping(value = "/statistica_referendum", method = RequestMethod.POST, 
            produces = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public RestResponse<Referendum> getStatisticaReferendum(
            @RequestParam(value = "id_referendum") int idReferendum) {
        RestResponse<Referendum> raspuns = new RestResponse<>();
        Referendum referendum = new Referendum();
        referendum = referendumDao.getReferendum(idReferendum);
        referendum.setProcentParticipare(voturiReferendumDao.getProcentPrezentaReferendum(idReferendum));
        for(OptiuneReferendum optiuneReferendum: referendum.getListaOptiuni()) {
            optiuneReferendum.setProcentVot(
                    voturiReferendumDao.getProcentVotOptiune(
                            optiuneReferendum.getIdOptiune(), idReferendum));
        }
        
        raspuns.setCodRetur(0);
        raspuns.setObjectResponse(referendum);
        
        return raspuns;
    }
}
