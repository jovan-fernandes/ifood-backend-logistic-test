package com.jovan.logistics.iFoodVRP.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 01:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutesResponse implements Serializable {

    private List<OrdersRoutesResponse> routes;
}
