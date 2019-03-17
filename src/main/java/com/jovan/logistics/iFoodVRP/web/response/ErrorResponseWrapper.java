package com.jovan.logistics.iFoodVRP.web.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 17:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class ErrorResponseWrapper {

    private List<ErrorResponse> erros;
}
