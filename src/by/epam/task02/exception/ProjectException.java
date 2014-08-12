/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.exception;

import static by.epam.task02.main.Task02.localLog;

/**
 *
 * @author Helena.Grouk
 */
public class ProjectException extends Exception{
    
    public ProjectException(){}

    public ProjectException(String msg) {
        localLog.error("ProjectException: " + msg);
        localLog.info(this.getStackTrace());
    }
}
