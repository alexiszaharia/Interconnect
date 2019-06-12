/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.dao.StireDao;
import ro.interconnect.db.Stire;

/**
 *
 * @author Alexis
 */
@Controller
public class CetateanController {
    @Autowired
    ConfigurareDetalii configurareDetalii;
    @Autowired
    StireDao stireDao;
    
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
        
        model.addAttribute("stire", stire);
        
        return "cetatean/detailed_news.jsp";
    }
}
