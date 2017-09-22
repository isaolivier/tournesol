package com.tournesol.service.repository;

import com.tournesol.service.entity.AdresseEntity;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface AdresseRepository extends PagingAndSortingRepository<AdresseEntity, Long> {

    public List<AdresseEntity> findByPlaceIdIsNull();
}
