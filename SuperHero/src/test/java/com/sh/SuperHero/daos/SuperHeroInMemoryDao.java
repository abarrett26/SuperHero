/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.SuperDao;
import com.sh.SuperHero.Dao.SuperHeroPersistenceException;
import com.sh.SuperHero.Dtos.Organization;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class SuperHeroInMemoryDao implements SuperDao {

    List<SuperHero> allSuperHeroes = new ArrayList<>();
    List<Organization> allOrganizations = new ArrayList<>();

    public SuperHeroInMemoryDao() {

        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("AAA");
        organization1.setDescription("AAA");
        organization1.setAddress("AAA");
        organization1.setPhoneNumber("1234567");
        allOrganizations.add(organization1);

        Organization organization2 = new Organization();
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("BBB");
        organization2.setDescription("BBB");
        organization2.setAddress("BBB");
        organization2.setPhoneNumber("1234567");
        allOrganizations.add(organization2);

        SuperHero super1 = new SuperHero();
        super1.setSuperHeroId(1);
        super1.setSuperHeroName("SSS");
        super1.setDescription("SSS");
        super1.setSuperPower("SSS");
        super1.setOrganizations(allOrganizations);
        allSuperHeroes.add(super1);

        SuperHero super2 = new SuperHero();
        super2.setSuperHeroId(2);
        super2.setSuperHeroName("WWW");
        super2.setDescription("WWW2");
        super2.setSuperPower("WWW");
        super2.setOrganizations(allOrganizations);
        allSuperHeroes.add(super2);

    }

    @Override
    public List<SuperHero> getAllSuperHeros() throws SuperHeroPersistenceException {
        return allSuperHeroes;
    }

    @Override
    public SuperHero getSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
        SuperHero toReturn = null;
        for (SuperHero toCheck : allSuperHeroes) {
            if (toCheck.getSuperHeroId() == superHeroId) {
                toReturn = toCheck;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public SuperHero addSuperHero(SuperHero toAdd) throws SuperHeroPersistenceException {
        toAdd.setSuperHeroId(addSuperHeroId(allSuperHeroes));
        allSuperHeroes.add(toAdd);
        return toAdd;
    }

    @Override
    public void editSuperHero(SuperHero toEdit) throws SuperHeroPersistenceException {
        SuperHero InmemorySuperHero = getSuperHeroById(toEdit.getSuperHeroId());
        InmemorySuperHero.setSuperHeroName(toEdit.getSuperHeroName());
        InmemorySuperHero.setDescription(toEdit.getDescription());
        InmemorySuperHero.setSuperPower(toEdit.getSuperPower());
        InmemorySuperHero.setOrganizations(toEdit.getOrganizations());
    }

    @Override
    public void deleteSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
        getSuperHeroById(superHeroId);
        int indexToRemove = Integer.MIN_VALUE;
        for (SuperHero superH : allSuperHeroes) {
            if (superH.getSuperHeroId() == superHeroId) {
                indexToRemove = allSuperHeroes.indexOf(superH);
                allSuperHeroes.remove(indexToRemove);
                break;
            }
        }
    }

    @Override
    public List<Organization> getOrganizationsForSuperHeroes(Integer superHeroId) throws SuperHeroPersistenceException {
        List<Organization> toReturn = new ArrayList();

        for (Organization Organization : allOrganizations) {
            List<SuperHero> allSuperHeroes = Organization.getSuperHeroes();
            for (SuperHero superH : allSuperHeroes) {
                if (superH.getSuperHeroId() == superHeroId) {
                    toReturn.add(Organization);
                }
            }
        }
        return toReturn;
    }

    public int addSuperHeroId(List<SuperHero> allSupers) {
        int toReturn = Integer.MIN_VALUE;
        if (allSupers.isEmpty()) {
            toReturn = 1;
        } else {
            for (SuperHero superH : allSupers) {
                if (superH.getSuperHeroId() > toReturn) {
                    toReturn = superH.getSuperHeroId();
                }
            }
            toReturn++;
        }
        return toReturn;
    }
}
