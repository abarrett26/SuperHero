/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sh.SuperHero.Dao.LocationDbDao;
import com.sh.SuperHero.Dtos.Location;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author alexbarrett
 */
public class LocationDbDaoTest {
    
   MysqlDataSource ds = new MysqlDataSource();
   JdbcTemplate jdbc;
   LocationDbDao locationDao;
 
   public LocationDbDaoTest() throws SQLException {
       ds.setServerName("localhost");
       ds.setDatabaseName("SuperHeroTest");
       ds.setUser("root");
       ds.setPassword("password");
       ds.setServerTimezone("America/Chicago");
       ds.setUseSSL(false);
       ds.setAllowPublicKeyRetrieval(true);
 
       this.jdbc = new JdbcTemplate(ds);
       this.locationDao = new LocationDbDao(jdbc);
   }
  
 
   
   @BeforeClass
   public static void setUpClass() {
   }
 
   @AfterClass
   public static void tearDownClass() {
   }
 
   @Before
   public void setUp() {
 
       jdbc.update("DELETE FROM superSighting");
       jdbc.update("DELETE FROM sighting");
       jdbc.update("DELETE FROM organization");
       jdbc.update("DELETE FROM location");
       jdbc.update("DELETE FROM superhero");
 
       jdbc.update("insert into location (locationId,name, description, address, latitude, longitude) values (1,'Central Park', 'East side', 'New York City', '10.0', '10.00');");
       jdbc.update("insert into location (locationId,name, description, address, latitude, longitude) values (2,'Golden Gate Bridge', 'landmark', 'San Francisco, CA', '10.0', '10.00');");
       jdbc.update("insert into location (locationId,name, description, address, latitude, longitude) values (3,'Eiffel Tower', 'landmark', 'Paris, France', '10.0', '10.00');");
 
       jdbc.update("insert into organization (organizationId,name, description, address) values(1,'Anti Hero League', 'Heroes suck!', 'New York City'), (2,'Anti Villain League','We hate villains','Los Angeles, California');");
 
       jdbc.update("insert into superhero (superId,name, description, power) values(1,'Batman','Bruce Wayne/Richest man in Gotham','Technology and Wealth');");
       jdbc.update("insert into superhero (superId,name, description, power) values(2,'Superman','The OG Superhero. Classic','Super Strength');");
       jdbc.update("insert into superhero (superId,name, description, power) values(3,'Catwoman','A woman who is a cat. Shes pretty cool','Cat-like Reflexes');");
 
       jdbc.update("insert into sighting (sightingId,locationId,dateOfSighting) values(1,1,'2019-03-10');");
 
       jdbc.update("insert into sighting (sightingId,locationId,dateOfSighting) values(2,1,'2019-03-20');");
 
       jdbc.update("insert into superSighting (superSightingId,superId, sightingId) values (1, 1, 1);");
       jdbc.update("insert into superSighting (superSightingId,superId, sightingId) values (2, 2, 1);");
       jdbc.update("insert into superSighting (superSightingId,superId, sightingId) values (3, 3, 2);");
 
   }
 
   @After
   public void tearDown() {
   }
 
   /**
     * Test of getLocationsById method, of class LocationDbDao.
     */
   @Test
   public void testGetLocationsById() throws Exception {
       Location location = locationDao.getLocationsById(1);
       Assert.assertEquals("Stillwater", location.getLocationName());
       Assert.assertEquals("Stillwater", location.getDescription());
       Assert.assertEquals("Stillwater", location.getAddress());
       Assert.assertEquals(new Double(5.0), location.getLat());
       Assert.assertEquals(new Double(5.00), location.getLng());
   }
 
   /**
     * Test of getAllLocations method, of class LocationDbDao.
     */
   @Test
   public void testGetAllLocations() throws Exception {
       List<Location> allLocations = locationDao.getAllLocations();
       Assert.assertEquals(3, allLocations.size());
       Location location1 = allLocations.get(0);
       Assert.assertEquals("Stillwater", location1.getLocationName());
       Assert.assertEquals("Stillwater", location1.getDescription());
       Assert.assertEquals("Stillwater", location1.getAddress());
       Assert.assertEquals(new Double(5.0), location1.getLat());
       Assert.assertEquals(new Double(5.00), location1.getLng());
   }
 
   /**
     * Test of addLocation method, of class LocationDbDao.
     */
   @Test
   public void testAddLocation() throws Exception {
       Location toAdd = new Location();
       toAdd.setLocationName("add");
       toAdd.setDescription("add");
       toAdd.setAddress("add");
       toAdd.setLat(new Double(1.0));
       toAdd.setLng(new Double(1.0));
 
       locationDao.addLocation(toAdd);
 
       List<Location> allLocations = locationDao.getAllLocations();
       Location location = allLocations.get(3);
       Assert.assertEquals(4, allLocations.size());
       Assert.assertEquals("add", location.getLocationName());
       Assert.assertEquals("add", location.getDescription());
       Assert.assertEquals("add", location.getAddress());
       Assert.assertEquals(new Double(1.0), location.getLat());
       Assert.assertEquals(new Double(1.0), location.getLng());
   }
 
   /**
     * Test of editLocation method, of class LocationDbDao.
     */
   @Test
   public void testEditLocation() throws Exception {
       Location toEdit = locationDao.getLocationsById(1);
       toEdit.setLocationName("editn");
       toEdit.setDescription("editd");
       toEdit.setAddress("edita");
       toEdit.setLat(new Double(1.0));
       toEdit.setLng(new Double(1.0));
 
       locationDao.editLocation(toEdit);
 
       List<Location> allLocations = locationDao.getAllLocations();
       Location location = allLocations.get(0);
       Assert.assertEquals(3, allLocations.size());
       Assert.assertEquals("editn", location.getLocationName());
       Assert.assertEquals("editd", location.getDescription());
       Assert.assertEquals("edita", location.getAddress());
       Assert.assertEquals(new Double(1.0), location.getLat());
       Assert.assertEquals(new Double(1.0), location.getLng());
   }
 
   /**
     * Test of deleteLocationById method, of class LocationDbDao.
     */
   @Test
   public void testDeleteLocationById() throws Exception {
       locationDao.deleteLocationById(2);
       List<Location> allLocations = locationDao.getAllLocations();
       Assert.assertEquals(2, allLocations.size());
       boolean found = false;
       for(Location toCheck : allLocations){
           if(toCheck.getLocationId() == 2){
               found = true;
           }
       }
       Assert.assertEquals(false, found);
   }
 
}