package com.imatia.statemachine.domain.service;

import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;

/**
 * This service will be the foundation of the <a href="https://en.wikipedia.org/wiki/Finite-state_machine">FSM</a>.
 *
 * @author Jesús Iglesias
 * @see com.imatia.statemachine.domain.service service
 */
public interface StateMachineService {
    /**
     * Takes an order and an event and attemps to update the order status.
     *
     * @param order an {@link Order} instance
     * @param event an {@link Event} instance
     */
    void updateOrderStatus(Order order, Event event);
}
