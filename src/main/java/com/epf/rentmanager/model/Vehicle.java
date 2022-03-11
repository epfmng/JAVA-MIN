package com.epf.rentmanager.model;


public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int seats;

    public Vehicle() {
    }

    public Vehicle(String constructeur, String modele, int seats) {
        this.constructeur = constructeur;
        this.modele = modele;
        this.seats = seats;
    }

    public Vehicle(int id, String constructeur, String modele, int seats) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", seats=" + seats +
                '}';
    }
}
