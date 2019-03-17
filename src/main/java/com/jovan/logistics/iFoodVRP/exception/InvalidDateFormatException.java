package com.jovan.logistics.iFoodVRP.exception;

import com.jovan.logistics.iFoodVRP.utils.DateFormatter;
import lombok.AllArgsConstructor;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-14 01:03
 */
@AllArgsConstructor
public class InvalidDateFormatException extends Exception {

    private static final String MESSAGE = "Formato de data inválido, esperao: " + DateFormatter.APPLICATION_DATE_TIME_FORMAT;

}
