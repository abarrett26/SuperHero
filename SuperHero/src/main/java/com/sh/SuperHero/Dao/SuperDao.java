/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

import com.sh.SuperHero.Dtos.Sighting;
import com.sh.SuperHero.Dtos.SuperHero;
import java.util.List;

/**
 *
 * @author alexbarrett
 */
public interface SuperDao {
    
    List<SuperHero> getAllSuperHeros() throws SuperHeroPersistenceException;
    
    SuperHero getSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException;

    SuperHero addSuperHero(SuperHero toAdd) throws SuperHeroPersistenceException;

    void editSuperHero(SuperHero toEdit) throws SuperHeroPersistenceException;

    void deleteSuperHeroById(Integer superHeroId) throws SuperHeroPersistenceException;
}
