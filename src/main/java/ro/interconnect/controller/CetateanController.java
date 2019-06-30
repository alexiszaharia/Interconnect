/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import ro.interconnect.beans.Atasament;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.dao.ReferendumDao;
import ro.interconnect.dao.StireDao;
import ro.interconnect.dao.UserDao;
import ro.interconnect.dao.VoturiReferendumDao;
import ro.interconnect.db.OptiuneReferendum;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.Stire;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@Controller
public class CetateanController {

    @Autowired
    private ConfigurareDetalii configurareDetalii;
    @Autowired
    private StireDao stireDao;
    @Autowired
    private ReferendumDao referendumDao;
    @Autowired
    private VoturiReferendumDao voturiReferendumDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN')")
    public String newsPage(Model model) {
        int paginaCurenta = 1;
        double totalPagini;

        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrStiri = Integer.valueOf(stireDao.getNumarStiri()).doubleValue();
        totalPagini = Math.ceil(nrStiri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }

        List<Stire> listaStiri = stireDao.getListaStiriPePagina(1, configurareDetalii.getNrElemPePagina());
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaStiri", listaStiri);

        return "cetatean/news.jsp";
    }

    @RequestMapping(value = "/news/{pagina}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN')")
    public String newsPageSelected(@PathVariable(value = "pagina") String strPaginaCurenta, Model model) {
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;

        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrStiri = Integer.valueOf(stireDao.getNumarStiri()).doubleValue();
        totalPagini = Math.ceil(nrStiri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }

        List<Stire> listaStiri = stireDao.getListaStiriPePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1,
                paginaCurenta * configurareDetalii.getNrElemPePagina());
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaStiri", listaStiri);

        return "cetatean/news.jsp";
    }

    @RequestMapping(value = "/news/detalii_news/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN')")
    public String detailedNewsSelected(@PathVariable(value = "id") String strId, Model model) {
        int id = Integer.valueOf(strId);

        Stire stire = stireDao.getStire(id);

        List<Atasament> listaAtasamente = new ArrayList<>();
        String caleDirector = configurareDetalii.getCaleFisiere() + "\\news\\" + stire.getId();
        File director = new File(caleDirector);
        if (director.exists()) {
            for (File fisier : director.listFiles()) {
                Atasament atasament = new Atasament();
                atasament.setCale("/news_attachment/" + stire.getId() + "/" + fisier.getName());
                atasament.setDenumire(fisier.getName());
                listaAtasamente.add(atasament);
            }
            stire.setListaAtasamente(listaAtasamente);
        }

        model.addAttribute("stire", stire);

        return "cetatean/detailed_news.jsp";
    }

    @RequestMapping(value = "/pagina_vot_referendum", method = RequestMethod.GET)
    @PreAuthorize("hasRole('CETATEAN')")
    public String paginaVotareReferendum(Principal principal, Model model) {
        Referendum referendum = null;
        User userCurent = userDao.getUser(principal.getName());
        boolean referendumActiv = referendumDao.verificareReferendumActiv();
        boolean oreActive = false;
        boolean votatDeja = true;

        if (referendumActiv) {

            referendum = referendumDao.getReferendumActiv();
            referendum.setProcentParticipare(voturiReferendumDao.getProcentPrezentaReferendum(
                    referendum.getIdReferendum()));
            for (OptiuneReferendum optiuneReferendum : referendum.getListaOptiuni()) {
                optiuneReferendum.setProcentVot(
                        voturiReferendumDao.getProcentVotOptiune(
                                optiuneReferendum.getIdOptiune(), referendum.getIdReferendum()));
            }

            Calendar calendar = Calendar.getInstance();
            int ora = calendar.get(Calendar.HOUR_OF_DAY);
            if (ora >= 7 && ora < 21) {
                oreActive = true;
            } else {
                oreActive = false;
            }

            votatDeja = referendumDao.referendumVotatDeUtilizator(userCurent, referendum);
        }

        model.addAttribute("referendumActiv", referendumActiv);
        model.addAttribute("oreActive", oreActive);
        model.addAttribute("votatDeja", votatDeja);
        model.addAttribute("referendum", referendum);

        return "cetatean/votare_referendum.jsp";
    }

//    @GetMapping(value = "/news_atachment/{id}/{denumire}",
//            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    @PreAuthorize("hasRole('CETATEAN')")
//    public @ResponseBody
//    byte[] getAttachmentNews(@PathVariable(value = "id") String strId,
//            @PathVariable(value = "denumire") String denumire) throws IOException {
//        String caleFisier = configurareDetalii.getCaleFisiere() + "\\news\\" + strId + "\\" + denumire;
//        System.out.println(caleFisier);
//        InputStream in = getClass().getResourceAsStream(caleFisier);
//        File file = new File(caleFisier);
//        if (file.exists()) {
//            System.out.println("gasit");
//            return IOUtils.toByteArray(in);
//        } else {
//            System.out.println("negasit");
//            return null;
//        }
//    }
    @GetMapping(value = "/news_attachment/{id}/{denumire}.{extensie}")
    @PreAuthorize("hasRole('CETATEAN')")
    public ResponseEntity<InputStreamResource> getAttachmentNews(@PathVariable(value = "id") String strId,
            @PathVariable(value = "denumire") String denumire, 
            @PathVariable(value = "extensie") String extensie) throws IOException {
        String caleFisier = configurareDetalii.getCaleFisiere() + "\\news\\" + strId + "\\" 
                + denumire + "." + extensie;
        File file = new File(caleFisier);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length())
                .body(resource);
    }
}
