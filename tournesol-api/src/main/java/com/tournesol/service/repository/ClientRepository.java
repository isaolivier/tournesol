package com.tournesol.service.repository;

import com.tournesol.service.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iolivier on 12/04/2017.
 */
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
}
