package com.jovan.logistics.iFoodVRP.service;


import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.web.response.SearchedRestaurantsResponse;
import org.springframework.data.domain.Page;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:35
 */
public interface RestaurantService {

    RestaurantDTO create(final RestaurantDTO restaurant);

    SearchedRestaurantsResponse getAll(Integer pageNumber, Integer numberOfElements);

    RestaurantDTO getById(final String id) throws EntityNotFoundException;

    RestaurantDTO update(final String id, final RestaurantDTO restaurant) throws EntityNotFoundException;

}
