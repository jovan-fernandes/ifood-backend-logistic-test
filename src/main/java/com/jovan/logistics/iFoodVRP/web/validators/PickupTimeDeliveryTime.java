package com.jovan.logistics.iFoodVRP.web.validators;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 01:21
 */
@Documented
@Target({ElementType.TYPE})
@Constraint(validatedBy = PickupTimeDeliveryTimeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PickupTimeDeliveryTime {

    String message() default "Invalid pickup delivery range";

    Class[] groups() default {};

    Class[] payload() default {};
}
