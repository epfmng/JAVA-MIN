package com.epf.rentmanager.main;


import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
            ClientService clientService = context.getBean(ClientService.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
            System.out.println(clientService);
            System.out.println(vehicleService);
    }
}
