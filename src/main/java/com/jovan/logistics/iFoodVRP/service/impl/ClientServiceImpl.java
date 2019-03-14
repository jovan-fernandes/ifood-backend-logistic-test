package com.jovan.logistics.iFoodVRP.service.impl;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.mapper.ClientMapper;
import com.jovan.logistics.iFoodVRP.repository.ClientRepository;
import com.jovan.logistics.iFoodVRP.service.ClientService;
import com.jovan.logistics.iFoodVRP.utils.PageableUtils;
import com.jovan.logistics.iFoodVRP.web.response.ClientResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedClientsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:59
 */
@Service
public class ClientServiceImpl implements ClientService {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper mapper;

    @Override
    public ClientDTO create(final ClientDTO client) {
        ClientEntity created = clientRepository.insert(this.mapper.toEntity(client));
        return this.mapper.toDTO(created);
    }

    @Override
    public SearchedClientsResponse getAll(final Integer pageNumber,final Integer numberOfElements) {
        PageRequest pageableForClients = PageableUtils.createPageableForClients(pageNumber, numberOfElements);
        Page<ClientEntity> clientEntities = clientRepository.findAll(pageableForClients);

        return convertToSearchedClientsResponse(clientEntities);
    }

    @Override
    public ClientDTO getById(final String id) throws EntityNotFoundException {
        return clientRepository.findById(id).map(c -> this.mapper.toDTO(c)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ClientDTO update(final String id, final ClientDTO client) throws EntityNotFoundException {
        ClientEntity entity = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        this.mapper.merge(client, entity);
        return this.mapper.toDTO(clientRepository.save(entity));
    }


    private SearchedClientsResponse convertToSearchedClientsResponse(Page<ClientEntity> pageable){
        return new SearchedClientsResponse(pageable.getTotalElements(), pageable.getTotalPages(),
                convertToClientResponse(pageable.getContent()));
    }

    private List<ClientResponse> convertToClientResponse(List<ClientEntity> clients){
        return clients.stream().map(c -> this.mapper.toResponse(c)).collect(Collectors.toList());
    }

}
