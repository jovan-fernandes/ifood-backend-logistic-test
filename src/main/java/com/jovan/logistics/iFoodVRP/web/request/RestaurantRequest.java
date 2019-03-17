package com.jovan.logistics.iFoodVRP.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-15 23:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class RestaurantRequest implements Serializable {


    @NotNull(message = "O campo 'name' é obrigatório")
    @NotEmpty
    private String name;

    @NotNull(message = "O campo 'latitude' não pode ser vazio")
    @Min(-90)
    @Max(90)
    private Double lat;

    @NotNull(message = "O campo 'longitude' não pode ser vazio")
    @Min(-180)
    @Max(180)
    private Double lon;
}
