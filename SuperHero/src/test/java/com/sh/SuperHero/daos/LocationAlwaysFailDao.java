/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.LocationDao;
import com.sh.SuperHero.Dao.LocationPersistenceException;
import com.sh.SuperHero.Dtos.Location;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class LocationAlwaysFailDao implements LocationDao {

    @Override
    public Location getLocationsById(Integer locationId) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to write new location to database.");
    }

    @Override
    public List<Location> getAllLocations() throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to write new location to database.");
    }

    @Override
    public Location addLocation(Location toAdd) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to write new location to database.");
    }

    @Override
    public void editLocation(Location toEdit) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to write new location to database.");
    }

    @Override
    public void deleteLocationById(Integer locationId) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to write new location to database.");
    }

}
