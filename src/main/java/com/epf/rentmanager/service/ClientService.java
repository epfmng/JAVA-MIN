package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

	private ClientDao clientDao;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public int count() throws ServiceException {
		try {
			return this.clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while counting clients");
		}
	}
	
	public long create(Client client) throws ServiceException {
			try {
				return this.clientDao.create(client);
			} catch (DaoException e) {
				e.printStackTrace();
				throw new ServiceException("Error while creating clients");
			}
	}

	public long delete(int id) throws ServiceException {
			try {
				return this.clientDao.delete(id);
			} catch (DaoException e) {
				e.printStackTrace();
				throw new ServiceException("Error while deleting clients");
			}
	}

	public long update(Client client) throws ServiceException {
		try {
			return this.clientDao.update(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while updating clients");
		}
	}

	public Client findById(int id) throws ServiceException {
		try {
			return this.clientDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while looking for one client by ID");
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while looking for all clients");
		}
	}



}
