package com.jovan.logistics.iFoodVRP.repository;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:26
 */
@Repository
public interface OrdersReposiory extends MongoRepository<OrderEntity, String> {

    Page<OrderEntity> findByRestaurant_name(final String name, final Pageable pageable);

    Page<OrderEntity> findByRestaurant_nameAndDelivery(final String name, final Date delivery, final Pageable pageable);

    Page<OrderEntity> findByDelivery(Date delivery, final Pageable pageable);

    List<OrderEntity> findByRestaurant_id(final String id);

}
