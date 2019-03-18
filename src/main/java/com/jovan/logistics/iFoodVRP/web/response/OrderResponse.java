package com.jovan.logistics.iFoodVRP.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderResponse implements Serializable {


    private String id;

    private RestaurantResponse restaurant;

    private ClientResponse client;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date pickup;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date delivery;
}
