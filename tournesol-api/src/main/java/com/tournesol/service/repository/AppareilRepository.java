package com.tournesol.service.repository;

import com.tournesol.service.entity.AppareilEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by iolivier on 12/04/2017.
 */
@RepositoryRestResource(collectionResourceRel = "appareil", path = "appareil")
public interface AppareilRepository extends PagingAndSortingRepository<AppareilEntity, Long> {

}
