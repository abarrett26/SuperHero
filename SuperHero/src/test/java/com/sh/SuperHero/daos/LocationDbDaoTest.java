/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sh.SuperHero.Dao.LocationDbDao;
import java.sql.SQLException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author alexbarrett
 */
public class LocationDbDaoTest {
    
   MysqlDataSource ds = new MysqlDataSource();
   JdbcTemplate jdbc;
   LocationDbDao dao;
 
   public LocationDbDaoTest() throws SQLException {
       ds.setServerName("localhost");
       ds.setDatabaseName("SuperHeroTest");
       ds.setUser("root");
       ds.setPassword("password");
       ds.setServerTimezone("America/Chicago");
       ds.setUseSSL(false);
       ds.setAllowPublicKeyRetrieval(true);
 
       this.jdbc = new JdbcTemplate(ds);
       this.dao = new LocationDbDao(jdbc);
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
}
