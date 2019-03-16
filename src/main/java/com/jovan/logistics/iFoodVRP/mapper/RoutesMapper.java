package com.jovan.logistics.iFoodVRP.mapper;

import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.web.response.OrdersRoutesResponse;
import com.jovan.logistics.iFoodVRP.web.response.RoutesResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 02:07
 */
@Mapper
public interface RoutesMapper {

    default RoutesResponse toResponse(RoutesDTO dto) {

        List<OrdersRoutesResponse> routes = dto.getOrders().stream()
                .map(drivers -> drivers.stream().map(orderDTO -> orderDTO.getId()).collect(Collectors.toList()))
                .map(OrdersRoutesResponse::new).collect(Collectors.toList());

        return RoutesResponse.builder().routes(routes).build();
    }

    ;


}