package com.jovan.logistics.iFoodVRP.web.response;

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
public class SearchedClientsResponse {

    private long totalOfElements;
    private int totalOfPages;
    private List<ClientResponse> clients;


    public SearchedClientsResponse(long totalOfElements, int totalOfPages, List<ClientResponse> clients){
        this.totalOfElements = totalOfElements;
        this.totalOfPages = totalOfPages;
        this.clients = clients;
    }
}
