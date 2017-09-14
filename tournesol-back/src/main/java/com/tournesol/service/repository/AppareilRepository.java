package com.tournesol.service.repository;

import com.tournesol.service.entity.AppareilEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface AppareilRepository extends PagingAndSortingRepository<AppareilEntity, Long> {

    @Query("select a from AppareilEntity a where a.clientId = :clientId")
    List<AppareilEntity> findAppareilEntities(@Param("clientId") String clientId);
}
