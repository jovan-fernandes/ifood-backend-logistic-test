package com.jovan.logistics.iFoodVRP.repository;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Calendar;
import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:26
 */
public interface OrdersReposiory extends MongoRepository<OrderEntity, String> {

    Page<OrderEntity> findByRestaurant_name(String name, Pageable pageable);

    Page<OrderEntity> findByRestaurant_nameAndDelivery(String name, Calendar delivery, Pageable pageable);

    List<OrderEntity> findByRestaurant_id(String id);
}
