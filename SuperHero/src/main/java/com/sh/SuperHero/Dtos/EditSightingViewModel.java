/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dtos;

import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class EditSightingViewModel {
    
   private Integer[] selectedSuperHeroIds;
   private Sighting sighting;
   private List<SuperHero> allSuperHeroes;
   private List<Location> allLocations;

    /**
     * @return the selectedSuperHeroIds
     */
    public Integer[] getSelectedSuperHeroIds() {
        return selectedSuperHeroIds;
    }

    /**
     * @param selectedSuperHeroIds the selectedSuperHeroIds to set
     */
    public void setSelectedSuperHeroIds(Integer[] selectedSuperHeroIds) {
        this.selectedSuperHeroIds = selectedSuperHeroIds;
    }

    /**
     * @return the sighting
     */
    public Sighting getSighting() {
        return sighting;
    }

    /**
     * @param sighting the sighting to set
     */
    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }

    /**
     * @return the allSuperHeroes
     */
    public List<SuperHero> getAllSuperHeroes() {
        return allSuperHeroes;
    }

    /**
     * @param allSuperHeroes the allSuperHeroes to set
     */
    public void setAllSuperHeroes(List<SuperHero> allSuperHeroes) {
        this.allSuperHeroes = allSuperHeroes;
    }

    /**
     * @return the allLocations
     */
    public List<Location> getAllLocations() {
        return allLocations;
    }

    /**
     * @param allLocations the allLocations to set
     */
    public void setAllLocations(List<Location> allLocations) {
        this.allLocations = allLocations;
    }
}
