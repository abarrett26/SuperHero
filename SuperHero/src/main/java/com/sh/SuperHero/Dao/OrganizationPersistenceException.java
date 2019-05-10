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
public class OrganizationPersistenceException extends Exception {
     
    public OrganizationPersistenceException(String message) {
        super(message);
    }

    public OrganizationPersistenceException(String message, Throwable innerException) {
        super(message, innerException);
    }
}