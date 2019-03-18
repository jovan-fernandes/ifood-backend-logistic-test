package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.repository.ClientRepository;
import com.jovan.logistics.iFoodVRP.repository.OrdersReposiory;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.service.OrderService;
import com.jovan.logistics.iFoodVRP.utils.PageableUtils;
import com.jovan.logistics.iFoodVRP.web.response.SearchedOrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 01:00
 */
@Service
public class OrdersServiceImpl implements OrderService {

    @Autowired
    private OrdersReposiory ordersReposiory;

    @Autowired
    private ClientRepository clientReposiory;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public OrderDTO create(OrderDTO order) throws EntityNotFoundException {

        RestaurantEntity restaurantEntity = getRestaurantEntity(order);
        ClientEntity clientEntity = getClientEntity(order);

        OrderEntity entity = this.mapper.toEntity(order);

        entity.setRestaurant(restaurantEntity);
        entity.setClient(clientEntity);

        OrderDTO orderDTO = this.mapper.toDTO(ordersReposiory.insert(entity));

        orderDTO.getClient().setLat(clientEntity.getLocation().getX());
        orderDTO.getClient().setLon(clientEntity.getLocation().getY());

        orderDTO.getRestaurant().setLat(restaurantEntity.getLocation().getX());
        orderDTO.getRestaurant().setLon(restaurantEntity.getLocation().getY());


        return orderDTO;
    }

    private ClientEntity getClientEntity(OrderDTO order) throws EntityNotFoundException {
        Optional<ClientEntity> byId = clientReposiory.findById(order.getClient().getClientId());
        return byId.isPresent() ? byId.get() :
                clientReposiory.findByName(order.getClient().getClientId())
                        .orElseThrow(EntityNotFoundException::new);

    }

    private RestaurantEntity getRestaurantEntity(OrderDTO order) throws EntityNotFoundException {
        Optional<RestaurantEntity> byId = restaurantRepository.findById(order.getRestaurant().getId());
        return byId.isPresent() ? byId.get() :
                restaurantRepository.findByName(order.getRestaurant().getId())
                        .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public SearchedOrdersResponse getAll(Integer pageNumber, Integer numberOfElements, Optional<Date> deliveryTime) {
        PageRequest pageableForOrders = PageableUtils.createPageable(pageNumber, numberOfElements, "id");

        Page<OrderEntity> orders;

        if (deliveryTime.isPresent()) {
            orders = ordersReposiory.findByDelivery(deliveryTime.get(), pageableForOrders);
        } else {
            orders = ordersReposiory.findAll(pageableForOrders);
        }
        return this.mapper.toSearchedOrdersResponse(orders);
    }

    @Override
    public SearchedOrdersResponse getByRestaurant(Integer pageNumber, Integer numberOfElements, String restaurantName, Optional<Date> deliveryTime) {
        PageRequest pageableForOrders = PageableUtils.createPageable(pageNumber, numberOfElements, "id");

        Page<OrderEntity> orders;

        if (deliveryTime.isPresent()) {
            orders = ordersReposiory.findByRestaurant_nameAndDelivery(restaurantName, deliveryTime.get(), pageableForOrders);
        } else {
            orders = ordersReposiory.findByRestaurant_name(restaurantName, pageableForOrders);
        }

        return this.mapper.toSearchedOrdersResponse(orders);
    }

    @Override
    public OrderDTO getById(String id) throws EntityNotFoundException {
        return ordersReposiory.findById(id).map(o -> this.mapper.toDTO(o)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OrderDTO update(String id, OrderDTO order) throws EntityNotFoundException {
        OrderEntity entity = ordersReposiory.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setRestaurant(getRestaurantEntity(order));
        entity.setClient(getClientEntity(order));

        entity.setDelivery(order.getDelivery());
        entity.setPickup(order.getPickup());

//        this.mapper.merge(order, entity);
        return this.mapper.toDTO(ordersReposiory.save(entity));
    }
}
