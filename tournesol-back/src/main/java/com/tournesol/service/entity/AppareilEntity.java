package com.tournesol.service.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by iolivier on 10/04/2017.
 */
@Entity
@Table(name = "APPAREIL")
public class AppareilEntity implements Serializable {
	
	private static final long serialVersionUID = 2138505980112741865L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Denomination de l'appareil.
     */
    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientId;

    /**
     * Adresse où l'appareil est entretenu.
     */
    @OneToOne(targetEntity = AdresseEntity.class)
    private AdresseEntity adresse;

    /**
     * Denomination de l'appareil.
     */
    @Column(name = "denomination")
    private String denomination;

    /**
     * Marque de l'appareil.
     */
    @Column(name = "marque")
    private String marque;

    /**
     * Numéro de série.
     */
    @Column(name = "numero_serie")
    private String numeroDeSerie;

    /**
     * Date d'installation.
     */
    @Column(name = "date_installation")
    private Date dateInstallation;

    /**
     * Date de mise en service.
     */
    @Column(name = "date_mise_en_service")
    private Date dateMiseEnService;

    @ManyToOne(targetEntity = ClientEntity.class)
    private ClientEntity client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdresseEntity getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseEntity adresse) {
        this.adresse = adresse;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public Date getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(Date dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public Date getDateMiseEnService() {
        return dateMiseEnService;
    }

    public void setDateMiseEnService(Date dateMiseEnService) {
        this.dateMiseEnService = dateMiseEnService;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
