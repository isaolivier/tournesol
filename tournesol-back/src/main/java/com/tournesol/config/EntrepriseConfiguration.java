package com.tournesol.config;

import com.tournesol.service.entity.EntrepriseEntity;
import com.tournesol.service.repository.EntrepriseRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

@Configuration
public class EntrepriseConfiguration {

    private ConfigurableEnvironment env;

    private final EntrepriseRepository entrepriseRepository;

    @Autowired
    public EntrepriseConfiguration(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Autowired
    public void setConfigurableEnvironment(ConfigurableEnvironment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        MutablePropertySources propertySources = env.getPropertySources();
        Map confMap = new HashMap();

        final EntrepriseEntity entrerpise = entrepriseRepository.findAll().iterator().next();

        confMap.put("configuration", entrerpise);

        propertySources.addFirst(new MapPropertySource("entreprise", confMap));
    }

    public LocalTime getHeureOuverture() {
        return getConfiguration().getHeureOuverture();
    }

    private EntrepriseEntity getConfiguration() {
        return (EntrepriseEntity)env.getPropertySources().get("entreprise").getProperty("configuration");
    }

    public LocalTime getHeureFermeture() {
        return (getConfiguration()).getHeureFermeture();
    }

    public List<DayOfWeek> getJoursOuverture (){
        return getConfiguration().getListJoursOuverture();
    }
}
