package com.jovan.logistics.iFoodVRP.repository;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:25
 */
@Repository
public interface RestaurantRepository extends MongoRepository<RestaurantEntity, String> {

    Optional<RestaurantEntity> findByName(String name);
}
