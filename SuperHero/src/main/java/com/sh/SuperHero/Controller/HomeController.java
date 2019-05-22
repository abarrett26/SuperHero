/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Controller;

import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Service.Response;
import com.sh.SuperHero.Service.SHService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 *
 * @author alexbarrett
 */
@Controller
public class HomeController {

    @Autowired
    SHService service;

    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        String toReturn = "";
 
       Response<List<Sighting>> tenResponse = service.get10Sightings();
       if (!tenResponse.isSuccess()) {
           toReturn = "error";
       } else {
 
           model.addAttribute("sighting", tenResponse.getResponseData());
           toReturn = "/home";
 
       }
       return toReturn;
   }

}
