package com.tournesol.bean.input;

import com.tournesol.bean.ClientBean;

public class ClientInputBean extends ClientBean {

    private String placeId;

    private String adresseId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(String adresseId) {
        this.adresseId = adresseId;
    }
}

