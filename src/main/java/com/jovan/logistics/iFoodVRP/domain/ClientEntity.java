package com.jovan.logistics.iFoodVRP.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 22:41
 */
@Document(collection = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {


    @Id
    private String id;
    private String name;
    private GeoJsonPoint location;

}
