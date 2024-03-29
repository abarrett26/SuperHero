/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sh.SuperHero.Dao.SightingDbDao;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author alexbarrett
 */
public class SightingDbDaoTest {
    SightingDbDao dao;
    JdbcTemplate jdbc;
    public SightingDbDaoTest() throws SQLException {
       MysqlDataSource ds = new MysqlDataSource();
       ds.setServerName("localhost");
       ds.setDatabaseName("SuperHeroTest");
       ds.setUser("root");
       ds.setPassword("password");
       ds.setServerTimezone("America/Chicago");
       ds.setUseSSL(false);
       ds.setAllowPublicKeyRetrieval(true);
       this.jdbc = new JdbcTemplate(ds);
       this.dao = new SightingDbDao(jdbc);
 
   
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSightingById method, of class SightingDbDao.
     */
    @Test
    public void testGetSightingById() throws Exception {
    }

    /**
     * Test of get10Sightings method, of class SightingDbDao.
     */
    @Test
    public void testGet10Sightings() throws Exception {
    }

    /**
     * Test of getAllSightings method, of class SightingDbDao.
     */
    @Test
    public void testGetAllSightings() throws Exception {
    }

    /**
     * Test of addSighting method, of class SightingDbDao.
     */
    @Test
    public void testAddSighting() throws Exception {
    }

    /**
     * Test of editSighting method, of class SightingDbDao.
     */
    @Test
    public void testEditSighting() throws Exception {
    }

    /**
     * Test of deleteSightingById method, of class SightingDbDao.
     */
    @Test
    public void testDeleteSightingById() throws Exception {
    }

    /**
     * Test of getSuperHeroesForSighting method, of class SightingDbDao.
     */
    @Test
    public void testGetSuperHeroesForSighting() throws Exception {
    }
    
}
