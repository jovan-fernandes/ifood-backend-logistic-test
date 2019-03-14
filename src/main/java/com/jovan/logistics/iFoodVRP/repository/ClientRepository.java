package com.jovan.logistics.iFoodVRP.repository;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-13 00:02
 */
@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
}
