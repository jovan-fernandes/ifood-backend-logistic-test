package com.jovan.logistics.iFoodVRP.service;

import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.vrp.VRPProblem;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 02:04
 */
public interface RoutesService {


    /**
     * Rules
     * <p>
     * You have limitless drivers. -> but it is better to minimize the number of drivers.
     * A worker can do at most 3 deliveries of the same restaurant at the same time (i.e. in the same route)
     * A worker can only do deliveries of a single restaurant at a time (i.e. in the same route)
     * You should try to avoid delivering an order after the delivery time
     * You CAN'T pick up an order before the pickup time
     * The driver is on the restaurant at the pickup time
     * Consider that the driver goes 0.1 units in line each 5 minute.
     * Each order must be optimized only once
     *
     * @return
     */
    default RoutesDTO getRoutes(VRPProblem vrpProblem) {
        return vrpProblem.calculate();
    }

    ;

    RoutesDTO getRoutes();

}
