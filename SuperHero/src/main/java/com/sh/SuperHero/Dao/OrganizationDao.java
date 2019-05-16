/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Organization;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public interface OrganizationDao {

    Organization getOrganizationById(Integer organizationId) throws OrganizationPersistenceException;

    List<Organization> getAllOrganizations() throws OrganizationPersistenceException;

    Organization addOrganization(Organization toAdd) throws OrganizationPersistenceException;

    void editOrganization(Organization toEdit) throws OrganizationPersistenceException;

    void deleteOrganizationById(Integer organizationId) throws OrganizationPersistenceException;
}
