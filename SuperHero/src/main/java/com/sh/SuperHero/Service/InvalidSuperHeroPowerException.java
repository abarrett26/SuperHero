/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.SuperHero.Service;

/**
 *
 * @author alexbarrett
 */
public class InvalidSuperHeroPowerException extends Exception {
 
   public InvalidSuperHeroPowerException(String message) {
       super(message);
   }
 
   public InvalidSuperHeroPowerException(String message, Throwable innerException) {
       super(message, innerException);
   }
   
}