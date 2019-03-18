package com.jovan.logistics.iFoodVRP.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 22:42
 */
@Document(collection = OrderEntity.ORDERS_COLLECTION_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    public static final String ORDERS_COLLECTION_NAME = "orders";


    @Id
    private String id;

    private RestaurantEntity restaurant;

    private ClientEntity client;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date pickup;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date delivery;


}
