package com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
@Table(name = "CLIENT")
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

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
    private int note;

    /**
     * Appareils du client.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<AppareilEntity> appareils;

}
