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
public class AddSuperHeroViewModel {
   
   private Integer[] selectedOrganizationIds;
   private SuperHero superHeroes;
   private List<Organization> organizations;

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
     * @return the organizations
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     * @param organizations the organizations to set
     */
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
