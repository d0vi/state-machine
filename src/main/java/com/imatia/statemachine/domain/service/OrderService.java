package com.imatia.statemachine.domain.service;

import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;

/**
 * Base operations within the context of the {@link Order} domain model.
 *
 * @author Jesús Iglesias
 * @see com.imatia.statemachine.domain.service service
 */
public interface OrderService {
    /**
     * Saves a given entity.
     *
     * @param order the entity ready to be persisted
     * @return a persisted {@link Order}
     */
    Order save(Order order);

    /**
     * Finds an order and executes the given {@link Event}.
     *
     * @param event an event on an order
     */
    void dispatch(Event event);
}
