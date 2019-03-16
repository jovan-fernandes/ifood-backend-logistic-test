package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.repository.OrdersReposiory;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 02:41
 */
@Service
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private OrdersReposiory ordersReposiory;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public RoutesDTO getRoutes() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        List<List<OrderEntity>> routesPerRestaurantes = restaurants.stream()
                .map(r -> ordersReposiory.findByRestaurant_id(r.getId()))
                .filter(r -> !r.isEmpty())
                .collect(Collectors.toList());

        List<List<OrderDTO>> ordersPerDrivers = new ArrayList<>();

        for (List<OrderEntity> restaurantOrders : routesPerRestaurantes) {

            ordersPerDrivers.add(new ArrayList<>());

            for (OrderEntity order : restaurantOrders) {
                if (ordersPerDrivers.get(ordersPerDrivers.size() - 1).size() < 3) {
                    ordersPerDrivers.get(ordersPerDrivers.size() - 1).add(mapper.toDTO(order));
                } else {
                    ordersPerDrivers.add(new ArrayList<>());
                    ordersPerDrivers.get(ordersPerDrivers.size() - 1).add(mapper.toDTO(order));
                }
            }

        }

        return new RoutesDTO(ordersPerDrivers);
    }
}
