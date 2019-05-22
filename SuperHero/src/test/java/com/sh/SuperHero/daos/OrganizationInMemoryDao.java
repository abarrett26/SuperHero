/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.OrganizationDao;
import com.sh.SuperHero.Dao.OrganizationPersistenceException;
import com.sh.SuperHero.Dtos.Organization;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class OrganizationInMemoryDao implements OrganizationDao {

    List<Organization> allOrganizations = new ArrayList<>();

    List<SuperHero> allSuperHeroes = new ArrayList<>();

    public OrganizationInMemoryDao() {

        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperHeroId(1);
        superHero1.setSuperHeroName("ST1n");
        superHero1.setDescription("ST1d");
        superHero1.setSuperPower("ST1p");
        allSuperHeroes.add(superHero1);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroId(2);
        superHero2.setSuperHeroName("st2n");
        superHero2.setDescription("st2d");
        superHero2.setSuperPower("st2p");
        allSuperHeroes.add(superHero2);

        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("TEAM CCC");
        organization1.setDescription("CCC");
        organization1.setAddress("CCC");
        organization1.setPhoneNumber("7654321");
        organization1.setSuperHeroes(allSuperHeroes);
        allOrganizations.add(organization1);

        Organization organization2 = new Organization();
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("TEAM DDD");
        organization2.setDescription("DDD");
        organization2.setAddress("DDD");
        organization2.setPhoneNumber("1234567");
        organization2.setSuperHeroes(allSuperHeroes);
        allOrganizations.add(organization2);
    }

    @Override
    public List<Organization> getAllOrganizations() throws OrganizationPersistenceException {
        return allOrganizations;
    }

    @Override
    public Organization getOrganizationById(Integer orgId) throws OrganizationPersistenceException {
        Organization toReturn = null;
        for (Organization toCheck : allOrganizations) {
            if (toCheck.getOrganizationId() == orgId) {
                toReturn = toCheck;
            }
        }
        return toReturn;
    }

    @Override
    public Organization addOrganization(Organization toAdd) throws OrganizationPersistenceException {
        toAdd.setOrganizationId(generateOrganizationId(allOrganizations));
        allOrganizations.add(toAdd);
        return toAdd;
    }

    @Override
    public void editOrganization(Organization toEdit) throws OrganizationPersistenceException {

        Organization InMemoryOrganzaion = getOrganizationById(toEdit.getOrganizationId());
        InMemoryOrganzaion.setOrganizationName(toEdit.getOrganizationName());
        InMemoryOrganzaion.setDescription(toEdit.getDescription());
        InMemoryOrganzaion.setAddress(toEdit.getAddress());
        InMemoryOrganzaion.setPhoneNumber(toEdit.getPhoneNumber());
        InMemoryOrganzaion.setSuperHeroes(toEdit.getSuperHeroes());

    }

    private int generateOrganizationId(List<Organization> AllOrganizations) {

        int toReturn = Integer.MIN_VALUE;
        if (AllOrganizations.isEmpty()) {
            toReturn = 1;
        } else {
            for (Organization organ : AllOrganizations) {
                if (organ.getOrganizationId() > toReturn) {
                    toReturn = organ.getOrganizationId();
                }
            }
            toReturn++;
        }
        return toReturn;

    }

    @Override
    public void deleteOrganizationById(Integer organzationId) throws OrganizationPersistenceException {

        deleteOrganizationById(organzationId);
        int removeorganization = Integer.MIN_VALUE;
        for (Organization toFind : allOrganizations) {
            if (toFind.getOrganizationId() == organzationId) {
                removeorganization = allOrganizations.indexOf(toFind);
                allOrganizations.remove(removeorganization);
                break;
            }
        }
    }

}
