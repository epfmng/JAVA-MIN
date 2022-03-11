package com.epf.rentmanager.persistence;

import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "RentManagerDatabase", true);
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;

        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Client(id INT primary key auto_increment, client_id INT, nom VARCHAR(100), prenom VARCHAR(100), email VARCHAR(100), naissance DATETIME)");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Vehicle(id INT primary key auto_increment, constructeur VARCHAR(100), modele VARCHAR(100), seats TINYINT(255))");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Reservation(id INT primary key auto_increment, idClient INT, foreign key(idClient) REFERENCES Client(id), idVehicle INT, foreign key(idVehicle) REFERENCES Vehicle(id), dateStart DATETIME, dateEnd DATETIME)");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
            	createPreparedStatement = connection.prepareStatement(createQuery);
	            createPreparedStatement.executeUpdate();
	            createPreparedStatement.close();
            }

            // Remplissage de la base avec des Vehicules et des Clients
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, seats) VALUES('Renault', 'Zoé', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, seats) VALUES('Peugeot', '206', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, seats) VALUES('Seat', 'Ibiza', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, seats) VALUES('Nissan', 'Ariya', 4)");
            
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Dupont', 'Jean', 'jean.dupont@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Morin', 'Sabrina', 'sabrina.morin@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Afleck', 'Steeve', 'steeve.afleck@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Rousseau', 'Jacques', 'jacques.rousseau@email.com', '1988-01-22')");

            stmt.execute("INSERT INTO Reservation(idClient, idVehicle, dateStart, dateEnd) VALUES('1', '1', '2021-03-11', '2021-03-16')");
            stmt.execute("INSERT INTO Reservation(idClient, idVehicle, dateStart, dateEnd) VALUES('1', '2', '2020-03-11', '2020-03-16')");
            stmt.execute("INSERT INTO Reservation(idClient, idVehicle, dateStart, dateEnd) VALUES('2', '1', '2019-03-11', '2019-03-16')");
            stmt.execute("INSERT INTO Reservation(idClient, idVehicle, dateStart, dateEnd) VALUES('2', '3', '2018-03-11', '2018-03-16')");


            connection.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
