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
import ro.interconnect.dao.UserDao;
import ro.interconnect.db.User;

/**
 *
 * @author Alexis
 */
@Controller
public class AdministratorController {
    @Autowired
    private ConfigurareDetalii configurareDetalii;
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value = "/pagina_adaugare_user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String pageAdaugareStire(Model model) {
        return "administrator/adaugare_user.jsp";
    }
    
    @RequestMapping(value = "/pagina_listare_useri", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String newsPage(Model model) {
        int paginaCurenta = 1;
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrStiri = Integer.valueOf(userDao.getNrCetateniAdministratie()).doubleValue();
        totalPagini = Math.ceil(nrStiri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<User> listaUseri = userDao.getListaUseriPePagina(1, configurareDetalii.getNrElemPePagina());
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaUseri", listaUseri);
        
        return "administrator/lista_useri.jsp";
    }
    
    @RequestMapping(value = "/pagina_listare_useri/{pagina}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String newsPageSelected(@PathVariable(value = "pagina") String strPaginaCurenta, Model model) {
        int paginaCurenta = Integer.valueOf(strPaginaCurenta);
        double totalPagini;
        
        double nrElemPePagina = Integer.valueOf(configurareDetalii.getNrElemPePagina()).doubleValue();
        double nrStiri = Integer.valueOf(userDao.getNrCetateniAdministratie()).doubleValue();
        totalPagini = Math.ceil(nrStiri / nrElemPePagina);
        if (totalPagini == 0) {
            totalPagini = 1;
        }
        
        List<User> listaUseri = userDao.getListaUseriPePagina(
                (paginaCurenta - 1) * configurareDetalii.getNrElemPePagina() + 1, 
                paginaCurenta * configurareDetalii.getNrElemPePagina());
        model.addAttribute("paginaCurenta", paginaCurenta);
        model.addAttribute("totalPagini", totalPagini);
        model.addAttribute("previousPage", paginaCurenta - 1);
        model.addAttribute("nextPage", paginaCurenta + 1);
        model.addAttribute("listaUseri", listaUseri);
        
        return "administrator/lista_useri.jsp";
    }
    
    @RequestMapping(value = "/modificare_user/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String detailedNewsSelected(@PathVariable(value = "id") String strId, Model model) {
        int id = Integer.valueOf(strId);
        
        User user = userDao.getUser(id);
        
        model.addAttribute("user", user);
        
        return "administrator/modificare_user.jsp";
    }
}
