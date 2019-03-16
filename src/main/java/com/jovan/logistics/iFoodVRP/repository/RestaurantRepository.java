package com.jovan.logistics.iFoodVRP.repository;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:25
 */
public interface RestaurantRepository extends MongoRepository<RestaurantEntity, String> {
}
