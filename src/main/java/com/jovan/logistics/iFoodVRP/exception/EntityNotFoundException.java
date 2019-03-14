package com.jovan.logistics.iFoodVRP.exception;

import lombok.AllArgsConstructor;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-14 01:03
 */
@AllArgsConstructor
public class EntityNotFoundException extends Exception {

    private static final String MESSAGE = "Entidade não encontrada";

}
