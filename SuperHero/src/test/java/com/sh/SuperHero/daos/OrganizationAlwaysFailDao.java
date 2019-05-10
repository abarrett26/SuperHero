/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.OrganizationDao;
import com.sh.SuperHero.Dao.OrganizationPersistenceException;
import com.sh.SuperHero.Dtos.Organization;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class OrganizationAlwaysFailDao implements OrganizationDao {

    @Override
    public Organization getOrganizationById(Integer organizationId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
    }

    @Override
    public List<Organization> getAllOrganizations() throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
    }

    @Override
    public List<Organization> getAllOrganizationsByUserId(Integer userId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
    }

    @Override
    public Organization addOrganization(Organization toAdd) throws OrganizationPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editOrganization(Organization toEdit) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
    }

    @Override
    public void deleteOrganizationById(Integer organizationId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve Organizations from database.");
    }

}
