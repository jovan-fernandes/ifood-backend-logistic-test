package com.jovan.logistics.iFoodVRP.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.geoNear;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 00:03
 */
@Repository
public class CustomOrdersQueryRepository {


    @Autowired
    private MongoTemplate mongoTemplate;


    public List<OrderEntity> findByRestautantOrdersSortedByNearest(final RestaurantEntity restaurant) {
        final NearQuery nearQuery = NearQuery.near(restaurant.getLocation())
                .spherical(true)
                .inKilometers();


        final MatchOperation match = match(Criteria.where("restaurant._id").is(new ObjectId(restaurant.getId())));
        Aggregation aggregation = Aggregation.newAggregation(geoNear(nearQuery, "distance"), match);

        AggregationResults<OrderEntity> results = mongoTemplate.aggregate(aggregation,
                OrderEntity.ORDERS_COLLECTION_NAME, OrderEntity.class);

        return results.getMappedResults();
    }
}
