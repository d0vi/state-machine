package com.imatia.statemachine.infrastructure.service;

import com.imatia.statemachine.domain.exception.ImpossibleStatusUpdateException;
import com.imatia.statemachine.domain.exception.StatusNotFoundException;
import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;
import com.imatia.statemachine.domain.model.order.OrderTracking;
import com.imatia.statemachine.domain.model.status.Status;
import com.imatia.statemachine.domain.model.status.StatusTransition;
import com.imatia.statemachine.domain.repository.order.OrderRepository;
import com.imatia.statemachine.domain.repository.order.OrderTrackingRepository;
import com.imatia.statemachine.domain.repository.status.StatusRepository;
import com.imatia.statemachine.domain.repository.status.StatusTransitionRepository;
import com.imatia.statemachine.domain.service.StateMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateMachineServiceImpl implements StateMachineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineServiceImpl.class);

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final OrderTrackingRepository orderTrackingRepository;
    private final StatusTransitionRepository statusTransitionRepository;

    public StateMachineServiceImpl(final OrderRepository orderRepository, final StatusRepository statusRepository,
                                   final OrderTrackingRepository orderTrackingRepository,
                                   final StatusTransitionRepository statusTransitionRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.orderTrackingRepository = orderTrackingRepository;
        this.statusTransitionRepository = statusTransitionRepository;
    }

    @Override
    public void updateOrderStatus(Order order, Event event) {
        Objects.requireNonNull(order, "Order may not be null");
        Objects.requireNonNull(event, "Event may not be null");
        Status status = findStatus(event);
        // if the order does not have a current state, set the event's state
        if (order.getStatus() == null) {
            orderTrackingRepository.save(new OrderTracking(order, null, status, event.getDate()));
            order.setStatus(status);
            orderRepository.save(order);
            return;
        }
        // find the available status transitions for this order
        List<StatusTransition> availableStatusTransitions = statusTransitionRepository
                .findAllByStatusFrom(order.getStatus().getId());
        List<Integer> availableStatusTo = availableStatusTransitions.stream()
                .map(StatusTransition::getStatusTo)
                .collect(Collectors.toList());
        Integer statusId = event.getStatusId();
        // if the transition is not possible, throw an exception
        if (!availableStatusTo.contains(statusId)) {
            LOGGER.error("Couldn't update order status. Current status for order {} is {}.", order.getId(),
                    order.getStatus());
            throw new ImpossibleStatusUpdateException(order.getId(), order.getStatus().getId(), statusId);
        }
        orderTrackingRepository.save(
                new OrderTracking(order, order.getStatus(), status, event.getDate()));
        order.setStatus(status);
        orderRepository.save(order);
        LOGGER.info("Updated order status. New status for order {} is {}", order.getId(), status);
    }

    private Status findStatus(final Event event) {
        // check if there is a Status for this OrderEvent's 'trackingStatusId'. In case that there is not
        // a Status for that 'trackingStatusId', throw a StatusNotFoundException and rollback all the changes
        Integer statusId = event.getStatusId();
        Optional<Status> status = statusRepository.findById(statusId);
        if (status.isEmpty()) {
            LOGGER.error("Status id {} isn't part of the available status. Order {} could not be updated",
                    statusId, event.getOrderId());
            throw new StatusNotFoundException(statusId);
        }
        return status.get();
    }
}
