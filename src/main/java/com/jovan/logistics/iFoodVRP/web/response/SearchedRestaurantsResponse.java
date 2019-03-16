package com.jovan.logistics.iFoodVRP.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-13 19:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchedRestaurantsResponse {

    private long totalOfElements;
    private int totalOfPages;
    private List<RestaurantResponse> restaurants;

}
