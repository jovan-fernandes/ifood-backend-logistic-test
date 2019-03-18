package com.jovan.logistics.iFoodVRP.service;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.InvalidDateFormatException;
import com.jovan.logistics.iFoodVRP.mapper.ClientMapper;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.mapper.RestaurantMapper;
import com.jovan.logistics.iFoodVRP.repository.ClientRepository;
import com.jovan.logistics.iFoodVRP.repository.CustomOrdersQueryRepository;
import com.jovan.logistics.iFoodVRP.repository.OrdersReposiory;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 16:36
 */
@Slf4j
public class AbstractIFoodVRPTest {


    public static final String CLIENT_ID = "54asjdbnbeiug892374";
    public static final String TEST_USER_NAME = "TEST USER";
    public static final Double CLIENT_LAT = -23.588055;
    public static final Double CLIENT_LON = -46.632403;


    public static final String RESTAURANT_ID = "98KALSAKJSDHKJ892374";
    public static final String TEST_RESTAURANT_NAME = "TEST RESTAURANT";
    public static final Double RESTAURANT_LAT = -23.890055;
    public static final Double RESTAURANT_LON = -46.453403;


    private static final String RESTAURANT_A_ID = "568KALSAKJSDHKJ892374";
    private static final String TEST_RESTAURANT_A_NAME = "TEST RESTAURANT A";
    private static final Double RESTAURANT_A_LAT = -23.1290055;
    private static final Double RESTAURANT_A_LON = -46.6673403;

    private static final String RESTAURANT_B_ID = "128KALSAKJSDHKJ892374";
    private static final String TEST_RESTAURANT_B_NAME = "TEST RESTAURANT B";
    private static final Double RESTAURANT_B_LAT = -23.670055;
    private static final Double RESTAURANT_B_LON = -46.475403;

    private static final String CLIENT_A_ID = "14asjdbnbeiug892374";
    private static final String TEST_USER_A_NAME = "TEST USER A";
    private static final Double CLIENT_A_LAT = -23.128055;
    private static final Double CLIENT_A_LON = -46.643403;


    private static final String CLIENT_B_ID = "24asjdbnbeiug892374";
    private static final String TEST_USER_B_NAME = "TEST USER B";
    private static final Double CLIENT_B_LAT = -23.1238055;
    private static final Double CLIENT_B_LON = -46.122403;

    public static final String ORDER_ID = "6635553A;KJSDHIUH99";
    public static final String ORDER_ID_A = "1635553A;KJSDHIUH99";
    public static final String ORDER_ID_B = "2635553A;KJSDHIUH99";
    public static final String ORDER_ID_C = "3635553A;KJSDHIUH99";
    public static final String ORDER_ID_D = "4635553A;KJSDHIUH99";


    @Mock
    protected OrdersReposiory ordersReposiory;

    @Mock
    protected RestaurantRepository restaurantRepository;

    @Mock
    protected ClientRepository clientRepository;

    @Mock
    protected CustomOrdersQueryRepository customOrdersQueryRepository;

    @Spy
    private OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Spy
    private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Spy
    private RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);

    @Before
    public void setup() {
        ReflectionTestUtils.setField(orderMapper, "clientMapper", clientMapper);
        ReflectionTestUtils.setField(orderMapper, "restaurantMapper", restaurantMapper);
    }

    protected Date getDefaultPickupDateTime() {
        try {
            return DateFormatter.formatDateToStrDefaultPattern("2018-06-05 13:37:00")
                    .orElseThrow(InvalidDateFormatException::new);
        } catch (InvalidDateFormatException e) {
            log.error("Invalid Format", e);
        }
        return null;
    }

    protected Date getDefaultDeliveryDateTime() {
        try {
            return DateFormatter.formatDateToStrDefaultPattern("2018-06-05 13:54:00")
                    .orElseThrow(InvalidDateFormatException::new);
        } catch (InvalidDateFormatException e) {
            log.error("Invalid Format", e);
        }
        return null;
    }


    protected ClientDTO buildDefaultClientDTO() {
        return ClientDTO.builder()
                .clientId(CLIENT_ID)
                .lat(CLIENT_LAT)
                .lon(CLIENT_LON)
                .name(TEST_USER_NAME)
                .build();
    }

    protected ClientEntity buildDefaultClientEntity() {
        return new ClientEntity(CLIENT_ID, TEST_USER_NAME,
                new GeoJsonPoint(CLIENT_LAT, CLIENT_LON));

    }

    protected RestaurantDTO buildDefaultRestaurantDTO() {
        return RestaurantDTO.builder()
                .id(RESTAURANT_ID)
                .lat(RESTAURANT_LAT)
                .lon(RESTAURANT_LON)
                .name(TEST_RESTAURANT_NAME)
                .build();
    }

    protected RestaurantEntity buildDefaultRestaurantEntity() {
        return new RestaurantEntity(RESTAURANT_ID, TEST_RESTAURANT_NAME,
                new GeoJsonPoint(RESTAURANT_LAT, RESTAURANT_LON));
    }


    protected List<RestaurantEntity> buildDefaultListRestaurantEntity() {
        List<RestaurantEntity> list = new ArrayList<>();

        list.add(buildDefaultRestaurantA());

        list.add(buildDefaultRestaurantB());

        return list;
    }

    protected RestaurantEntity buildDefaultRestaurantA() {
        return new RestaurantEntity(RESTAURANT_A_ID, TEST_RESTAURANT_A_NAME,
                new GeoJsonPoint(RESTAURANT_A_LAT, RESTAURANT_A_LON));
    }

    protected RestaurantEntity buildDefaultRestaurantB() {
        return new RestaurantEntity(RESTAURANT_B_ID, TEST_RESTAURANT_B_NAME,
                new GeoJsonPoint(RESTAURANT_B_LAT, RESTAURANT_B_LON));
    }


    protected ClientEntity buildDefaultClientA() {
        return new ClientEntity(CLIENT_A_ID, TEST_USER_A_NAME,
                new GeoJsonPoint(CLIENT_A_LAT, CLIENT_A_LON));
    }

    protected ClientEntity buildDefaultClientB() {
        return new ClientEntity(CLIENT_B_ID, TEST_USER_B_NAME,
                new GeoJsonPoint(CLIENT_B_LAT, CLIENT_B_LON));
    }


    protected List<OrderEntity> buildDefaultListOrderEntity() {
        List<OrderEntity> list = new ArrayList<>();

        list.add(new OrderEntity(ORDER_ID_A, buildDefaultRestaurantA(), buildDefaultClientA(),
                getDefaultPickupDateTime(), getDefaultDeliveryDateTime()));

        list.add(new OrderEntity(ORDER_ID_B, buildDefaultRestaurantA(), buildDefaultClientB(),
                getDefaultPickupDateTime(), getDefaultDeliveryDateTime()));

        return list;
    }
}
