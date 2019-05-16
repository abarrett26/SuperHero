/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dtos;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author alexbarrett
 */
public class Sighting {

    private Integer sightingId;
    @PastOrPresent(message = "Date must not be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private List<SuperHero> superHeroes;
    private Integer locationId;
    private Location LocOfSighting;

    public boolean hasLocation(Integer locationId) {
        boolean found = LocOfSighting.getLocationId() == locationId;
        return found;
    }
    
   

    public boolean hasSuper(Integer superHeroId) {
        boolean found = false;
        for (SuperHero toCheck : getSuperHeroes()) {
            if (toCheck.getSuperHeroId() == superHeroId) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * @return the sightingsId
     */
    public Integer getSightingId() {
        return sightingId;
    }

    /**
     * @param sightingId the sightingId to set
     */
    public void setSightingId(Integer sightingId) {
        this.sightingId = sightingId;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
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

    /**
     * @return the locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the LocOfSighting
     */
    public Location getLocOfSighting() {
        return LocOfSighting;
    }

    /**
     * @param LocOfSighting the LocOfSighting to set
     */
    public void setLocOfSighting(Location LocOfSighting) {
        this.LocOfSighting = LocOfSighting;
    }
}
