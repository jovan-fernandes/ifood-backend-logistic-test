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
 * @since 2019-03-16 02:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRoutesResponse implements Serializable {

    private List<String> orders;
}
