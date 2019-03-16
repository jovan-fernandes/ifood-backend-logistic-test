package com.jovan.logistics.iFoodVRP.mapper;


import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.web.request.OrderRequest;
import com.jovan.logistics.iFoodVRP.web.response.OrderResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedOrdersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-14 01:00
 */
@Mapper(uses = {ClientMapper.class, RestaurantMapper.class})
public interface OrderMapper {

    @Mappings({
            @Mapping(source = "restaurantId", target = "restaurant.id"),
            @Mapping(source = "clientId", target = "client.clientId"),
            @Mapping(target = "id", ignore = true)
    })
    OrderDTO toDTO(OrderRequest request);

    OrderDTO toDTO(OrderEntity entity);

    OrderEntity toEntity(OrderDTO dto);

    void merge(final OrderDTO source, @MappingTarget OrderEntity target);

    OrderResponse toResponse(OrderDTO dto);

    OrderResponse toResponse(OrderEntity entity);

    default SearchedOrdersResponse toSearchedOrdersResponse(Page<OrderEntity> pageable) {
        return new SearchedOrdersResponse(pageable.getTotalElements(), pageable.getTotalPages(),
                toOrdersResponse(pageable.getContent()));
    }

    default List<OrderResponse> toOrdersResponse(List<OrderEntity> orders) {
        return orders.stream().map(o -> this.toResponse(o)).collect(Collectors.toList());
    }

}
