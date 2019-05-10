/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dao.LocationDbDao.LocationMapper;
import com.sh.SuperHero.Dao.SuperDbDao.SuperMapper;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class SightingDbDao implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    public SightingDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Sighting getSightingById(Integer sightingId) throws SightingPersistenceException {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sightings WHERE sightingsId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            sighting.setSuperHeroes(getSupersForSighting(sighting.getSightingsId()));
            return sighting;
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
        }
    }

    @Override
    public List<Sighting> get10Sightings() throws SightingPersistenceException {
        List<Sighting> allSightings = new ArrayList<>();
        try {
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sightings ORDER BY Date asc LIMIT 10";
            allSightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
            associateLocationToSighting(allSightings);
            associateSuperHeroesToSightings(allSightings);
            return allSightings;
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to retrieve Sighting from database.");
        }
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingPersistenceException {
        List<Sighting> allSightings = new ArrayList<>();
        try {
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sightings";
            allSightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
            associateLocationToSighting(allSightings);
            associateSuperHeroesToSightings(allSightings);
            return allSightings;
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to retrieve characters from database.");
        }
    }

    @Override
    public List<Sighting> getAllCharactersByUserId(Integer sightingId) throws SightingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting toAdd) throws SightingPersistenceException {
        try {
            final String INSERT_GAME = "INSERT INTO Sightings(Date, locationId) "
                    + " VALUES(?,?)";
            jdbc.update(INSERT_GAME,
                    toAdd.getDate(),
                    toAdd.getLocationId());

            Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setSightingsId(newId);
            insertSuperHeroSighting(toAdd);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to add Sighting to database.", ex);
        }
        return toAdd;
    }

    @Override
    public void editSighting(Sighting toEdit) throws SightingPersistenceException {
        try {
            final String UPDATE_GAME = "UPDATE Sightings SET Date = ?, locationId = ?, sightingId "
                    + " WHERE sightingId = ?";
            jdbc.update(UPDATE_GAME,
                    toEdit.getDate(),
                    toEdit.getLocationId(),
                    toEdit.getSightingsId());

            final String DELETE_SUPERHERO_SIGHTING = "DELETE FROM superHeroSighting WHERE sightingId = ?";
            jdbc.update(DELETE_SUPERHERO_SIGHTING, toEdit.getSightingsId());
            insertSuperHeroSighting(toEdit);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to update Sighitng to database.");
        }
    }

    @Override
    public void deleteSightingById(Integer sightingId) throws SightingPersistenceException {
        try {
            final String DELETE_SUPER_SIGHITNG= "DELETE FROM superHeroSighting "
                    + " WHERE sightingId = ?";
            jdbc.update(DELETE_SUPER_SIGHITNG, sightingId);

            final String DELETE_GAME = "DELETE FROM Sightings WHERE sightingId = ?";
            jdbc.update(DELETE_GAME, sightingId);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to delete Sighting from database.", ex);
        }
    }

    @Override
    public List<SuperHero> getSupersForSighting(Integer sightingsId) throws SightingPersistenceException {
        try {
           final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT s.* FROM SuperHeros s "
                   + "JOIN superHeroSighting ss ON ss.superHeroId = s.superHeroId WHERE ss.sightingId = ?";
           return jdbc.query(SELECT_SUPERHERO_FOR_SIGHTING, new SuperMapper(), sightingsId);
       } catch (DataAccessException ex) {
           throw new SightingPersistenceException("Unable to retrieve supers from database.");
       }
   }
    
    
    private void associateLocationToSighting(List<Sighting> allSightings) throws SightingPersistenceException {
        try {
            for (Sighting sighting : allSightings) {
                List<Location> allLocations = getLocationForSighting(sighting.getSightingsId());

                for (Location location : allLocations) {
                    sighting.setSightingsId(location.getLocationId());
                }
            }
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to find user for character.");
        }
    }
    
    
    private void associateSuperHeroesToSightings(List<Sighting> allSightings) throws SightingPersistenceException {
        try {
           for (Sighting sighting : allSightings) {
               sighting.setSuperHeroes(this.getSupersForSighting(sighting.getSightingsId()));
           }
       } catch (DataAccessException ex) {
           throw new SightingPersistenceException("Unable to add supers to sighting in database.");
       }
   }
    
    
    private void insertSuperHeroSighting(Sighting toEdit) throws SightingPersistenceException {
        try {
            final String INSERT_SUPERHERO_SIGHTING = "INSERT INTO "
                    + " superHeroSighting(sightingId, superHeroId) VALUES(?,?)";
            if (toEdit.getSuperHeroes()!= null) {
                for (SuperHero user : toEdit.getSuperHeroes()) {
                    jdbc.update(INSERT_SUPERHERO_SIGHTING,
                            toEdit.getSightingsId(),
                            user.getSuperHeroId());
                }
            }
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Unable to make updates to database.");
        }
    }

    private List<Location> getLocationForSighting(Integer sightingsId) throws SightingPersistenceException {
        try {
           final String SELECT_LOCATION_FOR_SIGHTING = "SELECT * FROM Locations l"
                   + " JOIN Sightings s ON l.locationId = s.locationId WHERE sightingId = ?";
           return jdbc.query(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), sightingsId);
       } catch (DataAccessException ex) {
           throw new SightingPersistenceException("Unable to retrieve Locations from database.");
       }
   }


    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sight = new Sighting();
            sight.setSightingsId(rs.getInt("sightingId"));
            sight.setDate(rs.getDate("Date").toLocalDate());
            sight.setLocationId(rs.getInt("locationId"));
            return sight;
        }
    }

}
