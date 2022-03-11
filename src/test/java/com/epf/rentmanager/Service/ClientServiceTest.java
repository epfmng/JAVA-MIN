package com.epf.rentmanager.Service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientDao clientDao;

    @Test
    public void findAll_test() throws DaoException {
        when(this.clientDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }


    @Test
    public void count_test() throws DaoException {
        when(this.clientDao.count()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.count());
    }

    @Test
    public void create_test() throws DaoException {
        Client client = new Client("Dupont", "Jean", "jean.dupont@email.com", LocalDate.parse("1988-01-22"));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void delete_test() throws DaoException {
        doThrow(new DaoException("Exception occured")).when(clientDao).delete(1);
        assertThrows(ServiceException.class, () -> clientService.delete(1));
    }

    @Test
    public void update_test() throws DaoException {
        Client client = new Client(1,"Dupont", "Jean", "jean.dupont@email.com", LocalDate.parse("1988-01-22"));
        doThrow(new DaoException("Exception occured")).when(clientDao).update(client);
        assertThrows(ServiceException.class, () -> clientService.update(client));
    }
}