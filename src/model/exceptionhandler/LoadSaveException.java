/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.exceptionhandler;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author jongonhur
 */


public class LoadSaveException extends RuntimeException {
    public LoadSaveException(String message) {
        super(message);
    }

    public LoadSaveException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Override
    public String toString() {
        return "ERROR - LoadSaveException: " + getMessage();
}

}
