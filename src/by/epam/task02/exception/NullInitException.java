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
public class NullInitException extends ProjectException {

    public NullInitException(){}
    
    public NullInitException(String msg) {
        localLog.error("NullInitException " + msg);
        localLog.info(this.getStackTrace());
    }
    
}
