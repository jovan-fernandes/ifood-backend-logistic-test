package com.jovan.logistics.iFoodVRP.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityAlreadyExistsException;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.service.impl.ClientServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 02:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceImplTest extends AbstractIFoodVRPTest {

    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    public void createClientWithSucces() {

        ClientDTO newClient = ClientDTO.builder()
                .lat(CLIENT_LAT)
                .lon(CLIENT_LON)
                .name(TEST_USER_NAME)
                .build();


        when(clientRepository.insert(any(ClientEntity.class))).thenReturn(buildDefaultClientEntity());

        when(clientRepository.findByName(anyString())).thenReturn(Optional.empty());

        ClientDTO clientDTO = clientService.create(newClient);

        assertTrue(StringUtils.isNotEmpty(clientDTO.getClientId()));
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void createClientWithSameName() {
        ClientDTO newClient = ClientDTO.builder()
                .lat(CLIENT_LAT)
                .lon(CLIENT_LON)
                .name(TEST_USER_NAME)
                .build();


        when(clientRepository.insert(any(ClientEntity.class))).thenReturn(buildDefaultClientEntity());

        when(clientRepository.findByName(anyString())).thenReturn(Optional.of(buildDefaultClientEntity()));

        ClientDTO clientDTO = clientService.create(newClient);
    }


    @Test
    public void getClientByIdWithSuccess() throws EntityNotFoundException {
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(buildDefaultClientEntity()));

        ClientDTO clientDTO = clientService.getById(CLIENT_ID);

        Assertions.assertThat(clientDTO).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void getClientWithUnknownId() throws EntityNotFoundException {
        when(clientRepository.findById(anyString())).thenReturn(Optional.empty());

        clientService.getById("XXXXXX");
    }

    @Test
    public void updateClientWithSuccess() throws EntityNotFoundException {
        ClientEntity clientEntity = buildDefaultClientEntity();

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientEntity));

        String newName = "joao";

        clientEntity.setName(newName);

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientDTO updateDTO = ClientDTO.builder().name(newName).lon(00d).lat(10d).build();

        ClientDTO updatedDTO = clientService.update(CLIENT_ID, updateDTO);

        assertTrue(CLIENT_ID.equals(updatedDTO.getClientId()) && newName.equals(updatedDTO.getName()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateClientWithUnknownId() throws EntityNotFoundException {
        when(clientRepository.findById(anyString())).thenReturn(Optional.empty());

        clientService.update("XXXX", new ClientDTO());

    }

}
