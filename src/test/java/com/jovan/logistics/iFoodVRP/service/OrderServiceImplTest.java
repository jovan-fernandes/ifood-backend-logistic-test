package com.jovan.logistics.iFoodVRP.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.exception.InvalidDateFormatException;
import com.jovan.logistics.iFoodVRP.service.impl.OrdersServiceImpl;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 14:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class OrderServiceImplTest extends AbstractIFoodVRPTest {


    @InjectMocks
    private OrdersServiceImpl orderService;


    @Test
    public void createOrderWithSuccess() throws EntityNotFoundException {

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(buildDefaultClientEntity()));

        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(buildDefaultRestaurantEntity()));

        when(ordersReposiory.insert(any(OrderEntity.class))).thenReturn(buildDefaultOrderEntity());

        OrderDTO newOrder = OrderDTO.builder()
                .client(ClientDTO.builder().clientId(CLIENT_ID).build())
                .restaurant(RestaurantDTO.builder().id(RESTAURANT_ID).build())
                .pickup(getDefaultPickupDateTime())
                .delivery(getDefaultDeliveryDateTime())
                .build();

        OrderDTO orderDTO = orderService.create(newOrder);

        assertTrue(StringUtils.isNotEmpty(orderDTO.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void createOrderForUnknownClient() throws EntityNotFoundException {


        String unknownClientId = "XXXXX";
        when(clientRepository.findById(unknownClientId)).thenReturn(Optional.empty());

        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(buildDefaultRestaurantEntity()));

        when(ordersReposiory.insert(any(OrderEntity.class))).thenReturn(buildDefaultOrderEntity());

        OrderDTO newOrder = OrderDTO.builder()
                .client(ClientDTO.builder().clientId(unknownClientId).build())
                .restaurant(RestaurantDTO.builder().id(RESTAURANT_ID).build())
                .pickup(getDefaultPickupDateTime())
                .delivery(getDefaultDeliveryDateTime())
                .build();


        orderService.create(newOrder);
    }


    @Test(expected = EntityNotFoundException.class)
    public void createOrderForUnknownRestaurant() throws EntityNotFoundException {


        String unknownRestaurantId = "XXXXX";
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(buildDefaultClientEntity()));

        when(restaurantRepository.findById(unknownRestaurantId)).thenReturn(Optional.empty());

        when(ordersReposiory.insert(any(OrderEntity.class))).thenReturn(buildDefaultOrderEntity());

        OrderDTO newOrder = OrderDTO.builder()
                .client(ClientDTO.builder().clientId(unknownRestaurantId).build())
                .restaurant(RestaurantDTO.builder().id(RESTAURANT_ID).build())
                .pickup(getDefaultPickupDateTime())
                .delivery(getDefaultDeliveryDateTime())
                .build();


        orderService.create(newOrder);
    }


    @Test
    public void geOrderByIdWithSuccess() throws EntityNotFoundException {
        when(ordersReposiory.findById(ORDER_ID)).thenReturn(Optional.of(buildDefaultOrderEntity()));

        OrderDTO orderDTO = orderService.getById(ORDER_ID);

        Assertions.assertThat(orderDTO).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOrderWithUnknownId() throws EntityNotFoundException {
        when(ordersReposiory.findById(anyString())).thenReturn(Optional.empty());

        orderService.getById("XXXXXX");
    }

    @Test
    public void updateOrderWithSuccess() throws EntityNotFoundException, InvalidDateFormatException {
        OrderEntity orderEntity = buildDefaultOrderEntity();

        when(ordersReposiory.findById(ORDER_ID)).thenReturn(Optional.of(orderEntity));

        Calendar newDeliveryTime = DateFormatter.formatCalendarStrDefaultPattern("2018-06-05 14:00:30")
                .orElseThrow(InvalidDateFormatException::new);

        orderEntity.setDelivery(newDeliveryTime);

        when(ordersReposiory.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDTO orderDTO = buildDefaultOrderDTO();

        orderDTO.setDelivery(newDeliveryTime);

        OrderDTO updatedDTO = orderService.update(ORDER_ID, orderDTO);

        assertTrue(ORDER_ID.equals(updatedDTO.getId()) && newDeliveryTime.equals(updatedDTO.getDelivery()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateRestaurantWithUnknownId() throws EntityNotFoundException {
        when(ordersReposiory.findById(anyString())).thenReturn(Optional.empty());

        orderService.update("XXXX", new OrderDTO());

    }


    private OrderEntity buildDefaultOrderEntity() {
        return new OrderEntity(ORDER_ID, buildDefaultRestaurantEntity(), buildDefaultClientEntity(),
                getDefaultPickupDateTime(), getDefaultDeliveryDateTime());
    }

    private OrderDTO buildDefaultOrderDTO() {
        return OrderDTO.builder().delivery(getDefaultDeliveryDateTime())
                .id(ORDER_ID)
                .pickup(getDefaultPickupDateTime())
                .restaurant(buildDefaultRestaurantDTO())
                .client(buildDefaultClientDTO())
                .build();
    }

}
