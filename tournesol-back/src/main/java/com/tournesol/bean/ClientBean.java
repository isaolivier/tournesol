package com.tournesol.bean;

/**
 * Created by iolivier on 25/04/2017.
 */
public class ClientBean {

    private Long id;

    private String civilite;

    private String nom;

    private AdresseBean adresse;

    private String telephone;

    private String portable;

    private String email;

    private Integer note;

    public ClientBean() {
    }

    public ClientBean(Long id, String civilite, String nom, AdresseBean adresse, String telephone, String portable, String email, Integer note) {
        this.id = id;
        this.civilite = civilite;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.portable = portable;
        this.email = email;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public AdresseBean getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseBean adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPortable() {
        return portable;
    }

    public void setPortable(String portable) {
        this.portable = portable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
