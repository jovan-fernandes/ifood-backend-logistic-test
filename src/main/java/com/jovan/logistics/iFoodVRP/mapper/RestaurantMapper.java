package com.jovan.logistics.iFoodVRP.mapper;

import com.jovan.logistics.iFoodVRP.domain.RestaurantEntity;
import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.web.request.RestaurantRequest;
import com.jovan.logistics.iFoodVRP.web.response.RestaurantResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedRestaurantsResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-14 00:59
 */
@Mapper
public abstract class RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    public abstract RestaurantDTO toDTO(RestaurantRequest request);

    @Mappings({
            @Mapping(target = "lat", ignore = true),
            @Mapping(target = "lon", ignore = true)
    })
    public abstract RestaurantDTO toDTO(RestaurantEntity entity);

    @Mapping(target = "location", ignore = true)
    public abstract RestaurantEntity toEntity(RestaurantDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    public abstract void merge(final RestaurantDTO source, @MappingTarget RestaurantEntity target);


    public abstract RestaurantResponse toResponse(RestaurantDTO dto);


    @Mappings({
            @Mapping(target = "lat", ignore = true),
            @Mapping(target = "lon", ignore = true)
    })
    public abstract RestaurantResponse toResponse(RestaurantEntity entity);


    public SearchedRestaurantsResponse toSearchedRestaurantsResponse(Page<RestaurantEntity> pageable) {
        return new SearchedRestaurantsResponse(pageable.getTotalElements(), pageable.getTotalPages(),
                toRestaurantResponse(pageable.getContent()));
    }

    public List<RestaurantResponse> toRestaurantResponse(List<RestaurantEntity> restaurants) {
        return restaurants.stream().map(r -> this.toResponse(r)).collect(Collectors.toList());
    }

    @AfterMapping
    protected void addLocation(@MappingTarget RestaurantEntity restaurantEntity, RestaurantDTO dto) {
        if (dto.getLat() != null && dto.getLon() != null) {
            restaurantEntity.setLocation(new GeoJsonPoint(dto.getLat(), dto.getLon()));
        }
    }

    @AfterMapping
    protected void addLocation(@MappingTarget RestaurantResponse response, RestaurantEntity restaurantEntity) {
        if (restaurantEntity.getLocation() != null) {
            response.setLat(restaurantEntity.getLocation().getX());
            response.setLon(restaurantEntity.getLocation().getY());
        }
    }

    @AfterMapping
    protected void addLocation(@MappingTarget RestaurantDTO response, RestaurantEntity restaurantEntity) {
        if (restaurantEntity.getLocation() != null) {
            response.setLat(restaurantEntity.getLocation().getX());
            response.setLon(restaurantEntity.getLocation().getY());
        }
    }
}
