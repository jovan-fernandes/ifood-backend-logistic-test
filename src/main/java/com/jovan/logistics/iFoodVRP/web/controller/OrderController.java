package com.jovan.logistics.iFoodVRP.web.controller;

import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.OrderMapper;
import com.jovan.logistics.iFoodVRP.service.OrderService;
import com.jovan.logistics.iFoodVRP.utils.ApiVersion;
import com.jovan.logistics.iFoodVRP.web.request.OrderRequest;
import com.jovan.logistics.iFoodVRP.web.response.OrderResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedOrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:24
 */
@RestController
@RequestMapping(path = "/orders")
@ApiVersion("v1")
@Validated
public class OrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderMapper mapper;


    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody final OrderRequest request) throws EntityNotFoundException {
        OrderDTO dto = orderService.create(this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(dto));
    }

    @GetMapping
    public ResponseEntity<SearchedOrdersResponse> getAll(@RequestParam("pageNumber") @NotNull @Min(1) final Integer pageNumber,
                                                         @RequestParam("numberOfElements") @NotNull @Min(1) final Integer numberOfElements,
                                                         @RequestParam("restaurantName") final Optional<String> restaurantName,
                                                         @RequestParam("deliveryTime") @DateTimeFormat(pattern = "dd-MM-yyyy") final Optional<Calendar> deliveryTime) {
        SearchedOrdersResponse searchResult;

        if (restaurantName.isPresent()) {
            searchResult = orderService.getByRestaurant(pageNumber, numberOfElements, restaurantName.get(), deliveryTime);
        } else {
            searchResult = orderService.getAll(pageNumber, numberOfElements, deliveryTime);
        }

        return ResponseEntity.ok(searchResult);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("orderId") String id) throws EntityNotFoundException {
        OrderDTO dto = orderService.getById(id);
        return ResponseEntity.ok(this.mapper.toResponse(dto));
    }

    @PutMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponse> update(@PathVariable("orderId") String id, @Valid @RequestBody OrderRequest request) throws EntityNotFoundException {
        OrderDTO updated = orderService.update(id, this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(updated));
    }

}
