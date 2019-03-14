package com.jovan.logistics.iFoodVRP.service;


import org.springframework.data.domain.Page;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:35
 */
public interface RestaurantDTO {

    void create(final RestaurantDTO restaurant);

    Page<RestaurantDTO> getAll();

    RestaurantDTO getById(final Long id);

    void update(final RestaurantDTO restaurant);

}
