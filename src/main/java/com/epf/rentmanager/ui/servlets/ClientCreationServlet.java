package com.epf.rentmanager.ui.servlets;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name;
        String lastname;
        String email;
        LocalDate birthDate;

        name = request.getParameter("last_name");
        lastname = request.getParameter("first_name");
        email = request.getParameter("email");
        birthDate = LocalDate.parse(request.getParameter("naissance"));

        Client client = new Client(name, lastname, email, birthDate);
        try {
            clientService.create(client);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:8080/rentmanager/users");
    }
}
