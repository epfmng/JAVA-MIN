package com.epf.rentmanager.dao;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class ClientDao {
	private ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) AS count FROM Client;";

	public long create(Client client) throws DaoException {
		int id = 0;
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY, id = Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, client.getName().toUpperCase(Locale.ROOT));
			pstmt.setString(2, client.getLastname());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));

			pstmt.execute();
		} catch (SQLException e) {
			throw new DaoException("Error while creating a new client");
		}
		return id;
	}
	
	public long delete(int id) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY, id)) {
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch(SQLException e) {
			throw new DaoException("Error while deleting a new client");
		}
		return 0;
	}

	public long update(Client client) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_CLIENT_QUERY)) {
			pstmt.setString(1, client.getName().toUpperCase(Locale.ROOT));
			pstmt.setString(2, client.getLastname());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));
			pstmt.setInt(5, client.getId());
			pstmt.execute();
		} catch(SQLException e) {
			throw new DaoException("Error while updating a new client");
		}
		return 0;
	}

	public Optional<Client> findById(int id) throws DaoException {
		Client client = new Client();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_QUERY);

			pstmt.setInt(1,id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				client.setId(id);
				client.setName(rs.getString("nom"));
				client.setLastname(rs.getString("prenom"));
				client.setEmail(rs.getString("email"));
				client.setBirthDate(rs.getDate("naissance").toLocalDate());
			}

			return Optional.of(client);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<Client> findAll() throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = pstmt.executeQuery();) {

			List<Client> listClients = new ArrayList<>();
			while (rs.next()) {
				listClients.add(new Client(
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getDate("naissance").toLocalDate()));

			}
			return listClients;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error while looking for all clients");
		}
	}

	public int count() throws DaoException {
		int count = 0;
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_CLIENTS_QUERY);){

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Error while counting clients");
		}
		return count;
	}

}
