package com.tournesol.bean.output;

import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.ClientBean;

/**
 * Created by iolivier on 25/04/2017.
 */
public class ClientOutputBean extends ClientBean {

    private AdresseBean adresse;

    public AdresseBean getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseBean adresse) {
        this.adresse = adresse;
    }

}
