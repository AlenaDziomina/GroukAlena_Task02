/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.logic;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Helena.Grouk
 */
public class Localization {
    
    public static void projectLocal(){
        
        Locale current = Locale.getDefault();
        
        System.out.println("Default: " + current.getCountry() + ", " 
                + current.getDisplayCountry() + ", "
                + current.getLanguage() + ", "
                + current.getDisplayLanguage());
        
        Locale current2 = new Locale("en", "US");
        ResourceBundle rb2 = ResourceBundle.getBundle(
                "properties//text_en_us", current2);
        System.out.println("Locale1: " + current2.getCountry() + ", " 
                + current2.getDisplayCountry() + ", "
                + current2.getLanguage() + ", "
                + current2.getDisplayLanguage());
        System.out.println("In Locale1: " + rb2.getString("k1"));
        
        
        Locale current3 = new Locale("by", "BY");
        ResourceBundle rb3 = ResourceBundle.getBundle(
                "properties//text_by_BY", current3);
        System.out.println("Locale2: " + current3.getCountry() + ", " 
                + current3.getDisplayCountry() + ", "
                + current3.getLanguage() + ", "
                + current3.getDisplayLanguage());
        
        System.out.println("In Locale2: " + rb3.getString("k1"));
        
    }
    
}
