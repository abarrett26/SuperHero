/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alexbarrett
 */
@Component
public class LocationDbDao implements LocationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    public LocationDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Location getLocationsById(Integer locationId) throws LocationPersistenceException {
         try {
           final String GET_LOCATION_BY_ID = "SELECT * FROM Locations WHERE locationId = ?";
           Location Location = jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), locationId);
           return Location;
       } catch (DataAccessException ex) {
           throw new LocationPersistenceException("Unable to retrieve location from database");
       }
   }

    @Override
    public List<Location> getAllLocations() throws LocationPersistenceException {
        try {
           final String SELECT_ALL_LOCATIONS = "SELECT * FROM Locations";
           List<Location> allLocations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
           return allLocations;
       } catch (DataAccessException ex) {
           throw new LocationPersistenceException("Unable to retrieve locations from database");
       }
   }
    @Override
    @Transactional
    public Location addLocation(Location toAdd) throws LocationPersistenceException {
        try {
           final String INSERT_LOCATION = "INSERT INTO Locations(locationName, description, address, latitude, longitude) "
                   + "VALUES(?,?,?,?,?)";
           jdbc.update(INSERT_LOCATION,
                   toAdd.getLocationName(),
                   toAdd.getDescription(),
                   toAdd.getAddress(),
                   toAdd.getLat(),
                   toAdd.getLng());
 
           int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
           toAdd.setLocationId(newId);
       } catch (DataAccessException ex) {
           throw new LocationPersistenceException("Unable to write new location to database.");
       }
       return toAdd;
   }

    @Override
    public void editLocation(Location toEdit) throws LocationPersistenceException {
        try {
           final String UPDATE_LOCATION = "UPDATE Locations SET locationName = ?, description = ?, "
                   + "address = ?, latitude = ?, longitude = ? WHERE locationId = ?";
           jdbc.update(UPDATE_LOCATION,
                   toEdit.getLocationName(),
                   toEdit.getDescription(),
                   toEdit.getAddress(),
                   toEdit.getLat(),
                   toEdit.getLng(),
                   toEdit.getLocationId());
       } catch (DataAccessException ex) {
           throw new LocationPersistenceException("Unable to write updated location to database.");
       }
   }

    @Override
    public void deleteLocationById(Integer locationId) throws LocationPersistenceException {
        try {
           final String DELETE_FROM_SIGHTING = "DELETE FROM Sightings "
                   + " WHERE locationId = ?";
           jdbc.update(DELETE_FROM_SIGHTING, locationId);
 
           final String DELETE_LOCATION = "DELETE FROM Locations WHERE locationId = ?";
           jdbc.update(DELETE_LOCATION, locationId);
       } catch (DataAccessException ex) {
           throw new LocationPersistenceException("Unable to delete location from database");
       }
   }
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setLocationId(rs.getInt("locationId"));
            loc.setLocationName(rs.getString("locationName"));
            loc.setDescription(rs.getString("description"));
            loc.setAddress(rs.getString("address"));
            loc.setLat(rs.getDouble("latitude"));
            loc.setLng(rs.getDouble("longitude"));
            return loc;
        }
    }
    
}
