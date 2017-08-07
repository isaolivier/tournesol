package com.tournesol.service.repository;

import com.tournesol.service.repository.entity.AppareilEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface AppareilRepository extends PagingAndSortingRepository<AppareilEntity, Long> {

}
