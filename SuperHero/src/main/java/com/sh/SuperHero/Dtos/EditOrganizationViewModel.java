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
public class EditOrganizationViewModel {
    
   
   private Integer[] selectedSuperHeroIds;
   private Organization organizations;
   private List<SuperHero> allSuperHeroes;

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
     * @return the organizations
     */
    public Organization getOrganizations() {
        return organizations;
    }

    /**
     * @param organizations the organizations to set
     */
    public void setOrganizations(Organization organizations) {
        this.organizations = organizations;
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
}
