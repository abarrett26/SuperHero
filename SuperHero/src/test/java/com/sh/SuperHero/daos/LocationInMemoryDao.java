/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.LocationDao;
import com.sh.SuperHero.Dao.LocationPersistenceException;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Sighting;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class LocationInMemoryDao implements LocationDao {
 
   List<Location> allLocations = new ArrayList<>();
 
   public LocationInMemoryDao() {
       Location location1 = new Location();
       location1.setLocationId(1);
       location1.setLocationName("Test1");
       location1.setDescription("Test1");
       location1.setAddress("Test1");
       location1.setLat(1.00);
       location1.setLng(1.00);
       allLocations.add(location1);
 
       Location location2 = new Location();
       location2.setLocationId(2);
       location2.setLocationName("Test2");
       location2.setDescription("Test2");
       location2.setAddress("Test2");
       location2.setLat(2.00);
       location2.setLng(2.00);
       allLocations.add(location2);
 
   }
 
   @Override
   public Location getLocationsById(Integer locationId) {
 
       Location toReturn = null;
       for (Location toCheck : allLocations) {
           if (toCheck.getLocationId() == locationId) {
               toReturn = toCheck;
           }
       }
       return toReturn;
   }
 
   @Override
   public List<Location> getAllLocations() {
       return allLocations;
   }
 
   @Override
   public Location addLocation(Location toAdd) {
       toAdd.setLocationId(generateLocationId(allLocations));
       allLocations.add(toAdd);
       return toAdd;
   }
 
   @Override
   public void editLocation(Location toEdit) throws LocationPersistenceException {
       
       Location InMemorylocation = getLocationsById(toEdit.getLocationId());
       InMemorylocation.setLocationName(toEdit.getLocationName());
       InMemorylocation.setDescription(toEdit.getDescription());
       InMemorylocation.setAddress(toEdit.getAddress());
       InMemorylocation.setLat(toEdit.getLat());
       InMemorylocation.setLng(toEdit.getLng());
       
   }
 
   public int generateLocationId(List<Location> allLocations) {
 
       int toReturn = Integer.MIN_VALUE;
       if (allLocations.isEmpty()) {
           toReturn = 1;
       } else {
           for (Location toInspect : allLocations) {
               if (toInspect.getLocationId() > toReturn) {
                   toReturn = toInspect.getLocationId();
               }
           }
           toReturn++;
       }
       return toReturn;
   }
   
   @Override
   public void deleteLocationById(Integer locationId) throws LocationPersistenceException {
       getLocationsById(locationId);
       int indexToRemove = Integer.MIN_VALUE;
       for (Location location : allLocations){
           if (location.getLocationId() == locationId) {
              indexToRemove = allLocations.indexOf(location);
              allLocations.remove(indexToRemove);
              break;
           }
       }
   }

   
}