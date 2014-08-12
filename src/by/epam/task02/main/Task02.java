/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.main;

import by.epam.task02.entity.*;
import by.epam.task02.exception.*;
import static by.epam.task02.logic.Localization.projectLocal;
import by.epam.task02.logic.SearchTextEntity.ETYPE;
import static by.epam.task02.logic.SearchTextEntity.findEntitys;
import static by.epam.task02.logic.TextParser.compileText;
import static by.epam.task02.logic.TextParser.textParsing;
import by.epam.task02.logic.WordEntityComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Helena.Grouk
 */
public class Task02 {
    
    private static final String LOG_PROPERTIES_FILE = "Log4J.properties";
    public static Logger localLog = Logger.getLogger("localLoger");
    private static final String FILENAME = "text.txt";

    public static void main(String[] args) {
        
        PropertyConfigurator.configure(LOG_PROPERTIES_FILE);
        projectLocal();
        TextEntity rootEntity;
        try {
            rootEntity = textParsing(FILENAME);
            
            String str = compileText(rootEntity);
            printResult(str);
            printResult(str); //new
            
            Set set = findEntitys(rootEntity, ETYPE.WORD);
            printResult(set);
            
            List list = new ArrayList(set);        
            Collections.sort((List<WordEntity>)list, 
                            WordEntityComparator.stringCharsComparator);
            printResult(list); 
        } catch (ProjectException ex) {
            localLog.fatal("Parsing is failed.");
        }
    }
    
    private static void printResult(String str){
        System.out.println("*********************************************");
        System.out.println(str);
        System.out.println("*********************************************");
    }
    
    private static void printResult(Set set) throws ProjectException{
        if (set == null) {
            throw new ProjectException("Print result fails: set is null.");
        }
        System.out.print("TASK02_6:");
        char ch = '0';
        for (Object s : set) {
            String str = ((WordEntity)s).getWord().toLowerCase();
            if (str.charAt(0) == ch) {
                System.out.print(" " + str);
            } else {
                System.out.print("\n" + str);
                ch = str.charAt(0);
            }
        }
        System.out.println();
    }
    
    private static void printResult(List list) throws ProjectException{
        if (list == null) {
            throw new ProjectException("Print result fails: list is null.");
        }
        System.out.println("TASK02_7:");
        list.stream().forEach((s) -> {
            System.out.println(((WordEntity)s).getWord());
        });
    }
}
    

