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

@WebServlet("/rents/create")
public class ReservationCreationServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> listClient = new ArrayList<>();
        try {
            listClient = clientService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("listUsers", listClient);

        List<Vehicle> listVehicle = new ArrayList<>();
        try {
            listVehicle = vehicleService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("listVehicles", listVehicle);

        request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer idClient;
        Integer idVehicle;
        LocalDate dateStart;
        LocalDate dateEnd;

        idClient = Integer.valueOf(request.getParameter("client"));
        idVehicle = Integer.valueOf(request.getParameter("car"));
        dateStart = LocalDate.parse(request.getParameter("begin"));
        dateEnd = LocalDate.parse(request.getParameter("end"));

        Reservation reservation = new Reservation(idClient, idVehicle, dateStart, dateEnd);
       try {
            reservationService.create(reservation);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:8080/rentmanager/rents");
    }
}