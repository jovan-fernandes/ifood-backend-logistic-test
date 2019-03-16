package com.jovan.logistics.iFoodVRP.web.controller;

import com.jovan.logistics.iFoodVRP.dto.RestaurantDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.RestaurantMapper;
import com.jovan.logistics.iFoodVRP.service.RestaurantService;
import com.jovan.logistics.iFoodVRP.utils.ApiVersion;
import com.jovan.logistics.iFoodVRP.web.request.RestaurantRequest;
import com.jovan.logistics.iFoodVRP.web.response.RestaurantResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedRestaurantsResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:23
 */
@RestController
@RequestMapping(path = "/restaurants")
@ApiVersion("v1")
@Validated
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper mapper;


    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody final RestaurantRequest request) {
        RestaurantDTO restaurantDTO = restaurantService.create(this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(restaurantDTO));
    }

    @GetMapping
    public ResponseEntity<SearchedRestaurantsResponse> getAll(@RequestParam("pageNumber") @NotNull @Min(1) final Integer pageNumber,
                                                              @RequestParam("numberOfElements") @NotNull @Min(1) final Integer numberOfElements) {
        SearchedRestaurantsResponse restaurans = restaurantService.getAll(pageNumber, numberOfElements);
        return ResponseEntity.ok(restaurans);
    }

    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable("restaurantId") String id) throws EntityNotFoundException {
        RestaurantDTO dto = restaurantService.getById(id);
        return ResponseEntity.ok(this.mapper.toResponse(dto));
    }

    @PutMapping(path = "/{restaurantId}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable("restaurantId") String id, @Valid @RequestBody RestaurantRequest request) throws EntityNotFoundException {
        RestaurantDTO updated = restaurantService.update(id, this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(updated));
    }

}
