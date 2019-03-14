package com.jovan.logistics.iFoodVRP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDTO implements Serializable {


    private String id;

    private RestaurantDTO restaurant;

    private ClientDTO client;

    private Calendar pickup;

    private Calendar delivery;
}
