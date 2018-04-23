package com.example.gggab.certificatifandroid;

/**
 * Created by gggab(Zombietux) on 2018-04-23.
 */
public class Employe {
    private String nom;
    private String prenom;
    private String departement;
    private String taches;
    private String fonction;

    public Employe(String nom, String prenom, String departement, String taches, String fonction) {
        this.nom = nom;
        this.prenom = prenom;
        this.departement = departement;
        this.taches = taches;
        this.fonction = fonction;
    }

    public Employe(String s, String faculty) {
        this.nom = s;
        this.prenom = "";
        this.departement = faculty;
        this.taches = "";
        this.taches = "default";
    }

    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    private void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDepartement() {
        return departement;
    }

    private void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getTaches() {
        return taches;
    }

    private void setTaches(String taches) {
        this.taches = taches;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }
}
