/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Controller;

import com.sh.SuperHero.Dtos.AddSuperHeroViewModel;
import com.sh.SuperHero.Dtos.EditSuperHeroViewModel;
import com.sh.SuperHero.Dtos.Organization;
import com.sh.SuperHero.Dtos.SuperHero;
import com.sh.SuperHero.Service.Response;
import com.sh.SuperHero.Service.SHService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author alexbarrett
 */
@Controller
public class SuperHeroController {

    @Autowired
    SHService service;

    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<SuperHero>> violations = new HashSet();

    @GetMapping("superHero")
    public String displaySuperHeroes(Model model) {
        Response<List<SuperHero>> response = service.getAllSuperHeroes();
        Response<List<Organization>> organizations = service.getAllOrganizations();
        model.addAttribute("superHero", response.getResponseData());
        model.addAttribute("organization", organizations.getResponseData());
        return "superHero";
        
    }

    @GetMapping("displaySuperHero")
    public String displayCharacterById(Integer superHeroId, Model model) {
        Response<SuperHero> response = service.getSuperHeroById(superHeroId);
        model.addAttribute("superHero", response.getResponseData());
        return "displaySuperHero";
    }

    @PostMapping("addSuperHero")
    public String addSuperHero(AddSuperHeroViewModel vm, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getSuperHeroes());
        String toReturn = "";
        if (violations.isEmpty()) {
            List<Organization> selectedOrganizations = new ArrayList<Organization>();
            Integer[] selectedId = vm.getSelectedOrganizationIds();
            if (selectedId != null) {
                for (int i = 0; i < vm.getSelectedOrganizationIds().length; i++) {
                    int organizationId = selectedId[i];
                    Response<Organization> organizationResponse = service.getOrganizationById(organizationId);
                    selectedOrganizations.add(organizationResponse.getResponseData());
                }
            }
            vm.getSuperHeroes().setOrganizations(selectedOrganizations);
            Response<SuperHero> addResponse = service.addSuperHero(vm.getSuperHeroes());
        } else {
            model.addAttribute("superHeroName", vm.getSuperHeroes().getSuperHeroName());
            model.addAttribute("description", vm.getSuperHeroes().getDescription());
            model.addAttribute("superPower", vm.getSuperHeroes().getSuperPower());
            model.addAttribute("organization", vm.getSuperHeroes().getOrganizations());
        }
        model.addAttribute("errors", violations);
        toReturn = displaySuperHeroes(model);
        return toReturn;
    }
    

    @GetMapping("editSuperHero/{superHeroId}")
    public String editSuperHero(@PathVariable Integer superHeroId, Model model) {
        EditSuperHeroViewModel vm = new EditSuperHeroViewModel();
        Response<SuperHero> toEdit = service.getSuperHeroById(superHeroId);
        Response<List<Organization>> allOrganizations = service.getAllOrganizations();

        vm.setSuperHeroes(toEdit.getResponseData());
        vm.setAllOrganizations(allOrganizations.getResponseData());

        model.addAttribute("vm", vm);

        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String editSuperHeros(EditSuperHeroViewModel vm, Model model) {
        List<Organization> selectedOrganizations = new ArrayList<Organization>();
        Integer[] selectedIds = vm.getSelectedOrganizationIds();
        if (selectedIds != null) {
            for (int i = 0; i < selectedIds.length; i++) {
                Integer organizationId = selectedIds[i];
                Response<Organization> getOrganzationResponse = service.getOrganizationById(organizationId);
                selectedOrganizations.add(getOrganzationResponse.getResponseData());
            }
            vm.getSuperHeroes().setOrganizations(selectedOrganizations);
        }
        Response<SuperHero> editResponse = service.editSuperHero(vm.getSuperHeroes());
        model.addAttribute("vm", vm);

        return "redirect:/superHero";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(Integer superHeroId) {
        Response<SuperHero> response = service.deleteSuperHeroById(superHeroId);
        return "redirect:/superHero";
    }

}
