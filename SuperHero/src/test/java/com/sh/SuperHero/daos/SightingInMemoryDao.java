/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.SightingDao;
import com.sh.SuperHero.Dao.SightingPersistenceException;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class SightingInMemoryDao implements SightingDao {

    List<Sighting> allSightings = new ArrayList<>();
    List<SuperHero> allSuperHeroes = new ArrayList<>();
    List<Location> allLocations = new ArrayList<>();

    public SightingInMemoryDao() {

        SuperHero super1 = new SuperHero();
        super1.setSuperHeroId(1);
        super1.setSuperHeroName("Alex");
        super1.setDescription("Alexd");
        super1.setSuperPower("Alexp");
        allSuperHeroes.add(super1);

        SuperHero super2 = new SuperHero();
        super2.setSuperHeroId(2);
        super2.setSuperHeroName("Barrett");
        super2.setDescription("Barrettd");
        super2.setSuperPower("Barrettp");
        allSuperHeroes.add(super2);

        Location location1 = new Location();
        location1.setLocationId(1);
        location1.setLocationName("TLN1");
        location1.setDescription("TLD1");
        location1.setAddress("TLA1");
        location1.setLat(1.00);
        location1.setLng(1.00);
        allLocations.add(location1);

        Location location2 = new Location();
        location2.setLocationId(2);
        location2.setLocationName("TLN2");
        location2.setDescription("TLD2");
        location2.setAddress("TLA2");
        location2.setLat(2.00);
        location2.setLng(2.00);
        allLocations.add(location2);

        LocalDate falsedate1 = LocalDate.of(2019, 01, 01);
        Sighting sighting1 = new Sighting();
        sighting1.setSightingId(1);
        sighting1.setLocationId(1);
        sighting1.setLocOfSighting(location1);
        sighting1.setSuperHeroes(allSuperHeroes);
        sighting1.setDate(falsedate1);
        allSightings.add(sighting1);

        LocalDate falsedate2 = LocalDate.of(2019, 02, 01);
        Sighting sighting2 = new Sighting();
        sighting2.setSightingId(2);
        sighting2.setLocationId(2);
        sighting2.setLocOfSighting(location2);
        sighting2.setSuperHeroes(allSuperHeroes);
        sighting2.setDate(falsedate2);
        allSightings.add(sighting2);

    }

    @Override
    public List<Sighting> getAllSightings() throws SightingPersistenceException {
        return allSightings;
    }

    @Override
    public Sighting getSightingById(Integer sightingId) throws SightingPersistenceException {
        Sighting toReturn = null;
        for (Sighting toCheck : allSightings) {
            if (toCheck.getSightingId() == sightingId) {
                toReturn = toCheck;
            }
        }
        return toReturn;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SightingPersistenceException {
        toAdd.setSightingId(generateSightingId(allSightings));
        allSightings.add(toAdd);
        return toAdd;
    }

    @Override
    public void editSighting(Sighting toEdit) throws SightingPersistenceException {
        Sighting memorySighting = getSightingById(toEdit.getSightingId());
        memorySighting.setDate(toEdit.getDate());
        memorySighting.setLocationId(toEdit.getLocationId());
        memorySighting.setLocOfSighting(toEdit.getLocOfSighting());
        memorySighting.setSuperHeroes(allSuperHeroes);
    }

    private int generateSightingId(List<Sighting> allSightings) {
        int toReturn = Integer.MIN_VALUE;
        if (allSightings.isEmpty()) {
            toReturn = 1;
        } else {
            for (Sighting toInspect : allSightings) {
                if (toInspect.getSightingId() > toReturn) {
                    toReturn = toInspect.getSightingId();
                }
            }
            toReturn++;
        }
        return toReturn;
    }

    @Override
    public void deleteSightingById(Integer sightingId) throws SightingPersistenceException {
        getSightingById(sightingId);
        int indexToRemove = Integer.MIN_VALUE;
        for (Sighting toFind : allSightings) {
            if (toFind.getSightingId() == sightingId) {
                indexToRemove = allSightings.indexOf(toFind);
                allSightings.remove(indexToRemove);
                break;
            }
        }
    }

    @Override
    public List<Sighting> get10Sightings() throws SightingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SuperHero> getSuperHeroesForSighting(Integer sightingId) throws SightingPersistenceException {
      List<SuperHero> toReturn = new ArrayList();

        for (SuperHero SuperHero : allSuperHeroes) {
            List<Sighting> allSightings = SuperHero.getSightings();
            for (Sighting sighting : allSightings) {
                if (sighting.getSightingId()== sightingId) {
                    toReturn.add(SuperHero);
                }
            }
        }
        return toReturn;
    }
}
