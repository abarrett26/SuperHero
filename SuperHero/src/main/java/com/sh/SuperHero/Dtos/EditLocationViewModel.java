/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dtos;

import com.sh.SuperHero.Controller.*;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class EditLocationViewModel {
    
   private List<Sighting> allSightings;
   private Location location;
   private Integer[] selectedSightingsIds;

    /**
     * @return the allSightings
     */
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    /**
     * @param allSightings the allSightings to set
     */
    public void setAllSightings(List<Sighting> allSightings) {
        this.allSightings = allSightings;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the selectedSightingsIds
     */
    public Integer[] getSelectedSightingsIds() {
        return selectedSightingsIds;
    }

    /**
     * @param selectedSightingsIds the selectedSightingsIds to set
     */
    public void setSelectedSightingsIds(Integer[] selectedSightingsIds) {
        this.selectedSightingsIds = selectedSightingsIds;
    }
}
