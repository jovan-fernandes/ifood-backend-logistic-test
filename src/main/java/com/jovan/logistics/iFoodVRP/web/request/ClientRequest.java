package com.jovan.logistics.iFoodVRP.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 00:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ClientRequest implements Serializable {

    @NotNull(message = "O campo 'name' é obrigatório")
    @NotEmpty
    private String name;

    @NotNull(message = "O campo 'latitude' não pode ser vazio")
    private Double lat;

    @NotNull(message = "O campo 'longitude' não pode ser vazio")
    private Double lon;
}
