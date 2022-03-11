package com.epf.rentmanager.ui.servlets;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {

    public ClientDetailsServlet() {
    }

    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.valueOf(request.getQueryString().substring(3));

        List<Vehicle> listVehicles = new ArrayList<>();
        List<Reservation> listReservations = new ArrayList<>();

        Client client = new Client();
        try {
            client = clientService.findById(id);
        } catch (ServiceException e) {
        }

        try {
            listReservations = reservationService.findResaByClientId(id);
        } catch (ServiceException e) {
        }

        try {
            for (int i = 0; i < listReservations.size(); i++) {
                listVehicles.add(vehicleService.findById(listReservations.get(i).getIdVehicle()));
            }
        } catch (ServiceException e) {
        }

        request.setAttribute("nom", client.getName());
        request.setAttribute("prenom", client.getLastname());
        request.setAttribute("email", client.getEmail());
        request.setAttribute("naissance", client.getBirthDate());

        request.setAttribute("rent", listReservations);
        request.setAttribute("vehicle", listVehicles);

        request.setAttribute("Vehiclesize", listVehicles.size());
        request.setAttribute("Reservationsize", listReservations.size());

        request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
