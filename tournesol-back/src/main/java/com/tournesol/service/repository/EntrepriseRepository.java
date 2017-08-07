package com.tournesol.service.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.tournesol.service.repository.entity.EntrepriseEntity;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface EntrepriseRepository extends PagingAndSortingRepository<EntrepriseEntity, Long> {

}
