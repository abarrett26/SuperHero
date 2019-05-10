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
            organ.setSuperHeroes(getSuperHeroesForOrg(organizationId));
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
    public List<Organization> getAllOrganizationsByUserId(Integer userId) throws OrganizationPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization toAdd) throws OrganizationPersistenceException {
        try {
            final String INSERT_GAME = "INSERT INTO Organizations(organizationName, description, address, phoneNumber) "
                    + " VALUES(?,?,?,?)";
            jdbc.update(INSERT_GAME,
                    toAdd.getOrganizationName(),
                    toAdd.getDescription(),
                    toAdd.getAddress(),
                    toAdd.getPhoneNumber());

            Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setOrganizationId(newId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to add Organization to database.", ex);
        }
        return toAdd;
    }

    @Override
    public void editOrganization(Organization toEdit) throws OrganizationPersistenceException {
        try {
            final String UPDATE_GAME = "UPDATE Organizations SET organizationName = ?, description = ?, address = ?, PhoneNumber = ?"
                    + " WHERE organizationId = ?";
            jdbc.update(UPDATE_GAME,
                    toEdit.getOrganizationName(),
                    toEdit.getDescription(),
                    toEdit.getAddress(),
                    toEdit.getPhoneNumber());

            final String DELETE_GAME_USERS = "DELETE FROM superHeroOrganization WHERE organizationId = ?";
            jdbc.update(DELETE_GAME_USERS, toEdit.getOrganizationId());
            insertSuperHeroOrganizations(toEdit);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to update game to database.");
        }
    }

    @Override
    public void deleteOrganizationById(Integer organizationId) throws OrganizationPersistenceException {
        try {
            final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM uperHeroOrganization "
                    + " WHERE organizationId = ?";
            jdbc.update(DELETE_SUPERHERO_ORGANIZATION, organizationId);

            final String DELETE_ORGANIZATION = "DELETE FROM Organizations WHERE organizationId = ?";
            jdbc.update(DELETE_ORGANIZATION, organizationId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to delete Organizations from database.", ex);
        }
    }

    private void insertSuperHeroOrganizations(Organization toEdit) throws OrganizationPersistenceException {
        try {
            final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO "
                    + " superHeroOrganization(organizationId, superHeroId) VALUES(?,?)";
            if (toEdit.getSuperHeroes() != null) {
                for (SuperHero superh : toEdit.getSuperHeroes()) {
                    jdbc.update(INSERT_SUPERHERO_ORGANIZATION,
                            toEdit.getOrganizationId(),
                            superh.getSuperHeroId());
                }
            }
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Unable to make updates to database.");
        }
    }

    private List<SuperHero> getSuperHeroesForOrg(Integer organizationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
