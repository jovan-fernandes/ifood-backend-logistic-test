package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.repository.CustomOrdersQueryRepository;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.service.RoutesService;
import com.jovan.logistics.iFoodVRP.vrp.GeneralizedDesignation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 02:41
 */
@Service
@Slf4j
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private CustomOrdersQueryRepository customOrdersQueryRepository;


    @Autowired
    private RestaurantRepository restaurantRepository;


    @Autowired
    private OrderMapper mapper;


    @Override
    public RoutesDTO getRoutes() {
        return getRoutes(new GeneralizedDesignation(customOrdersQueryRepository, restaurantRepository, mapper));
    }
}
