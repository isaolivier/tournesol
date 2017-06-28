package com.tournesol.service.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by iolivier on 10/04/2017.
 */
@Entity
@Table(name = "CLIENT")
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Mr/Mme/MrMme/Sté
     */
    @Column(name = "civilite")
    private String civilite;

    /**
     * Nom du client
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Numéro de téléphone fixe
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * Numéro de téléphone mobile.
     */
    @Column(name = "portable")
    private String portable;

    /**
     * Email.
     */
    @Column(name = "email")
    private String email;

    /**
     * Note du client : entier de 1 à 5.
     */
    @Column(name = "note")
    private Integer note;

    /**
     * Appareils du client.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<AppareilEntity> appareils;

    /**
     * Adresse principale du client.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private AdresseEntity adresse;

    public ClientEntity() {
    }

    public ClientEntity(String civilite, String nom, String telephone, String portable, String email, Integer note, List<AppareilEntity> appareils, AdresseEntity adresse) {
        this.civilite = civilite;
        this.nom = nom;
        this.telephone = telephone;
        this.portable = portable;
        this.email = email;
        this.note = note;
        this.appareils = appareils;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public String getCivilite() {
        return civilite;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPortable() {
        return portable;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNote() {
        return note;
    }

    public List<AppareilEntity> getAppareils() {
        return appareils;
    }

    public AdresseEntity getAdresse() {
        return adresse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPortable(String portable) {
        this.portable = portable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public void setAppareils(List<AppareilEntity> appareils) {
        this.appareils = appareils;
    }

    public void setAdresse(AdresseEntity adresse) {
        this.adresse = adresse;
    }
}
