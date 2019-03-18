package com.jovan.logistics.iFoodVRP.mapper;

import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.exception.InvalidDateFormatException;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
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
                .map(drivers -> drivers.stream().map(orderDTO -> {
                    try {
                        return buildRouteOutput(orderDTO);
                    } catch (InvalidDateFormatException e) {
                        e.printStackTrace();
                    }
                    return orderDTO.getId();
                }).collect(Collectors.toList()))
                .map(OrdersRoutesResponse::new).collect(Collectors.toList());

        return RoutesResponse.builder().routes(routes).build();
    }

    default String buildRouteOutput(OrderDTO orderDTO) throws InvalidDateFormatException {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido do(a) ")
                .append(orderDTO.getClient().getName())
                .append(" - Restaurante: ")
                .append(orderDTO.getRestaurant().getName())
                .append(" - id: ")
                .append(orderDTO.getId())
                .append(" - pickup: ")
                .append(DateFormatter.formatDateDefaultPattern(orderDTO.getPickup()).orElseThrow(InvalidDateFormatException::new))
                .append(" - delivery: ")
                .append(DateFormatter.formatDateDefaultPattern(orderDTO.getDelivery()).orElseThrow(InvalidDateFormatException::new));


        return sb.toString();
    }

    ;


}
