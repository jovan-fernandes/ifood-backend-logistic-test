package com.jovan.logistics.iFoodVRP.web.controller;

import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.mapper.RoutesMapper;
import com.jovan.logistics.iFoodVRP.service.RoutesService;
import com.jovan.logistics.iFoodVRP.utils.ApiVersion;
import com.jovan.logistics.iFoodVRP.web.response.RoutesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 02:03
 */
@RestController
@RequestMapping(path = "/routes")
@ApiVersion("v1")
@Validated
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    @Autowired
    private RoutesMapper mapper;

    @GetMapping
    public ResponseEntity<RoutesResponse> getRoutes() {
        RoutesDTO routes = routesService.getRoutes();
        return ResponseEntity.ok(this.mapper.toResponse(routes));
    }
}
