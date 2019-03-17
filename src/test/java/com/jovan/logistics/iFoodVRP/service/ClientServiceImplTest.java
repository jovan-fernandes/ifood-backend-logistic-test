package com.jovan.logistics.iFoodVRP.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.exception.EntityAlreadyExistsException;
import com.jovan.logistics.iFoodVRP.mapper.ClientMapper;
import com.jovan.logistics.iFoodVRP.repository.ClientRepository;
import com.jovan.logistics.iFoodVRP.service.impl.ClientServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 02:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceImplTest {

    public static final String CLIENT_ID = "54asjdbnbeiug892374";
    public static final String TEST_USER_NAME = "TEST USER";
    public static final Double LAT = -23.588055;
    public static final Double LON = -46.632403;


    @Mock
    private ClientRepository clientRepository;


    @Spy
    private ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    public void createClientWithSucces() {

        ClientDTO newClient = ClientDTO.builder()
                .lat(LAT)
                .lon(LON)
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
                .lat(LAT)
                .lon(LON)
                .name(TEST_USER_NAME)
                .build();


        when(clientRepository.insert(any(ClientEntity.class))).thenReturn(buildDefaultClientEntity());

        when(clientRepository.findByName(anyString())).thenReturn(Optional.of(buildDefaultClientEntity()));

        ClientDTO clientDTO = clientService.create(newClient);
    }


    private ClientDTO buildDefaultClientDTO() {
        return ClientDTO.builder()
                .clientId(CLIENT_ID)
                .lat(LAT)
                .lon(LON)
                .name(TEST_USER_NAME)
                .build();
    }

    private ClientEntity buildDefaultClientEntity() {
        return new ClientEntity(CLIENT_ID, TEST_USER_NAME,
                new GeoJsonPoint(LAT, LON));

    }
}
