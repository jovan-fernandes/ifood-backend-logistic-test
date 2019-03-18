package com.jovan.logistics.iFoodVRP.service;

import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.web.response.SearchedOrdersResponse;

import java.util.Date;
import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:32
 */
public interface OrderService {


    OrderDTO create(final OrderDTO order) throws EntityNotFoundException;

    SearchedOrdersResponse getAll(Integer pageNumber, Integer numberOfElements, Optional<Date> deliveryTime);

    SearchedOrdersResponse getByRestaurant(Integer pageNumber, Integer numberOfElements, String restaurantName, Optional<Date> deliveryTime);

    OrderDTO getById(final String id) throws EntityNotFoundException;

    OrderDTO update(final String id, final OrderDTO order) throws EntityNotFoundException;
}
