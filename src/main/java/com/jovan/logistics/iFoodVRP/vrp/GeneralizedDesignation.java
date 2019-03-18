package com.jovan.logistics.iFoodVRP.vrp;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.repository.CustomOrdersQueryRepository;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-18 04:22
 */
@AllArgsConstructor
@Service
public class GeneralizedDesignation extends VRPProblem {


    private CustomOrdersQueryRepository customOrdersQueryRepository;


    private RestaurantRepository restaurantRepository;


    private OrderMapper mapper;


    @Override
    public RoutesDTO calculate() {

        List<List<OrderEntity>> ordersPerRestaurants = solveTspProblem(restaurantRepository.findAll());

        List<List<OrderDTO>> ordersPerDrivers = new ArrayList<>();

        for (List<OrderEntity> restaurantOrders : ordersPerRestaurants) {

            List<List<OrderDTO>> ordersPerDriversByRestaurant = new ArrayList<>();
            ordersPerDriversByRestaurant.add(new ArrayList<>());

            for (OrderEntity order : restaurantOrders) {

                if (!canPickup(order)) {
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


            sortByLatest(ordersPerDriversByRestaurant);


            ordersPerDrivers.addAll(ordersPerDriversByRestaurant.stream()
                    .filter(r -> !r.isEmpty())
                    .collect(Collectors.toList()));

        }

        return new RoutesDTO(ordersPerDrivers);
    }


    /**
     * O pedido mais atrasado terá prioridade para o entregador.
     * Pedidos do mesmo cliente são agrupados
     *
     * @param ordersPerDriversByRestaurant
     */
    private void sortByLatest(List<List<OrderDTO>> ordersPerDriversByRestaurant) {
        for (List<OrderDTO> orderDTOS : ordersPerDriversByRestaurant) {
            orderDTOS.sort(new Comparator<OrderDTO>() {
                @Override
                public int compare(OrderDTO o1, OrderDTO o2) {
                    if (o1.getClient().getClientId().equals(o2.getClient().getClientId()))
                        return 0;
                    return o1.getDelivery().compareTo(o2.getDelivery());
                }
            });
        }
    }

    /**
     * Recupera os pedidos dos restaurantes ordenados pelos mais próximos.
     *
     * @param restaurants
     * @return
     */
    private List<List<OrderEntity>> solveTspProblem(List<RestaurantEntity> restaurants) {
        return restaurants.stream()
                .map(r -> customOrdersQueryRepository.findByRestautantOrdersSortedByNearest(r))
                .filter(r -> !r.isEmpty())
                .collect(Collectors.toList());
    }
}
