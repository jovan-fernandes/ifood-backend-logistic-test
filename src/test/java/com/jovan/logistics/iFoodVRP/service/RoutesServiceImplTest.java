package com.jovan.logistics.iFoodVRP.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.service.impl.RoutesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 16:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RoutesServiceImplTest extends AbstractIFoodVRPTest {

    @InjectMocks
    private RoutesServiceImpl routesService;


    @Override
    public void setup() {
        super.setup();
        mockMongoGetRoutes();
    }

    @Test
    public void getRoutesWithSuccess() {

        RoutesDTO routes = routesService.getRoutes();

        assertTrue(!routes.getOrders().isEmpty());
    }


    @Test
    public void verifyRoutesOverDriverCapacity() {

        RoutesDTO routes = routesService.getRoutes();

        List<List<OrderDTO>> result = routes.getOrders().stream().filter(orderDTOS -> orderDTOS.size() > RoutesService.DRIVER_CAPACITY)
                .collect(Collectors.toList());

        assertTrue(result.isEmpty());
    }


    @Test
    public void verifyMoreThanOneRestaurantPerRoute() {


        RoutesDTO routes = routesService.getRoutes();

        List<List<OrderDTO>> result = routes.getOrders().stream().filter(route -> {
            if (!route.isEmpty()) {
                boolean hasDiferentRestaurant = false;

                String orderRestaurantId = route.get(0).getRestaurant().getId();
                for (int i = 1; i < route.size(); i++) {
                    if (!route.get(i).getRestaurant().getId().equals(orderRestaurantId)) {
                        hasDiferentRestaurant = true;
                        break;
                    }
                }
                return hasDiferentRestaurant;
            }
            return false;
        }).collect(Collectors.toList());

        assertTrue(result.isEmpty());
    }

    private void mockMongoGetRoutes() {
        when(restaurantRepository.findAll()).thenReturn(buildDefaultListRestaurantEntity());

        when(customOrdersQueryRepository.findByRestautantOrdersSortedByNearest(any(RestaurantEntity.class)))
                .thenReturn(buildDefaultListOrderEntity());
    }

}
