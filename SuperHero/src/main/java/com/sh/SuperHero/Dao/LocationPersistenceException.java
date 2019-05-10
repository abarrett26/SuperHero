/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Dao;

/**
 *
 * @author alexbarrett
 */
public class LocationPersistenceException extends Exception {
     
    public LocationPersistenceException(String message) {
        super(message);
    }

    public LocationPersistenceException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
