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
public class InvalidOrganizationAddressException extends Exception {
 
   public InvalidOrganizationAddressException(String message) {
       super(message);
   }
 
   public InvalidOrganizationAddressException(String message, Throwable innerException) {
       super(message, innerException);
   }
   
}
