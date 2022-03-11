package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.create(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Error while creating a rent");
        }
    }

    public long delete(int id) throws ServiceException {
        try {
            return this.reservationDao.delete(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Error while deleting a rent");
        }
    }

    public int count() throws ServiceException {
        try {
            return this.reservationDao.count();
        } catch (DaoException e) {
           e.printStackTrace();
            throw new ServiceException("Error while counting rents");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return this.reservationDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Error while looking for all rents");
        }
    }

    public List<Reservation> findResaByClientId(int idClient) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(idClient);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Error while looking for a rent by client id");
        }
    }

    public List<Reservation> findResaByVehicleId(int idVehicle) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(idVehicle);
        } catch (DaoException e) {
            throw new ServiceException("Error while looking for a rent by vehicle id");
        }
    }
}
