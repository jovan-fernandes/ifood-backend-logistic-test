package com.jovan.logistics.iFoodVRP.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-13 19:39
 */
public class PageableUtils {

    public static PageRequest createPageable(Integer pageNumber, Integer numberOfElements, String orderPropertyName) {
        return PageRequest.of(pageNumber - 1 < 0 ? pageNumber : pageNumber - 1, numberOfElements, Sort.Direction.ASC, (orderPropertyName));
    }
}
