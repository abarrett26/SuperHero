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
public class InvalidOrganizationDescriptionException extends Exception {
 
   public InvalidOrganizationDescriptionException(String message) {
       super(message);
   }
 
   public InvalidOrganizationDescriptionException(String message, Throwable innerException) {
       super(message, innerException);
   }
   
}