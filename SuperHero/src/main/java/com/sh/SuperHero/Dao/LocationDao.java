/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Location;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public interface LocationDao {

  
     
    Location getLocationsById(Integer locationId) throws LocationPersistenceException;

    List<Location> getAllLocations() throws LocationPersistenceException;

    Location addLocation(Location toAdd) throws LocationPersistenceException;

    void editLocation(Location toEdit) throws LocationPersistenceException;

    void deleteLocationById(Integer locationId) throws LocationPersistenceException;
}
