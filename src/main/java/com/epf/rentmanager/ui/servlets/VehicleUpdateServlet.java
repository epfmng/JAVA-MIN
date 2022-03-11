package com.epf.rentmanager.ui.servlets;

import com.epf.rentmanager.exception.ServiceException;


import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
        private int id;

        @Autowired
        VehicleService vehicleService;

        @Override
        public void init() throws ServletException {
            super.init();
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            id = Integer.valueOf(request.getQueryString().substring(3));
            request.setAttribute("id", id);
            Vehicle vehicle = new Vehicle();
            try {
                vehicle = vehicleService.findById(id);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.setAttribute("constructeur", vehicle.getConstructeur());
            request.setAttribute("modele", vehicle.getModele());
            request.setAttribute("seats", vehicle.getSeats());
            request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String constructeur;
            String modele;
            Integer seats;

            constructeur = request.getParameter("manufacturer");
            modele = request.getParameter("modele");
            seats = Integer.valueOf(request.getParameter("seats"));

            Vehicle vehicle = new Vehicle(id, constructeur, modele, seats);

            try {
                vehicleService.update(vehicle);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("http://localhost:8080/rentmanager/cars");
        }
}
