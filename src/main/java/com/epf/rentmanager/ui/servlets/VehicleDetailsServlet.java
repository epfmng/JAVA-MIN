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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet {

    public VehicleDetailsServlet() {
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

        List<Client> listClients = new ArrayList<>();
        List<Reservation> listReservations = new ArrayList<>();

        Vehicle Vehicle = new Vehicle();
        try {
            Vehicle = vehicleService.findById(id);
        } catch (ServiceException e) {
        }

        try {
            listReservations = reservationService.findResaByVehicleId(id);
        } catch (ServiceException e) {
        }

        try {
            for (int i = 0; i < listReservations.size(); i++) {
                listClients.add(clientService.findById(listReservations.get(i).getIdVehicle()));
            }
        } catch (ServiceException e) {
        }

        request.setAttribute("constructeur", Vehicle.getConstructeur());
        request.setAttribute("modele", Vehicle.getModele());
        request.setAttribute("seats", Vehicle.getSeats());

        request.setAttribute("reservations", listReservations);
        request.setAttribute("clients", listClients);

        request.setAttribute("Clientsize", listClients.size());
        request.setAttribute("Reservationsize", listReservations.size());


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}