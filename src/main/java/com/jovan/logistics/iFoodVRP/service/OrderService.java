package com.jovan.logistics.iFoodVRP.service;

import com.jovan.logistics.iFoodVRP.dto.OrderDTO;
import org.springframework.data.domain.Page;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-12 23:32
 */
public interface OrderService {


    void create(final OrderDTO order);

    Page<OrderDTO> getAll();

    OrderDTO getById(final Long id);

    void update(final OrderDTO order);
}
