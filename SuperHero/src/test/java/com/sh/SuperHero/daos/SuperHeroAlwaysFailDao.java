/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.daos;

import com.sh.SuperHero.Dao.SuperDao;
import com.sh.SuperHero.Dao.SuperHeroPersistenceException;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public class SuperHeroAlwaysFailDao implements SuperDao {

    @Override
    public List<SuperHero> getAllSuperHeros() throws SuperHeroPersistenceException {
        throw new SuperHeroPersistenceException("Unable to get SuperHero from database.");
    }

    @Override
    public SuperHero getSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
        throw new SuperHeroPersistenceException("Unable to get SuperHero from database.");
    }
    @Override
    public SuperHero addSuperHero(SuperHero toAdd) throws SuperHeroPersistenceException {
        throw new SuperHeroPersistenceException("Unable to get SuperHero from database.");
    }

    @Override
    public void editSuperHero(SuperHero toEdit) throws SuperHeroPersistenceException {
        throw new SuperHeroPersistenceException("Unable to get SuperHero from database.");
    }

    @Override
    public void deleteSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException {
        throw new SuperHeroPersistenceException("Unable to get SuperHero from database.");
    }
    
}
