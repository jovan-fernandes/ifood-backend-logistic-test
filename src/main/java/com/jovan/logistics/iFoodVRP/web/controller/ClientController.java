package com.jovan.logistics.iFoodVRP.web.controller;

import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.ClientMapper;
import com.jovan.logistics.iFoodVRP.service.ClientService;
import com.jovan.logistics.iFoodVRP.utils.ApiVersion;
import com.jovan.logistics.iFoodVRP.web.request.ClientRequest;
import com.jovan.logistics.iFoodVRP.web.response.ClientResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedClientsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:23
 */
@Controller
@RequestMapping(path = "/clients")
@ApiVersion("v1")
@Validated
public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper mapper;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody final ClientRequest request){
        ClientDTO created = clientService.create(this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(created));
    }


    @GetMapping
    public ResponseEntity<SearchedClientsResponse> getAll() {
        SearchedClientsResponse all = clientService.getAll(1, 10);
        return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/{clientId}")
    public ResponseEntity<ClientResponse> getById(@PathVariable("clientId") String id) throws EntityNotFoundException {
        ClientDTO dto = clientService.getById(id);
        return ResponseEntity.ok(this.mapper.toResponse(dto));
    }

    @PutMapping(path = "/{clientId}")
    public ResponseEntity<ClientResponse> update(@PathVariable("clientId") String id, @Valid @RequestBody ClientRequest request) throws EntityNotFoundException {
        ClientDTO updated = clientService.update(id , this.mapper.toDTO(request));
        return ResponseEntity.ok(this.mapper.toResponse(updated));
    }

}
