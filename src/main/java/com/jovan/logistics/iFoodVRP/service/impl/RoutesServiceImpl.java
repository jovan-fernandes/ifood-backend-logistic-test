package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.repository.CustomOrdersQueryRepository;
import com.jovan.logistics.iFoodVRP.repository.OrdersReposiory;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.service.RoutesService;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private OrdersReposiory ordersReposiory;

    @Autowired
    private CustomOrdersQueryRepository customOrdersQueryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public RoutesDTO getRoutes() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        //Recupera os pedidos ordenados pelos mais próximos ao restaurante
        List<List<OrderEntity>> ordersPerRestaurants = restaurants.stream()
                .map(r -> customOrdersQueryRepository.findByRestautantOrdersSortedByNearest(r))
                .filter(r -> !r.isEmpty())
                .collect(Collectors.toList());

        List<List<OrderDTO>> ordersPerDrivers = new ArrayList<>();


        for (List<OrderEntity> restaurantOrders : ordersPerRestaurants) {

            List<List<OrderDTO>> ordersPerDriversByRestaurant = new ArrayList<>();
            ordersPerDriversByRestaurant.add(new ArrayList<>());

            for (OrderEntity order : restaurantOrders) {

                //CAN'T pick up an order before the pickup time
                if (DateFormatter.getNowDefaultTimeZone().before(order.getPickup())) {
                    continue;
                }

                //Handle driver capicity
                if (ordersPerDriversByRestaurant.get(ordersPerDriversByRestaurant.size() - 1).size() < DRIVER_CAPACITY) {
                    ordersPerDriversByRestaurant.get(ordersPerDriversByRestaurant.size() - 1).add(mapper.toDTO(order));
                } else {
                    ordersPerDriversByRestaurant.add(new ArrayList<>());
                    ordersPerDriversByRestaurant.get(ordersPerDriversByRestaurant.size() - 1).add(mapper.toDTO(order));
                }
            }

            //O pedido mais atrasado terá prioridade para o entregador
            for (List<OrderDTO> orderDTOS : ordersPerDriversByRestaurant) {
                orderDTOS.sort(new Comparator<OrderDTO>() {
                    @Override
                    public int compare(OrderDTO o1, OrderDTO o2) {
                        return o1.getDelivery().compareTo(o2.getDelivery());
                    }
                });
            }

            
            ordersPerDrivers.addAll(ordersPerDriversByRestaurant.stream()
                    .filter(r -> !r.isEmpty())
                    .collect(Collectors.toList()));

        }

        return new RoutesDTO(ordersPerDrivers);
    }
}
