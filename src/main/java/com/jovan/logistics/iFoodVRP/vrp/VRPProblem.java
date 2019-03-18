package com.jovan.logistics.iFoodVRP.vrp;

import com.jovan.logistics.iFoodVRP.domain.OrderEntity;
import com.jovan.logistics.iFoodVRP.dto.RoutesDTO;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-18 04:19
 */

public abstract class VRPProblem {

    public static final int DRIVER_CAPACITY = 3;


    //CAN'T pick up an order before the pickup time
    protected boolean canPickup(OrderEntity order) {
        return DateFormatter.getNowDefaultTimeZone().after(order.getPickup());
    }

    public abstract RoutesDTO calculate();
}
