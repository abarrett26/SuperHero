/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Organization;
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
public class SuperDbDao implements SuperDao {
    
    @Autowired
    JdbcTemplate jdbc;

    public SuperDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Override
    public List<SuperHero> getAllSuperHeros() throws SuperHeroPersistenceException {
        
        try {
            final String SELECT_ALL_SUPERHEROES = "SELECT * FROM SuperHeros";
            List<SuperHero> allSuperHeroes = jdbc.query(SELECT_ALL_SUPERHEROES, new SuperMapper());
            return allSuperHeroes;
            
        } catch (DataAccessException ex) {
            throw new SuperHeroPersistenceException("Unable to retrieve SuperHeros from database.");
        }
    }
    
    @Override
    public SuperHero getSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
       try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM SuperHeros WHERE superHeroId = ?";
            
            return jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperMapper(), superHeroId);
            
        } catch (DataAccessException ex) {
            throw new SuperHeroPersistenceException("Unable to retrieve SuperHeros from database.", ex);
        }
    }

    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero toAdd) throws SuperHeroPersistenceException {
        try {
            final String INSERT_GAME = "INSERT INTO SuperHeros(superHeroName, description, superPower) "
                    + " VALUES(?,?,?)";
            jdbc.update(INSERT_GAME,
                    toAdd.getSuperHeroName(),
                    toAdd.getDescription(),
                    toAdd.getSuperPower());
         
                    

            Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setSuperHeroId(newId);
            insertSuperHeroOrganization(toAdd);
        } catch (DataAccessException ex) {
            throw new SuperHeroPersistenceException("Unable to add SuperHero to database.", ex);
        }
        return toAdd;
    }

    @Override
    public void editSuperHero(SuperHero toEdit) throws SuperHeroPersistenceException {
        try {
            final String UPDATE_SUPERHERO = "UPDATE SuperHeros SET superHeroName = ?, description = ?, superPower = ?"
                    + " WHERE superHeroId = ?";
            jdbc.update(UPDATE_SUPERHERO,
                    toEdit.getSuperHeroId(),
                    toEdit.getSuperHeroName(),
                    toEdit.getDescription(),
                    toEdit.getSuperPower());

            final String DELETE_SUPERHERO_ORGANIZATIONS = "DELETE FROM superHeroOrganization WHERE superHeroId = ?";
            jdbc.update(DELETE_SUPERHERO_ORGANIZATIONS, toEdit.getSuperHeroId());
      //      insertGameUsers(toEdit);
        } catch (DataAccessException ex) {
            throw new SuperHeroPersistenceException("Unable to update SuperHero to database.");
        }
    }

    @Override
    public void deleteSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
        try {
            //delete from any character bridge tables first
            final String DELETE_SUPER_SIGHITNG = "DELETE FROM superHeroSighting "
                    + " WHERE superHeroId = ?";
            jdbc.update(DELETE_SUPER_SIGHITNG, superHeroId);

            final String DELETE_SUPER_ORGANIZATIONS = "DELETE FROM superHeroOrganization "
                    + " WHERE superHeroId = ?";
            jdbc.update(DELETE_SUPER_ORGANIZATIONS, superHeroId);
            
            final String DELETE_SUPERHERO = "DELETE FROM SuperHeros "
                    + " WHERE superHeroId = ?";
            jdbc.update(DELETE_SUPERHERO, superHeroId);
            
        } catch (DataAccessException ex) {
            throw new SuperHeroPersistenceException("Unable to remove SuperHero from database.");
        }
    }

    private void insertSuperHeroOrganization(SuperHero toAdd) throws SuperHeroPersistenceException {
        try {
           final String INSERT_SUPER_ORGANIZATION = "INSERT INTO "
                   + "superHeroOrganization(organizationId, superHeroId) VALUES(?,?)";
 
           if (toAdd.getOrganizations() != null) {
               for (Organization organization : toAdd.getOrganizations()) {
                   jdbc.update(INSERT_SUPER_ORGANIZATION,
                           organization.getOrganizationId(),
                           toAdd.getSuperHeroId());
               }
           }
       } catch (DataAccessException ex) {
           throw new SuperHeroPersistenceException("Unable to make updates to database.");
       }
   }

   
   public static final class SuperMapper implements RowMapper<SuperHero> {
 
       @Override
       public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
           SuperHero superHero = new SuperHero();
           superHero.setSuperHeroId(rs.getInt("superHeroId"));
           superHero.setSuperHeroName(rs.getString("superHeroName"));
           superHero.setDescription(rs.getString("description"));
           superHero.setSuperPower(rs.getString("superPower"));
 
           return superHero;
       }
   }
}
