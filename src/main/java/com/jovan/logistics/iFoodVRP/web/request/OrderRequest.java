package com.jovan.logistics.iFoodVRP.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Calendar;

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
public class OrderRequest implements Serializable {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @NotNull(message = "O campo 'restaurantId' é obrigatório")
    @NotEmpty
    private String restaurantId;

    @NotNull(message = "O campo 'clientId' é obrigatório")
    @NotEmpty
    private String clientId;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "America/Sao_Paulo")
    @ApiModelProperty(required = true, example = "2018-06-05T13:37:00Z")
    private Calendar pickup;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "America/Sao_Paulo")
    @ApiModelProperty(required = true, example = "2018-06-05T13:54:00Z")
    private Calendar delivery;
}
