/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Controller;

import com.sh.SuperHero.Dtos.AddOrganizationViewModel;
import com.sh.SuperHero.Dtos.EditOrganizationViewModel;
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
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author alexbarrett
 */
@Controller
public class OrganizationController {

    @Autowired
    SHService service;

    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Organization>> violations = new HashSet();

    @GetMapping("organization")
    public String displayOrganizations(Model model) {
        Response<List<Organization>> organization = service.getAllOrganizations();
        model.addAttribute("organization", organization.getResponseData());
        Response<List<SuperHero>> superHero = service.getAllSuperHeroes();
        model.addAttribute("superHero", superHero.getResponseData());
        return "organization";
    }

    @GetMapping("displayOrganization")
    public String displayOrganizationById(Integer organizationId, Model model) {
        Response<Organization> response = service.getOrganizationById(organizationId);
        model.addAttribute("organization", response.getResponseData());
        return "displayOrganization";
    }

    @PostMapping("addOrganization")
    public String addOrganization(AddOrganizationViewModel vm, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getOrganizations());
        String toReturn = "";
        if (violations.isEmpty()) {
            List<SuperHero> selectedSuperHeroes = new ArrayList<SuperHero>();
            Integer[] selectedIds = vm.getSelectedSuperHeroIds();
            if (selectedIds != null) {
                for (int i = 0; i < vm.getSelectedSuperHeroIds().length; i++) {
                    int superHeroId = selectedIds[i];
                    Response<SuperHero> superHeroResponse = service.getSuperHeroById(superHeroId);
                    selectedSuperHeroes.add(superHeroResponse.getResponseData());
                }
            }
            vm.getOrganizations().setSuperHeroes(selectedSuperHeroes);
            Response<Organization> addResponse = service.addOrganization(vm.getOrganizations());

        } else {
            model.addAttribute("organizationName", vm.getOrganizations().getOrganizationName());
            model.addAttribute("description", vm.getOrganizations().getDescription());
            model.addAttribute("address", vm.getOrganizations().getAddress());
            model.addAttribute("phoneNumber", vm.getOrganizations().getPhoneNumber());
            model.addAttribute("superHero", vm.getOrganizations().getSuperHeroes());
        }
        model.addAttribute("errors", violations);
        toReturn = displayOrganizations(model);
        return toReturn;
    }

    @GetMapping("editOrganization/{organizationId}")
    public String editOrganization(@PathVariable Integer organizationId, Model model) {
        EditOrganizationViewModel vm = new EditOrganizationViewModel();
        Response<Organization> toEdit = service.getOrganizationById(organizationId);
        Response<List<SuperHero>> allSuperHeroes = service.getAllSuperHeroes();

        vm.setOrganizations(toEdit.getResponseData());
        vm.setAllSuperHeroes(allSuperHeroes.getResponseData());

        model.addAttribute("vm", vm);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String editOrganization(EditOrganizationViewModel vm, Model model) {

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Organization>> validationFailures = validate.validate(vm.getOrganizations());
        model.addAttribute("errors", validationFailures);
        if (validationFailures.isEmpty()) {

            List<SuperHero> selectedSupers = new ArrayList<SuperHero>();
            Integer[] selectedIds = vm.getSelectedSuperHeroIds();

            if (selectedIds != null) {
                for (int i = 0; i < selectedIds.length; i++) {
                    Integer superId = selectedIds[i];
                    Response<SuperHero> getSuperResponse = service.getSuperHeroById(superId);
                    selectedSupers.add(getSuperResponse.getResponseData());
                }
                vm.getOrganizations().setSuperHeroes(selectedSupers);
            }
            Response<Organization> editResponse = service.editOrganization(vm.getOrganizations());
            model.addAttribute("vm", vm);
            Response<Organization> response = service.getOrganizationById(editResponse.getResponseData().getOrganizationId());
            model.addAttribute("organization", response.getResponseData());
            return "redirect:/organization";
        } else {
            return editOrganization(vm.getOrganizations().getOrganizationId(), model);
        }
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer organizationId) {
        Response<Organization> response = service.deleteOrganizationById(organizationId);
        return "redirect:/organization";
    }

}


