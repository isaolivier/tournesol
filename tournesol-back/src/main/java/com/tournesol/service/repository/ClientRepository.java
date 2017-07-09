package com.tournesol.service.repository;

import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iolivier on 12/04/2017.
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, Long> {

    @Query("select c from ClientEntity c order by c.nom")
    public Iterable<ClientEntity> findAll();
}
