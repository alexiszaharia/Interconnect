/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.interconnect.beans.Atasament;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.dao.InitiativaDao;
import ro.interconnect.dao.OpinieDao;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.dao.VoturiInitiativeDao;
import ro.interconnect.db.Initiativa;
import ro.interconnect.db.Opinie;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Controller
public class ComunController {
    @Autowired
    private ConfigurareDetalii configurareDetalii;
    @Autowired
    private InitiativaDao initiativaDao;
    @Autowired
    private OpinieDao opinieDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private VoturiInitiativeDao voturiInitiativeDao;
    @Autowired
    private ReferendumDao referendumDao;
    
    //--------------------------------- ADMINISTRATIE PUBLICA -----------------------------------
    
    @RequestMapping(value = "/initiative_administratie", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeAdministratiePage(Model model) {
        int paginaCurenta = 1;
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.ADMINISTRATIE_PUBLICA, "", "")).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                1, configurareDetalii.getNrElemPePagina(), RoluriUtilizatori.ADMINISTRATIE_PUBLICA, "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_administratie");
        model.addAttribute("tipInitiativa", "administratie publica");
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/initiative.jsp";
    }
    
    @RequestMapping(value = "/initiative_administratie/{pagina}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeAdministratiePageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta, Model model) {
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.ADMINISTRATIE_PUBLICA, "", "")).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), 
                RoluriUtilizatori.ADMINISTRATIE_PUBLICA, "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_administratie");
        model.addAttribute("tipInitiativa", "administratie publica");
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/initiative.jsp";
    }
    
    @RequestMapping(value = "/initiative_administratie/{pagina}/{continut}&{autor}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeAdministratiePageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta,
            @PathVariable(value = "continut") String paramContinut, 
            @PathVariable(value = "autor") String paramAutor,
            Model model) {
        String continut = (paramContinut == null) ? "" : paramContinut;
        String autor = (paramAutor == null) ? "" : paramAutor;
        
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.ADMINISTRATIE_PUBLICA, continut, autor)).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), 
                RoluriUtilizatori.ADMINISTRATIE_PUBLICA, continut, autor);
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_administratie");
        model.addAttribute("tipInitiativa", "administratie publica");
        model.addAttribute("continut", continut);
        model.addAttribute("autor", autor);
        
        return "comun/initiative.jsp";
    }
    
    //--------------------------------- CETATENI -----------------------------------
    
    @RequestMapping(value = "/initiative_cetateni", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeCetateniPage(Model model) {
        int paginaCurenta = 1;
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.CETATEAN, "", "")).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                1, configurareDetalii.getNrElemPePagina(), RoluriUtilizatori.CETATEAN, "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_cetateni");
        model.addAttribute("tipInitiativa", "cetateni");
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/initiative.jsp";
    }
    
    @RequestMapping(value = "/initiative_cetateni/{pagina}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeCetateniPageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta, Model model) {
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.CETATEAN, "", "")).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), 
                RoluriUtilizatori.CETATEAN, "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_cetateni");
        model.addAttribute("tipInitiativa", "cetateni");
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/initiative.jsp";
    }
    
    @RequestMapping(value = "/initiative_cetateni/{pagina}/{continut}&{autor}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativeCetateniPageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta, 
            @PathVariable(value = "continut") String paramContinut, 
            @PathVariable(value = "autor") String paramAutor,
            Model model) {
        String continut = (paramContinut == null) ? "" : paramContinut;
        String autor = (paramAutor == null) ? "" : paramAutor;
        
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrInitiative = Integer.valueOf(initiativaDao.getNumarInitiative(
                RoluriUtilizatori.CETATEAN, continut, autor)).doubleValue();
        totalPagini = Math.ceil(nrInitiative / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Initiativa> listaInitiative = initiativaDao.getListaInitiativePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), 
                RoluriUtilizatori.CETATEAN, continut, autor);
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaInitiative", listaInitiative);
        model.addAttribute("linkPagina", "initiative_cetateni");
        model.addAttribute("tipInitiativa", "cetateni");
        model.addAttribute("continut", continut);
        model.addAttribute("autor", autor);
        
        return "comun/initiative.jsp";
    }
    
    @RequestMapping(value = "/initiative/detalii_initiativa/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String initiativaSelectataDetaliata(@PathVariable(value = "id") String strId, 
            Principal principal,  
            Model model) {
        int id = Integer.valueOf(strId);
        
        Initiativa initiativa = initiativaDao.getInitiativa(id);
        List<Opinie> listaOpinii = opinieDao.getOpiniiInitiativa(initiativa.getId());
        initiativa.setListaOpinii(listaOpinii);
        
        List<Atasament> listaAtasamente = new ArrayList<>();
        String caleDirector = configurareDetalii.getCaleFisiere() + "\\initiative\\" + initiativa.getId();
        File director = new File(caleDirector);
        if (director.exists()) {
            for (File fisier : director.listFiles()) {
                Atasament atasament = new Atasament();
                atasament.setCale("/initiativa_attachment/" + initiativa.getId() + "/" + fisier.getName());
                atasament.setDenumire(fisier.getName());
                listaAtasamente.add(atasament);
            }
            initiativa.setListaAtasamente(listaAtasamente);
        }
        
        User user = userDao.getUser(principal.getName());
        
        model.addAttribute("initiativa", initiativa);
        model.addAttribute("like", 
                voturiInitiativeDao.getNrVoturiPozitiveNegative(true, initiativa.getId()));
        model.addAttribute("dislike", 
                voturiInitiativeDao.getNrVoturiPozitiveNegative(false, initiativa.getId()));
        model.addAttribute("tipVotUserCurent", 
                voturiInitiativeDao.getModVotareUserPeInitiativa(user.getId(), initiativa.getId()));
        
        return "comun/initiativa_detaliata.jsp";
    }
    
    @RequestMapping(value = "/creare_initiativa", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String pageAdaugareInitiativa() {
        return "comun/adaugare_initiativa.jsp";
    }
    
    //--------------------------------- ISTORIC REFERENDUMURI -----------------------------------
    @RequestMapping(value = "/istoric_referendumuri", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String istoricReferendumuriPage(Model model) {
        int paginaCurenta = 1;
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrReferendumuri = Integer.valueOf(referendumDao.getNrReferendumuriTrecute("", ""))
                .doubleValue();
        totalPagini = Math.ceil(nrReferendumuri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Referendum> listaReferendumuri = referendumDao.getListaReferendumuriTrecutePePagina(
                1, configurareDetalii.getNrElemPePagina(), "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaReferendumuri", listaReferendumuri);
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/istoric_referendumuri.jsp";
    }
    
    @RequestMapping(value = "/istoric_referendumuri/{pagina}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String istoricReferendumuriPageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta, Model model) {
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrReferendumuri = Integer.valueOf(referendumDao.getNrReferendumuriTrecute("", ""))
                .doubleValue();
        totalPagini = Math.ceil(nrReferendumuri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Referendum> listaReferendumuri = referendumDao.getListaReferendumuriTrecutePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), "", "");
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaReferendumuri", listaReferendumuri);
        model.addAttribute("continut", "");
        model.addAttribute("autor", "");
        
        return "comun/istoric_referendumuri.jsp";
    }
    
    @RequestMapping(value = "/istoric_referendumuri/{pagina}/{continut}&{autor}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String istoricReferendumuriPageSelected(
            @PathVariable(value = "pagina") String strPaginaCurenta, 
            @PathVariable(value = "continut") String paramContinut, 
            @PathVariable(value = "autor") String paramAutor,
            Model model) {
        String continut = (paramContinut == null) ? "" : paramContinut;
        String autor = (paramAutor == null) ? "" : paramAutor;
        
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrReferendumuri = Integer.valueOf(referendumDao.getNrReferendumuriTrecute(continut, autor))
                .doubleValue();
        totalPagini = Math.ceil(nrReferendumuri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<Referendum> listaReferendumuri = referendumDao.getListaReferendumuriTrecutePePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina(), continut, autor);
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaReferendumuri", listaReferendumuri);
        model.addAttribute("continut", continut);
        model.addAttribute("autor", autor);
        
        return "comun/istoric_referendumuri.jsp";
    }
    
    @RequestMapping(value = "/istoric_referendumuri/detalii_referendum/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public String referendumSelectatDetaliat(@PathVariable(value = "id") String strId, Model model) {
        int id = Integer.valueOf(strId);
        
        model.addAttribute("idReferendum", id);
        
        return "comun/referendum_detaliat.jsp";
    }
    
    @GetMapping(value = "/initiativa_attachment/{id}/{denumire}.{extensie}")
    @PreAuthorize("hasRole('CETATEAN') or hasRole('ADMINISTRATIE_PUBLICA')")
    public ResponseEntity<InputStreamResource> getAttachmentNews(@PathVariable(value = "id") String strId,
            @PathVariable(value = "denumire") String denumire, 
            @PathVariable(value = "extensie") String extensie) throws IOException {
        String caleFisier = configurareDetalii.getCaleFisiere() + "\\initiative\\" + strId + "\\" 
                + denumire + "." + extensie;
        File file = new File(caleFisier);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length())
                .body(resource);
    }
}
