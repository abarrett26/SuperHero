/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public interface SightingDao {
    
    Sighting getSightingById(Integer sightingId) throws SightingPersistenceException;
    
    List<Sighting> get10Sightings() throws SightingPersistenceException;

    List<Sighting> getAllSightings() throws SightingPersistenceException;

    List<Sighting> getAllCharactersByUserId(Integer sightingId) throws SightingPersistenceException;

    Sighting addSighting(Sighting toAdd) throws SightingPersistenceException;

    void editSighting(Sighting toEdit) throws SightingPersistenceException;

    void deleteSightingById(Integer sightingId) throws SightingPersistenceException;
    
    List<SuperHero> getSupersForSighting(Integer sightingsId) throws SightingPersistenceException;
    
}
