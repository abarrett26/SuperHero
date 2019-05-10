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
public class InvalidLocationAddressException extends Exception {
 
   public InvalidLocationAddressException(String message) {
       super(message);
   }
 
   public InvalidLocationAddressException(String message, Throwable innerException) {
       super(message, innerException);
   }
   
}