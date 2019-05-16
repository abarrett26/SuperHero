/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Controller;

import com.sh.SuperHero.Dtos.AddSightingViewModel;
import com.sh.SuperHero.Dtos.EditSightingViewModel;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Sighting;
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
public class SightingController {

    @Autowired
    SHService service;

    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Sighting>> violations = new HashSet();

    @GetMapping("sighting")
    public String displaySightings(Model model) {
        Response<List<Sighting>> sightingResponse = service.getAllSightings();
        Response<List<SuperHero>> superHeroResponse = service.getAllSuperHeroes();
        Response<List<Location>> locationResponse = service.getAllLocations();

        model.addAttribute("sighting", sightingResponse.getResponseData());
        model.addAttribute("superHero", superHeroResponse.getResponseData());
        model.addAttribute("location", locationResponse.getResponseData());

        return "sighting";
    }

    @GetMapping("displaySighting")
    public String displaySightingById(Integer sightingId, Model model) {
        Response<Sighting> response = service.getSightingsById(sightingId);
        model.addAttribute("sighting", response.getResponseData());
        return "displaySighting";
    }

    @PostMapping("addSighting")
    public String addSighting(AddSightingViewModel vm, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getSighting());
        String toReturn = "";
        if (violations.isEmpty()) {
            List<SuperHero> selectedSuperHeroes = new ArrayList<SuperHero>();
            Integer[] selectedIds = vm.getSelectedSuperHeroIds();
            if (selectedIds == null) {
            } else {
                for (int i = 0; i < vm.getSelectedSuperHeroIds().length; i++) {
                    int superHeroId = selectedIds[i];
                    Response<SuperHero> superHeroResponse = service.getSuperHeroById(superHeroId);
                    selectedSuperHeroes.add(superHeroResponse.getResponseData());
                }
            }

            Response<Location> locationResponse = service.getLocationById(vm.getSighting().getLocationId());
            Location locationAdd = locationResponse.getResponseData();

            vm.getSighting().setSuperHeroes(selectedSuperHeroes);
            vm.getSighting().setLocOfSighting(locationAdd);

            Response<Sighting> addResponse = service.addSighting(vm.getSighting());
        } else {
            model.addAttribute("Date", vm.getSighting().getDate());
            model.addAttribute("allSuperHeroes", vm.getSighting().getSuperHeroes());
            model.addAttribute("locationId", vm.getSighting().getLocationId());
        }
        model.addAttribute("errors", violations);
        toReturn = displaySightings(model);
        return toReturn;
    }

    @GetMapping("editSighting/{sightingId}")
    public String editSighting(@PathVariable Integer sightingId, Model model) {
        EditSightingViewModel vm = new EditSightingViewModel();
        Response<List<SuperHero>> allSuperHeroes = service.getAllSuperHeroes();
        Response<List<Location>> allLocations = service.getAllLocations();
        Response<Sighting> toEdit = service.getSightingsById(sightingId);

        vm.setAllSuperHeroes(allSuperHeroes.getResponseData());
        vm.setAllLocations(allLocations.getResponseData());
        vm.setSighting(toEdit.getResponseData());

        model.addAttribute("vm", vm);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String editSighting(EditSightingViewModel vm, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Sighting>> validationFailures = validate.validate(vm.getSighting());
        model.addAttribute("errors", validationFailures);

        if (validationFailures.isEmpty()) {
            List<SuperHero> selectedSuperHeroes = new ArrayList<SuperHero>();
            Integer[] selectedSuperHeroIds = vm.getSelectedSuperHeroIds();
            Response<Location> selectedLocation = new Response<Location>();
            Integer selectedLocationId = vm.getSighting().getLocationId();

            if (selectedSuperHeroIds != null && selectedLocationId != null) {
                for (int i = 0; i < selectedSuperHeroIds.length; i++) {
                    Integer superHeroId = selectedSuperHeroIds[i];
                    Response<SuperHero> getSuperResponse = service.getSuperHeroById(superHeroId);
                    selectedSuperHeroes.add(getSuperResponse.getResponseData());
                }
                vm.getSighting().setSuperHeroes(selectedSuperHeroes);
                selectedLocation = service.getLocationById(selectedLocationId);
                vm.getSighting().setLocationId(selectedLocation.getResponseData().getLocationId());
                
            }
            Response<Sighting> editResponse = service.editSighting(vm.getSighting());
            model.addAttribute("vm", vm);
            Response<Sighting> response = service.getSightingsById(editResponse.getResponseData().getSightingId());
            model.addAttribute("sighting", response.getResponseData());
            return "redirect:/sighting";
        } else {
            return editSighting(vm.getSighting().getSightingId(), model);
        }
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer sightingId) {
        Response<Sighting> response = service.deleteSightingsById(sightingId);
        return "redirect:/sighting";
    }
}
