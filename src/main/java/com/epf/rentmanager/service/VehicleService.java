package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while creating a vehicle");
		}
	}

	public long delete(int id) throws ServiceException {
		try {
			return this.vehicleDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while deleting a vehicle");
		}
	}

	public long update(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.update(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while updating a vehicle");
		}
	}

	public Vehicle findById(int id) throws ServiceException {
		try {
			return this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while looking for a vehicle by ID");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while looking for all vehicles");
		}
	}

	public int count() throws ServiceException {
		try {
			return this.vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Error while counting vehicles");
		}
	}

}
