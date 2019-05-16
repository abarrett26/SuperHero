/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Service;

import com.sh.SuperHero.Dao.LocationDao;
import com.sh.SuperHero.Dao.LocationPersistenceException;
import com.sh.SuperHero.Dao.OrganizationDao;
import com.sh.SuperHero.Dao.OrganizationPersistenceException;
import com.sh.SuperHero.Dao.SightingDao;
import com.sh.SuperHero.Dao.SightingPersistenceException;
import com.sh.SuperHero.Dao.SuperDao;
import com.sh.SuperHero.Dao.SuperHeroPersistenceException;
import com.sh.SuperHero.Dtos.InvalidIdException;
import com.sh.SuperHero.Dtos.Location;
import com.sh.SuperHero.Dtos.Organization;
import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexbarrett
 */
@Service
public class SHService {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperDao superDao;

    @Autowired
    SightingDao sightingDao;

    public SHService() {

    }

    public SHService(LocationDao locationDao, OrganizationDao organizationDao, SuperDao superDao, SightingDao sightingDao) {
        this.locationDao = locationDao;
        this.organizationDao = organizationDao;
        this.superDao = superDao;
        this.sightingDao = sightingDao;
    }

   

    public Response<List<Location>> getAllLocations() {
        Response<List<Location>> response = new Response<List<Location>>();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            response.setSuccess(true);
            response.setResponseData(allLocations);
        } catch (LocationPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Organization>> getAllOrganizations() {
        Response<List<Organization>> response = new Response<List<Organization>>();
        try {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();
            response.setSuccess(true);
            response.setResponseData(allOrganizations);
        } catch (OrganizationPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Sighting>> getAllSightings() {
        Response<List<Sighting>> response = new Response<List<Sighting>>();
        try {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            response.setSuccess(true);
            response.setResponseData(allSightings);
        } catch (SightingPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<SuperHero>> getAllSuperHeroes() {
        Response<List<SuperHero>> response = new Response<List<SuperHero>>();
        try {
            List<SuperHero> allSuperHeroes = superDao.getAllSuperHeros();
            response.setSuccess(true);
            response.setResponseData(allSuperHeroes);
        } catch (SuperHeroPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<SuperHero> getSuperHeroById(Integer superHeroId) {
        Response<SuperHero> response = new Response<SuperHero>();
        try {
            List<SuperHero> allSupers = superDao.getAllSuperHeros();
            validateSuperHeroId(superHeroId, allSupers);
            SuperHero superHero = superDao.getSuperHeroById(superHeroId);
            response.setSuccess(true);
            response.setResponseData(superHero);
        } catch (SuperHeroPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Location> getLocationById(Integer locationId) {
        Response<Location> response = new Response<Location>();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(locationId, allLocations);
            Location location = locationDao.getLocationsById(locationId);
            response.setSuccess(true);
            response.setResponseData(location);
        } catch (LocationPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Location> addLocation(Location toAdd) {
        Response<Location> response = new Response<Location>();
        try {
            toAdd = locationDao.addLocation(toAdd);
            validateLocation(toAdd);
            response.setSuccess(true);
            response.setResponseData(toAdd);
        } catch (LocationPersistenceException | InvalidLocationAddressException | InvalidLatException | InvalidLngException | InvalidLocationDescriptionException | InvalidLocationNameException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Location> deleteLocationById(Integer locationId) {
        Response<Location> response = new Response<Location>();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(locationId, allLocations);
            locationDao.deleteLocationById(locationId);
            response.setSuccess(true);
        } catch (LocationPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Location> editLocation(Location toEdit) {
        Integer locationId = toEdit.getLocationId();
        Response<Location> response = new Response<Location>();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(locationId, allLocations);
            validateLocation(toEdit);
            locationDao.editLocation(toEdit);
            response.setSuccess(true);
            response.setResponseData(toEdit);
        } catch (LocationPersistenceException | InvalidIdException | InvalidLocationNameException | InvalidLocationDescriptionException | InvalidLocationAddressException | InvalidLngException | InvalidLatException ex) { 
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<SuperHero> deleteSuperHeroById(Integer superHeroId) {
        Response<SuperHero> response = new Response<SuperHero>();
        try {
            List<SuperHero> allSuperHeroes = superDao.getAllSuperHeros();
            validateSuperHeroId(superHeroId, allSuperHeroes);
            superDao.deleteSuperHeroById(superHeroId);
            response.setSuccess(true);
        } catch (SuperHeroPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Organization> getOrganizationById(Integer organizationId) {
        Response<Organization> response = new Response<Organization>();
        try {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();
            validateOrganizationId(organizationId, allOrganizations);
            Organization organization = organizationDao.getOrganizationById(organizationId);
            response.setSuccess(true);
            response.setResponseData(organization);
        } catch (OrganizationPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<SuperHero> addSuperHero(SuperHero toAdd) {
        Response<SuperHero> response = new Response<SuperHero>();
        try {
            toAdd = superDao.addSuperHero(toAdd);
            validateSuperHero(toAdd);
            response.setSuccess(true);
            response.setResponseData(toAdd);
        } catch (SuperHeroPersistenceException | InvalidSuperHeroDescriptionException | InvalidSuperHeroNameException | InvalidSuperHeroPowerException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<SuperHero> editSuperHero(SuperHero toEdit) {
        Response<SuperHero> response = new Response<SuperHero>();
        Integer superHeroId = toEdit.getSuperHeroId();
        try {
            List<SuperHero> allSuperHeroes = superDao.getAllSuperHeros();
            validateSuperHeroId(superHeroId, allSuperHeroes);
            superDao.editSuperHero(toEdit);
            validateSuperHero(toEdit);
            response.setSuccess(true);
            response.setResponseData(toEdit);
        } catch (SuperHeroPersistenceException | InvalidIdException | InvalidSuperHeroDescriptionException | InvalidSuperHeroNameException | InvalidSuperHeroPowerException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Sighting> getSightingsById(Integer sightingId) {
        Response<Sighting> response = new Response<Sighting>();
        try {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            validateSightingId(sightingId, allSightings);
            Sighting sighting = sightingDao.getSightingById(sightingId);
            response.setSuccess(true);
            response.setResponseData(sighting);
        } catch (SightingPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Sighting> addSighting(Sighting toAdd) {
        Response<Sighting> response = new Response<Sighting>();
        try {
            validateSighting(toAdd);
            toAdd = sightingDao.addSighting(toAdd);
            response.setSuccess(true);
            response.setResponseData(toAdd);
        } catch (SightingPersistenceException | InvalidDateException | InvalidLocationException | InvalidSuperHeroPowerException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Sighting> editSighting(Sighting toEdit) {
        Response<Sighting> response = new Response<Sighting>();
        Integer sightingId = toEdit.getSightingId();
        try {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            validateSightingId(sightingId, allSightings);
            validateSighting(toEdit);
            sightingDao.editSighting(toEdit);
            response.setSuccess(true);
            response.setResponseData(toEdit);
        } catch (SightingPersistenceException | InvalidIdException | InvalidDateException | InvalidLocationException | InvalidSuperHeroPowerException  ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;

    }

    public Response<Sighting> deleteSightingsById(Integer sightingId) {
        Response<Sighting> response = new Response<Sighting>();
        try {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            validateSightingId(sightingId, allSightings);
            sightingDao.deleteSightingById(sightingId);
            response.setSuccess(true);
        } catch (SightingPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Organization> addOrganization(Organization toAdd) {
        Response<Organization> response = new Response<Organization>();
        try {
            toAdd = organizationDao.addOrganization(toAdd);
            validateOrganization(toAdd);
          
            response.setSuccess(true);
            response.setResponseData(toAdd);
        } catch (OrganizationPersistenceException | InvalidOrganizationAddressException | InvalidOrganizationDescriptionException | InvalidOrganizationNameException | InvalidOrganizationPhoneNumberException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Organization> editOrganization(Organization toEdit)  {
        Integer organizationId = toEdit.getOrganizationId();
        Response<Organization> response = new Response<Organization>();
        try {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();
            validateOrganizationId(organizationId, allOrganizations);
            validateOrganization(toEdit);
            organizationDao.editOrganization(toEdit);
            response.setSuccess(true);
            response.setResponseData(toEdit);
        } catch (OrganizationPersistenceException | InvalidIdException | InvalidOrganizationAddressException | InvalidOrganizationDescriptionException | InvalidOrganizationNameException | InvalidOrganizationPhoneNumberException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Organization> deleteOrganizationById(Integer organizationId) {
        Response<Organization> response = new Response<Organization>();
        try {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();
            validateOrganizationId(organizationId, allOrganizations);
            organizationDao.deleteOrganizationById(organizationId);
            response.setSuccess(true);
        } catch (OrganizationPersistenceException | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    private void validateLocationId(Integer locationId, List<Location> allLocations) throws InvalidIdException {
        boolean found = false;
        for (Location toCheck : allLocations) {
            if (locationId == toCheck.getLocationId()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidIdException("The Location does not exist in the database");
        }
    }

    private void validateOrganizationId(Integer organizationId, List<Organization> allOrganizations) throws InvalidIdException {
        boolean found = false;
        for (Organization toCheck : allOrganizations) {
            if (organizationId == toCheck.getOrganizationId()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidIdException("The Organization does not exist in the database");
        }
    }

    private void validateSightingId(Integer sightingId, List<Sighting> allSightings) throws InvalidIdException {
        boolean found = false;
        for (Sighting toCheck : allSightings) {
            if (sightingId == toCheck.getSightingId()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidIdException("The Sighting does not exist in the database");
        }
    }

    private void validateSuperHeroId(Integer superHeroId, List<SuperHero> allSupers) throws InvalidIdException {
        boolean found = false;
        for (SuperHero toCheck : allSupers) {
            if (superHeroId == toCheck.getSuperHeroId()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidIdException("The SuperHero does not exist in the database");
        }
    }

    private void validateLocation(Location toAdd) throws InvalidLocationNameException, InvalidLocationDescriptionException, InvalidLocationAddressException, InvalidLngException, InvalidLatException {
        if (toAdd.getAddress().equals("")) {
            throw new InvalidLocationAddressException("The Location's address cannot be blank.");
        }
        if (toAdd.getDescription().equals("")) {
            throw new InvalidLocationDescriptionException("The Location's description cannot be blank.");
        }
        if (toAdd.getLocationName().equals("")) {
            throw new InvalidLocationNameException("The Location's name cannot be blank.");
        }
        if (toAdd.getLng() == null) {
            throw new InvalidLngException("The Longitutude must be entered.");
        }
        if (toAdd.getLat()== null) {
            throw new InvalidLatException("The Latitude must be entered.");
        }
        if (toAdd.getLat() > 90 || toAdd.getLat() < -90) {
            throw new InvalidLatException("Enter between 90 & -90 LAT");
        }
        if (toAdd.getLng()> 180 || toAdd.getLat() < -180) {
            throw new InvalidLngException("Enter between 180 & -180 LNG");
        }
        
    }

    private void validateOrganization(Organization toEdit) throws InvalidOrganizationNameException, InvalidOrganizationDescriptionException, InvalidOrganizationAddressException, InvalidOrganizationPhoneNumberException {
        if (toEdit.getAddress().equals("")) {
            throw new InvalidOrganizationAddressException("The Organization's address cannot be blank.");
        }

        if (toEdit.getDescription().equals("")) {
            throw new InvalidOrganizationDescriptionException("The Organization's description cannot be blank.");
        }

        if (toEdit.getOrganizationName().equals("")) {
            throw new InvalidOrganizationNameException("The Organization's name cannot be blank.");
        }

        if (toEdit.getPhoneNumber().equals("")) {
            throw new InvalidOrganizationPhoneNumberException("Phone Number cannot be blank...");
        }
    }

    private void validateSighting(Sighting toEdit) throws InvalidDateException, InvalidLocationException, InvalidSuperHeroPowerException  {
        LocalDate rightNow = LocalDate.now();
        if (toEdit.getDate().isAfter(rightNow)) {
            throw new InvalidDateException("The Date should be the past");
        }
        if (toEdit.getLocOfSighting().equals("")) {
            throw new InvalidLocationException("The Location name cannot be blank");
        }
        if (toEdit.getSuperHeroes().equals("")) {
            throw new InvalidSuperHeroPowerException("The SuperHero cannot be blank");
        }
    }

    private void validateSuperHero(SuperHero toEdit) throws InvalidSuperHeroPowerException, InvalidSuperHeroNameException, InvalidSuperHeroDescriptionException {
        if (toEdit.getDescription().equals("")) {
            throw new InvalidSuperHeroDescriptionException("The SuperHero's description cannot be blank");
        }
        if (toEdit.getSuperHeroName().equals("")) {
            throw new InvalidSuperHeroNameException("The SuperHero's name cannot not be blank");
        }
        if (toEdit.getSuperPower().equals("")) {
            throw new InvalidSuperHeroPowerException("The SuperPower cannot be blank");
        }
    }

    public Response<List<Sighting>> get10Sightings() {
        Response<List<Sighting>> response = new Response<List<Sighting>>();
       try {
           List<Sighting> tenSightings = sightingDao.get10Sightings();
           response.setSuccess(true);
           response.setResponseData(tenSightings);
       } catch (SightingPersistenceException ex) {
           response.setSuccess(false);
           response.setMessage(ex.getMessage());
       }
       return response;
   }

}
