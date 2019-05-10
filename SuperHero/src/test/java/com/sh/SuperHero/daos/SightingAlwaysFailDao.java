/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.SightingDao;
import com.sh.SuperHero.Dao.SightingPersistenceException;
import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class SightingAlwaysFailDao implements SightingDao {

    @Override
    public Sighting getSightingById(Integer sightingId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public List<Sighting> get10Sightings() throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public List<Sighting> getAllCharactersByUserId(Integer sightingId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public void editSighting(Sighting toEdit) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public void deleteSightingById(Integer sightingId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

    @Override
    public List<SuperHero> getSupersForSighting(Integer sightingsId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
    }

}
