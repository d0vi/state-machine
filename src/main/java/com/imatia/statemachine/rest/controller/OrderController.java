package com.imatia.statemachine.rest.controller;

import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.service.OrderService;
import com.imatia.statemachine.openapi.api.OrderApi;
import com.imatia.statemachine.openapi.model.OrderEvent;
import com.imatia.statemachine.openapi.model.OrderEvents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that provides an interface to manipulate orders.
 *
 * @author Jesús Iglesias
 */
@RestController
public class OrderController implements OrderApi {
    private final OrderService service;

    public OrderController(final OrderService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> dispatch(OrderEvents orderEvents) {
        // cycle through the incoming events
        for (OrderEvent e : orderEvents.getOrderTrackings()) {
            // dispatch one by one
            service.dispatch(new Event(e.getOrderId(), e.getTrackingStatusId(), e.getChangeStatusDate()));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
