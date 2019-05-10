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
public class EditSuperHeroViewModel {
    
   
   private Integer[] selectedOrganizationIds;
   private SuperHero superHeroes;
   private List<Organization> allOrgsanizations;

    /**
     * @return the selectedOrganizationIds
     */
    public Integer[] getSelectedOrganizationIds() {
        return selectedOrganizationIds;
    }

    /**
     * @param selectedOrganizationIds the selectedOrganizationIds to set
     */
    public void setSelectedOrganizationIds(Integer[] selectedOrganizationIds) {
        this.selectedOrganizationIds = selectedOrganizationIds;
    }

    /**
     * @return the superHeroes
     */
    public SuperHero getSuperHeroes() {
        return superHeroes;
    }

    /**
     * @param superHeroes the superHeroes to set
     */
    public void setSuperHeroes(SuperHero superHeroes) {
        this.superHeroes = superHeroes;
    }

    /**
     * @return the allOrgsanizations
     */
    public List<Organization> getAllOrgsanizations() {
        return allOrgsanizations;
    }

    /**
     * @param allOrgsanizations the allOrgsanizations to set
     */
    public void setAllOrgsanizations(List<Organization> allOrgsanizations) {
        this.allOrgsanizations = allOrgsanizations;
    }
}
