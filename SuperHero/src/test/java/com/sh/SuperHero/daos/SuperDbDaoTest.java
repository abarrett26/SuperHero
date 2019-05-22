/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sh.SuperHero.Dao.SuperDbDao;
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
public class SuperDbDaoTest {
    SuperDbDao dao;
    JdbcTemplate jdbc;
    
    public SuperDbDaoTest() throws SQLException {
       MysqlDataSource ds = new MysqlDataSource();
       ds.setServerName("localhost");
       ds.setDatabaseName("SuperHeroTest");
       ds.setUser("root");
       ds.setPassword("password");
       ds.setServerTimezone("America/Chicago");
       ds.setUseSSL(false);
       ds.setAllowPublicKeyRetrieval(true);
       this.jdbc = new JdbcTemplate(ds);
       this.dao = new SuperDbDao(jdbc);
 
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
     * Test of getAllSuperHeros method, of class SuperDbDao.
     */
    @Test
    public void testGetAllSuperHeros() throws Exception {
    }

    /**
     * Test of getSuperHeroById method, of class SuperDbDao.
     */
    @Test
    public void testGetSuperHeroById() throws Exception {
    }

    /**
     * Test of addSuperHero method, of class SuperDbDao.
     */
    @Test
    public void testAddSuperHero() throws Exception {
    }

    /**
     * Test of editSuperHero method, of class SuperDbDao.
     */
    @Test
    public void testEditSuperHero() throws Exception {
    }

    /**
     * Test of deleteSuperHeroById method, of class SuperDbDao.
     */
    @Test
    public void testDeleteSuperHeroById() throws Exception {
    }

    /**
     * Test of getOrganizationsForSuperHeroes method, of class SuperDbDao.
     */
    @Test
    public void testGetOrganizationsForSuperHeroes() throws Exception {
    }
    
}
