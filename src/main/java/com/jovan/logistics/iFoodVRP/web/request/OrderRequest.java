package com.jovan.logistics.iFoodVRP.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
import com.jovan.logistics.iFoodVRP.web.validators.PickupTimeDeliveryTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 00:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@PickupTimeDeliveryTime
public class OrderRequest implements Serializable {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @NotNull(message = "O campo 'restaurantId' é obrigatório")
    @NotEmpty
    private String restaurantId;

    @NotNull(message = "O campo 'clientId' é obrigatório")
    @NotEmpty
    private String clientId;

    @NotNull
    @DateTimeFormat(pattern = DateFormatter.APPLICATION_DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(required = true, example = "2019-03-17T13:37:00Z")
    private Date pickup;

    @NotNull
    @DateTimeFormat(pattern = DateFormatter.APPLICATION_DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(required = true, example = "2019-03-17T13:54:00Z")
    private Date delivery;
}
