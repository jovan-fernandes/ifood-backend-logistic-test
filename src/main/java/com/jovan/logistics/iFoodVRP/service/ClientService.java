package com.jovan.logistics.iFoodVRP.service;

import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.web.response.SearchedClientsResponse;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 22:40
 */
public interface ClientService {


    ClientDTO create(final ClientDTO client);

    SearchedClientsResponse getAll(final Integer pageNumber,final Integer numberOfElements);

    ClientDTO getById(final String id) throws EntityNotFoundException;

    ClientDTO update(final String id, final ClientDTO client) throws EntityNotFoundException;
}
