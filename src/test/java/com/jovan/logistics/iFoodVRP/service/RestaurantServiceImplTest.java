package com.jovan.logistics.iFoodVRP.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityAlreadyExistsException;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.service.impl.RestaurantServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 14:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantServiceImplTest extends AbstractIFoodVRPTest {


    @InjectMocks
    private RestaurantServiceImpl restaurantService;


    @Test
    public void createRestaurantWithSucces() {

        RestaurantDTO newRestaurant = RestaurantDTO.builder()
                .lat(RESTAURANT_LAT)
                .lon(RESTAURANT_LON)
                .name(TEST_RESTAURANT_NAME)
                .build();


        when(restaurantRepository.insert(any(RestaurantEntity.class))).thenReturn(buildDefaultRestaurantEntity());

        when(restaurantRepository.findByName(anyString())).thenReturn(Optional.empty());

        RestaurantDTO restaurantDTO = restaurantService.create(newRestaurant);

        assertTrue(StringUtils.isNotEmpty(restaurantDTO.getId()));
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void createRestaurantWithSameName() {
        RestaurantDTO newRestaurant = RestaurantDTO.builder()
                .lat(RESTAURANT_LAT)
                .lon(RESTAURANT_LON)
                .name(TEST_RESTAURANT_NAME)
                .build();


        when(restaurantRepository.insert(any(RestaurantEntity.class))).thenReturn(buildDefaultRestaurantEntity());

        when(restaurantRepository.findByName(anyString())).thenReturn(Optional.of(buildDefaultRestaurantEntity()));

        restaurantService.create(newRestaurant);
    }


    @Test
    public void getRestaurantByIdWithSuccess() throws EntityNotFoundException {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(buildDefaultRestaurantEntity()));

        RestaurantDTO restaurantDTO = restaurantService.getById(RESTAURANT_ID);

        Assertions.assertThat(restaurantDTO).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void getRestaurantWithUnknownId() throws EntityNotFoundException {
        when(restaurantRepository.findById(anyString())).thenReturn(Optional.empty());

        restaurantService.getById("XXXXXX");
    }

    @Test
    public void updateRestaurantWithSuccess() throws EntityNotFoundException {
        RestaurantEntity restaurantEntity = buildDefaultRestaurantEntity();

        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantEntity));

        String newName = "Lanchonete do Zezinho";

        restaurantEntity.setName(newName);

        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);

        RestaurantDTO restaurantDTO = RestaurantDTO.builder().name(newName).lon(00d).lat(10d).build();

        RestaurantDTO updatedDTO = restaurantService.update(RESTAURANT_ID, restaurantDTO);

        assertTrue(RESTAURANT_ID.equals(updatedDTO.getId()) && newName.equals(updatedDTO.getName()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateRestaurantWithUnknownId() throws EntityNotFoundException {
        when(restaurantRepository.findById(anyString())).thenReturn(Optional.empty());

        restaurantService.update("XXXX", new RestaurantDTO());

    }


}
