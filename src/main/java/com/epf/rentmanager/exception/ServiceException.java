package com.epf.rentmanager.exception;

public class ServiceException extends Exception{
    public ServiceException(){
        super("Erreur dans le Service");
    }

    public ServiceException(String message) {
        super(message);
    }

    public void printStackTrace() {
    }
}
