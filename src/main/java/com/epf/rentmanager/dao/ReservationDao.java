package com.epf.rentmanager.dao;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(idClient, idVehicle, dateStart, dateEnd) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, idVehicle, dateStart, dateEnd FROM Reservation WHERE idClient=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, idClient, dateStart, dateEnd FROM Reservation WHERE idVehicle=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, idClient, idVehicle, dateStart, dateEnd FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) AS count FROM Reservation;";


	public long create(Reservation reservation) throws DaoException {
			int id = 0;
			try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY, id = Statement.RETURN_GENERATED_KEYS);){
				pstmt.setInt(1, reservation.getIdClient());
				pstmt.setInt(2, reservation.getIdVehicle());
				pstmt.setDate(3, Date.valueOf(reservation.getDateStart()));
				pstmt.setDate(4, Date.valueOf(reservation.getDateEnd()));
				pstmt.execute();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException("Error while creating a rent");
			}
			return id;
		}

	public int count() throws DaoException {
		int count = 0;

		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_RESERVATIONS_QUERY);){

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count");
			}
			pstmt.execute();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while counting a rent");
		}
		return count;
	}
	
	public long delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);){
			pstmt.setInt(1, id);
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while deleting a rent");
		}
		return id;
	}

	public List<Reservation> findResaByClientId(int idClient) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {

			pstmt.setInt(1, idClient);
			pstmt.execute();

			ResultSet rs = pstmt.executeQuery();
			List<Reservation> listRentClient = new ArrayList<>();
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("id"));
				reservation.setIdClient(idClient);
				reservation.setIdVehicle(rs.getInt("idVehicle"));
				reservation.setDateStart(rs.getDate("dateStart").toLocalDate());
				reservation.setDateEnd(rs.getDate("dateEnd").toLocalDate());
				listRentClient.add(reservation);
			}
			rs.close();
			return listRentClient;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while looking for a rent by ID client");
		}
	}
	
	public List<Reservation> findResaByVehicleId(int idVehicle) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);){
			pstmt.setInt(1, idVehicle);
			pstmt.execute();


			ResultSet rs = pstmt.executeQuery();
			List<Reservation> listRentVehicle = new ArrayList<>();
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("id"));
				reservation.setIdClient(rs.getInt("idVehicle"));
				reservation.setIdVehicle(idVehicle);
				reservation.setDateStart(rs.getDate("dateStart").toLocalDate());
				reservation.setDateEnd(rs.getDate("dateEnd").toLocalDate());
				listRentVehicle.add(reservation);
			}
			rs.close();
			return listRentVehicle;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while looking for a rent by ID vehicle");
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_QUERY);
			 ResultSet rs = pstmt.executeQuery();) {

			List<Reservation> listRents = new ArrayList<>();
			while (rs.next()) {
				listRents.add(new Reservation(
				rs.getInt("id"),
				rs.getInt("idClient"),
				rs.getInt("idVehicle"),
				rs.getDate("dateStart").toLocalDate(),
				rs.getDate("dateEnd").toLocalDate()));
			}
			return listRents;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while looking for all rents");
		}
	}
}
