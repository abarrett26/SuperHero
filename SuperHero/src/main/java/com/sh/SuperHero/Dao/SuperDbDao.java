/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dao.OrganizationDbDao.OrganizationMapper;
import com.sh.SuperHero.Dtos.Organization;
import com.sh.SuperHero.Dtos.SuperHero;
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
           final String GET_SUPERHERO_BY_ID = "SELECT * FROM SuperHeros WHERE superHeroId = ?";
           SuperHero superHero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperMapper(), superHeroId);
           superHero.setOrganizations(getOrganizationsForSuperHeroes(superHeroId));
           return superHero;
       } catch (DataAccessException ex) {
           throw new SuperHeroPersistenceException("Unable to retrieve superhero from database.");
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
                   
                    toEdit.getSuperHeroName(),
                    toEdit.getDescription(),
                    toEdit.getSuperPower(),
                    toEdit.getSuperHeroId());

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
    

    private void insertSuperHeroOrganization(SuperHero toEdit) throws SuperHeroPersistenceException {
        try {
           final String INSERT_SUPER_ORGANIZATION = "INSERT INTO "
                   + "superHeroOrganization(organizationId, superHeroId) VALUES(?,?)";
 
           if (toEdit.getOrganizations() != null) {
               for (Organization organization : toEdit.getOrganizations()) {
                   jdbc.update(INSERT_SUPER_ORGANIZATION,
                           organization.getOrganizationId(),
                           toEdit.getSuperHeroId());
               }
           }
       } catch (DataAccessException ex) {
           throw new SuperHeroPersistenceException("Unable to make updates to database.");
       }
   }
    
    
    private void associateOrganizationToSuperHero(List<SuperHero> AllSupers) throws SuperHeroPersistenceException {
       try {
           for (SuperHero superHero : AllSupers) {
               superHero.setOrganizations(this.getOrganizationsForSuperHeroes(superHero.getSuperHeroId()));
           }
       } catch (DataAccessException ex) {
           throw new SuperHeroPersistenceException("Unable to add orgs to super in database.");
       }
   }

    @Override
    public List<Organization> getOrganizationsForSuperHeroes(Integer superHeroId) throws SuperHeroPersistenceException {
        try {
           final String SELECT_ORGANIZATION_FOR_SUPERHERO = "SELECT o.* FROM Organizations o "
                   + " JOIN superHeroOrganization sho ON sho.organizationId = o.organizationId WHERE sho.superHeroId = ?";
           return jdbc.query(SELECT_ORGANIZATION_FOR_SUPERHERO, new OrganizationMapper(), superHeroId);
       } catch (DataAccessException ex) {
           throw new SuperHeroPersistenceException("Unable to retrieve organizations from database.");
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
