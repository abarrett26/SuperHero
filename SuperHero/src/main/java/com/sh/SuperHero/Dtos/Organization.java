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
public class Organization {
    
   Integer organizationId;
    String organizationName;
   String description;
    String address;
    String phoneNumber;
  List<SuperHero> superHeroes;
    
    
    
    public boolean hasSuper(Integer superHeroId) {
       boolean found = false;
       for (SuperHero toCheck : getSuperHeroes()) {
           if (toCheck.getSuperHeroId()== superHeroId) {
               found = true;
               break;
           }
       }
       return found;
   }
    
   @Override
   public int hashCode() {
       int hash = 5;
       hash = 71 * hash + this.getOrganizationId();
       hash = 71 * hash + Objects.hashCode(this.getOrganizationName());
       hash = 71 * hash + Objects.hashCode(this.getDescription());
       hash = 71 * hash + Objects.hashCode(this.getAddress());
       hash = 71 * hash + Objects.hashCode(this.getSuperHeroes());
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
       final Organization other = (Organization) obj;
       if (this.getOrganizationId() != other.getOrganizationId()) {
           return false;
       }
       if (!Objects.equals(this.organizationName, other.organizationName)) {
           return false;
       }
       if (!Objects.equals(this.description, other.description)) {
           return false;
       }
       if (!Objects.equals(this.address, other.address)) {
           return false;
       }
       if (!Objects.equals(this.superHeroes, other.superHeroes)) {
           return false;
       }
       return true;
   }

    /**
     * @return the organizationId
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the superHeroes
     */
    public List<SuperHero> getSuperHeroes() {
        return superHeroes;
    }

    /**
     * @param superHeroes the superHeroes to set
     */
    public void setSuperHeroes(List<SuperHero> superHeroes) {
        this.superHeroes = superHeroes;
    }
}
