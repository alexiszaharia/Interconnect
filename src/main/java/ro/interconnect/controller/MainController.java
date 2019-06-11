/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexis
 */
@Controller
public class MainController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage() {
        return "home.jsp";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login.jsp";
    }
    
    @RequestMapping(value = "/meniu", method = RequestMethod.GET)
    public String meniu() {
        return "meniu.jsp";
    }
    
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String header() {
        return "header.jsp";
    }
}
