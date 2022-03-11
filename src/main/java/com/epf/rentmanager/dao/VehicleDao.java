package com.epf.rentmanager.dao;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VehicleDao {
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, seats) VALUES(?, ?, ?);";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, seats = ? WHERE id=?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, seats FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, seats FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";


	public long create(Vehicle vehicle) throws DaoException {
		int id = 0;
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY, id = Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, vehicle.getConstructeur());
			pstmt.setString(2, vehicle.getModele());
			pstmt.setInt(3, vehicle.getSeats());

			pstmt.execute();
		} catch (SQLException e) {
			throw new DaoException("Error while creating a new vehicle");
		}
		return id;
	}

	public long update(Vehicle vehicle) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_VEHICLE_QUERY)) {
			pstmt.setString(1, vehicle.getConstructeur());
			pstmt.setString(2, vehicle.getModele());
			pstmt.setInt(3, vehicle.getSeats());
			pstmt.setInt(4, vehicle.getId());
			pstmt.execute();
		} catch(SQLException e) {
			throw new DaoException("Error while updating a new vehicle");
		}
		return 0;
		}

	public long delete(int id) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY, id)) {
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch(SQLException e) {
			throw new DaoException("Error while deleting a new vehicle");
		}
		return 0;
	}

	public Optional<Vehicle> findById(int id) throws DaoException {
		Vehicle vehicle = new Vehicle();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_QUERY);

			pstmt.setInt(1,id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				vehicle.setId(id);
				vehicle.setConstructeur(rs.getString("constructeur"));
				vehicle.setModele(rs.getString("modele"));
				vehicle.setSeats(rs.getInt("seats"));
			}

			return Optional.of(vehicle);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet rs = pstmt.executeQuery();) {

			List<Vehicle> listVehicles = new ArrayList<>();
			while (rs.next()) {
				listVehicles.add(new Vehicle(
						rs.getInt("id"),
						rs.getString("constructeur"),
						rs.getString("modele"),
						rs.getInt("seats")));
			}
			return listVehicles;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while looking for all vehicles");
		}
	}

	public int count() throws DaoException{
		int count = 0;
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_VEHICLES_QUERY);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while counting vehicles");
		}
		return count;
	}
}
