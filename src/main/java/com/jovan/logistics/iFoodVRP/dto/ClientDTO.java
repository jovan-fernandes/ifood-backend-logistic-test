package com.jovan.logistics.iFoodVRP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString(exclude = {"clientId", "lat", "lon"})
public class ClientDTO implements Serializable {

    private String clientId;

    private String name;

    private Double lat;

    private Double lon;
}
