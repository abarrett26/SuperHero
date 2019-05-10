/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Controller;

import com.sh.SuperHero.Dtos.EditLocationViewModel;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Service.Response;
import com.sh.SuperHero.Service.SHService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author alexbarrett
 */

@Controller
public class LocationController {

    @Autowired
    SHService service;

    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Location>> violations = new HashSet();

    @GetMapping("location")
    public String displayLocations(Model model) {
        Response<List<Location>> response = service.getAllLocations();
        List<Location> allLocations = response.getResponseData();
        model.addAttribute("location", allLocations);
        return "location";
    }

    @GetMapping("displayLocation")
    public String displayLocationById(Integer locationId, Model model) {
        Response<Location> response = service.getLocationById(locationId);
        model.addAttribute("location", response.getResponseData());
        return "displayLocation";
    }

    @PostMapping("addLocation")
    public String addLocation(@ModelAttribute Location toAdd, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Location>> validationFailures = validate.validate(toAdd);
        model.addAttribute("errors", validationFailures);
        if (validationFailures.isEmpty()) {
            Response<Location> response = service.addLocation(toAdd);
        } else {
            model.addAttribute("LocationName", toAdd.getLocationName());
            model.addAttribute("Descriptions", toAdd.getDescription());
            model.addAttribute("Address", toAdd.getAddress());
            model.addAttribute("lat", toAdd.getLat());
            model.addAttribute("lng", toAdd.getLng());
        }
        return displayLocations(model);
    }

    @GetMapping("editLocation/{LocationID}")
    public String editLocation(@PathVariable Integer locationId, Model model) {

        EditLocationViewModel vm = new EditLocationViewModel();
        Response<Location> toEdit = service.getLocationById(locationId);
        Response<List<Sighting>> allSightings = service.getAllSightings();

        vm.setLocation(toEdit.getResponseData());
        vm.setAllSightings(allSightings.getResponseData());

        model.addAttribute("vm", vm);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String editLocation(EditLocationViewModel vm, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Location>> validationFailures = validate.validate(vm.getLocation());
        model.addAttribute("errors", validationFailures);
        if (validationFailures.isEmpty()) {
            Response<Location> editResponse = service.editLocation(vm.getLocation());
            model.addAttribute("vm", vm);
            Response<Location> response = service.getLocationById(editResponse.getResponseData().getLocationId());
            model.addAttribute("Location", response.getResponseData());
            return "redirect:/Location";
        } else {
            return editLocation(vm.getLocation().getLocationId(), model);
        }
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locationId) {
        Response<Location> response = service.deleteLocationById(locationId);
        return "redirect:/location";
    }

}
