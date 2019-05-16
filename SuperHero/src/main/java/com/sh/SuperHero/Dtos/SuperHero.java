/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dtos;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author alexbarrett
 */
public class SuperHero {

    private Integer superHeroId;
    private String superHeroName;
    private String description;
    private String superPower;
    private List<Organization> organizations;
    private List<Sighting> sighting;
    
    public boolean hasOrganization (Integer organzationId) {
       boolean found = false;
       for ( Organization toCheck : getOrganizations()) {
           if (toCheck.getOrganizationId()== organzationId) {
               found = true;
               break;
           }
       }
       return found;
   }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.getSuperHeroId();
        hash = 71 * hash + Objects.hashCode(this.getSuperHeroName());
        hash = 71 * hash + Objects.hashCode(this.getDescription());
        hash = 71 * hash + Objects.hashCode(this.getSuperPower());
        hash = 71 * hash + Objects.hashCode(this.getOrganizations());
        hash = 71 * hash + Objects.hashCode(this.getSightings());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuperHero other = (SuperHero) obj;
        if (this.getSuperHeroId() != other.getSuperHeroId()) {
            return false;
        }
        if (!Objects.equals(this.superHeroName, other.superHeroName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        if (!Objects.equals(this.sighting, other.sighting)) {
            return false;
        }
        return true;
    }

    /**
     * @return the superHeroId
     */
    public Integer getSuperHeroId() {
        return superHeroId;
    }

    /**
     * @param superHeroId the superHeroId to set
     */
    public void setSuperHeroId(Integer superHeroId) {
        this.superHeroId = superHeroId;
    }

    /**
     * @return the superHeroName
     */
    public String getSuperHeroName() {
        return superHeroName;
    }

    /**
     * @param superHeroName the superHeroName to set
     */
    public void setSuperHeroName(String superHeroName) {
        this.superHeroName = superHeroName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the superPower
     */
    public String getSuperPower() {
        return superPower;
    }

    /**
     * @param superPower the superPower to set
     */
    public void setSuperPower(String superPower) {
        this.superPower = superPower;
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

    /**
     * @return the sighting
     */
    public List<Sighting> getSightings() {
        return sighting;
    }

    /**
     * @param sighting the sightings to set
     */
    public void setSightings(List<Sighting> sighting) {
        this.sighting = sighting;
    }
}
