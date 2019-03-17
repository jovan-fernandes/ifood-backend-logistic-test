package com.jovan.logistics.iFoodVRP.mapper;

import com.jovan.logistics.iFoodVRP.domain.ClientEntity;
import com.jovan.logistics.iFoodVRP.dto.ClientDTO;
import com.jovan.logistics.iFoodVRP.web.request.ClientRequest;
import com.jovan.logistics.iFoodVRP.web.response.ClientResponse;
import com.jovan.logistics.iFoodVRP.web.response.SearchedClientsResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:45
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ClientMapper {

    @Mapping(target = "clientId", ignore = true)
    public abstract ClientDTO toDTO(ClientRequest request);

    @Mappings({
            @Mapping(source = "id", target = "clientId"),
            @Mapping(target = "lat", ignore = true),
            @Mapping(target = "lon", ignore = true)
    })
    public abstract ClientDTO toDTO(ClientEntity entity);

    @Mappings({
            @Mapping(source = "clientId", target = "id"),
            @Mapping(target = "location", ignore = true)
    })
    public abstract ClientEntity toEntity(ClientDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    public abstract void merge(final ClientDTO source, @MappingTarget ClientEntity target);

    @Mapping(source = "clientId", target = "id")
    public abstract ClientResponse toResponse(ClientDTO dto);


    @Mappings({
            @Mapping(target = "lat", ignore = true),
            @Mapping(target = "lon", ignore = true)
    })
    public abstract ClientResponse toResponse(ClientEntity entity);


    public SearchedClientsResponse toSearchedClientsResponse(Page<ClientEntity> pageable) {
        return new SearchedClientsResponse(pageable.getTotalElements(), pageable.getTotalPages(),
                toClientResponse(pageable.getContent()));
    }

    public List<ClientResponse> toClientResponse(List<ClientEntity> clients) {
        return clients.stream().map(c -> this.toResponse(c)).collect(Collectors.toList());
    }

    @AfterMapping
    protected void addLocation(@MappingTarget ClientEntity clientEntity, ClientDTO dto) {
        if (dto.getLat() != null && dto.getLon() != null) {
            clientEntity.setLocation(new GeoJsonPoint(dto.getLat(), dto.getLon()));
        }
    }

    @AfterMapping
    protected void addLocation(@MappingTarget ClientResponse response, ClientEntity clientEntity) {
        if (clientEntity.getLocation() != null) {
            response.setLat(clientEntity.getLocation().getX());
            response.setLon(clientEntity.getLocation().getY());
        }
    }

    @AfterMapping
    protected void addLocation(@MappingTarget ClientDTO response, ClientEntity clientEntity) {
        if (clientEntity.getLocation() != null) {
            response.setLat(clientEntity.getLocation().getX());
            response.setLon(clientEntity.getLocation().getY());
        }
    }

}
