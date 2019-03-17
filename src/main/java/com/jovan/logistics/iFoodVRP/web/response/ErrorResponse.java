package com.jovan.logistics.iFoodVRP.web.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class ErrorResponse {

    private String code;

    private String message;
}
