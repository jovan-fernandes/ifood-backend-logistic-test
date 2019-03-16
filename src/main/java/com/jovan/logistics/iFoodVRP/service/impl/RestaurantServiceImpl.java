package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.RestaurantMapper;
import com.jovan.logistics.iFoodVRP.repository.RestaurantRepository;
import com.jovan.logistics.iFoodVRP.service.RestaurantService;
import com.jovan.logistics.iFoodVRP.utils.PageableUtils;
import com.jovan.logistics.iFoodVRP.web.response.SearchedRestaurantsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:24
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper mapper;

    @Override
    public RestaurantDTO create(RestaurantDTO restaurant) {
        RestaurantEntity restaurantEntity = this.mapper.toEntity(restaurant);
        return this.mapper.toDTO(restaurantRepository.save(restaurantEntity));
    }

    @Override
    public SearchedRestaurantsResponse getAll(Integer pageNumber, Integer numberOfElements) {
        PageRequest pageableForRestaurants = PageableUtils.createPageable(pageNumber, numberOfElements, "name");
        Page<RestaurantEntity> restaurants = restaurantRepository.findAll(pageableForRestaurants);
        return this.mapper.toSearchedRestaurantsResponse(restaurants);
    }

    @Override
    public RestaurantDTO getById(String id) throws EntityNotFoundException {
        return restaurantRepository.findById(id).map(r -> this.mapper.toDTO(r)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public RestaurantDTO update(String id, RestaurantDTO restaurant) throws EntityNotFoundException {
        RestaurantEntity entity = restaurantRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        this.mapper.merge(restaurant, entity);
        return this.mapper.toDTO(restaurantRepository.save(entity));
    }
}
