package com.jovan.logistics.iFoodVRP.web.validators;

import com.jovan.logistics.iFoodVRP.web.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 01:23
 */
@Slf4j
public class PickupTimeDeliveryTimeValidatorImpl implements ConstraintValidator<PickupTimeDeliveryTime, OrderRequest> {

    @Override
    public boolean isValid(OrderRequest order, ConstraintValidatorContext context) {
        return order.getDelivery().after(order.getPickup());
    }
}
