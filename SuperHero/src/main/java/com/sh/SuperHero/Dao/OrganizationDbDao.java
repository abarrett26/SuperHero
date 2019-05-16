/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dao.SuperDbDao.SuperMapper;
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
public class OrganizationDbDao implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    public OrganizationDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Organization getOrganizationById(Integer organizationId) throws OrganizationPersistenceException {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organizations WHERE organizationId = ?";
            Organization organ = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID,
                    new OrganizationMapper(), organizationId);
            organ.setSuperHeroes(getSuperHeroesForOrganization(organizationId));
            return organ;
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to retrieve Organization from database.");
        }
    }

    @Override
    public List<Organization> getAllOrganizations() throws OrganizationPersistenceException {
        try {
            final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM Organizations";
            List<Organization> allOrganizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
            return allOrganizations;
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization toAdd) throws OrganizationPersistenceException {
        try {
            final String INSERT_ORGANIZATION = "INSERT INTO Organizations(organizationName, organizationDescription, address, phoneNumber) "
                    + " VALUES(?,?,?,?)";
            jdbc.update(INSERT_ORGANIZATION,
                    toAdd.getOrganizationName(),
                    toAdd.getDescription(),
                    toAdd.getAddress(),
                    toAdd.getPhoneNumber());

            Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setOrganizationId(newId);
            insertSuperHeroOrganizations(toAdd);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to add Organization to database.", ex);
        }
        return toAdd;
    }

    @Override
    @Transactional
    public void editOrganization(Organization toEdit) throws OrganizationPersistenceException {
        try {
            final String UPDATE_ORGANIZATION = "UPDATE Organizations SET organizationName = ?, organizationDescription = ?, address = ?, phoneNumber = ?"
                    + " WHERE organizationId = ?";
            jdbc.update(UPDATE_ORGANIZATION,
                    toEdit.getOrganizationName(),
                    toEdit.getDescription(),
                    toEdit.getAddress(),
                    toEdit.getPhoneNumber());
                    toEdit.getOrganizationId();

            final String DELETE_SUPERHERO_ORGANIZATIONS = "DELETE FROM superHeroOrganization WHERE organizationId = ?";
            jdbc.update(DELETE_SUPERHERO_ORGANIZATIONS, toEdit.getOrganizationId());
            insertSuperHeroOrganizations(toEdit);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to update game to database.");
        }
    }

    @Override
    public void deleteOrganizationById(Integer organizationId) throws OrganizationPersistenceException {
        try {
            final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superHeroOrganization "
                    + " WHERE organizationId = ?";
            jdbc.update(DELETE_SUPERHERO_ORGANIZATION, organizationId);

            final String DELETE_ORGANIZATION = "DELETE FROM Organizations WHERE organizationId = ?";
            jdbc.update(DELETE_ORGANIZATION, organizationId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to delete Organizations from database.", ex);
        }
    }

    private void insertSuperHeroOrganizations(Organization toAdd) throws OrganizationPersistenceException {
        try {
            final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO "
                    + " superHeroOrganization(organizationId, superHeroId) VALUES(?,?)";
            if (toAdd.getSuperHeroes() != null) {
                for (SuperHero superh : toAdd.getSuperHeroes()) {
                    jdbc.update(INSERT_SUPERHERO_ORGANIZATION,
                            toAdd.getOrganizationId(),
                            superh.getSuperHeroId());
                }
            }
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to insert into database");
        }
    }

    public List<SuperHero> getSuperHeroesForOrganization(int id) throws OrganizationPersistenceException {
        try {
           final String SELECT_SUPERS_FOR_ORGANIZATION = "SELECT s.* FROM SuperHeros s "
                   + "JOIN superHeroOrganization sho ON sho.superHeroId = s.superHeroId WHERE sho.organizationId = ?";
           return jdbc.query(SELECT_SUPERS_FOR_ORGANIZATION, new SuperMapper(), id);
       } catch (DataAccessException ex) {
           throw new OrganizationPersistenceException("unable to retrieve super heroes from database ");
       }
   }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organ = new Organization();
            organ.setOrganizationId(rs.getInt("organizationId"));
            organ.setOrganizationName(rs.getString("organizationName"));
            organ.setDescription(rs.getString("organizationDescription"));
            organ.setAddress(rs.getString("address"));
            organ.setPhoneNumber(rs.getString("phoneNumber"));

            return organ;
        }
    }
}
