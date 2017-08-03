package com.tournesol.service.repository;

import com.tournesol.service.entity.RendezVousEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface RendezVousRepository extends PagingAndSortingRepository<RendezVousEntity, Long> {

    @Query("select r from RendezVousEntity r where r.eventId in :eventIds")
    List<RendezVousEntity> findRendezVousEntities(@Param("eventIds") List<String> eventIds);
}
