package com.imatia.statemachine.infrastructure.service;

import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;
import com.imatia.statemachine.domain.repository.order.OrderRepository;
import com.imatia.statemachine.domain.service.OrderService;
import com.imatia.statemachine.domain.service.StateMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository repository;

    private final StateMachineService stateMachine;

    public OrderServiceImpl(final OrderRepository repository, final StateMachineService stateMachine) {
        this.repository = repository;
        this.stateMachine = stateMachine;
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Transactional
    @Override
    public void dispatch(final Event event) {
        // find an order by its id: if it does not exist, a new order will be created. Otherwise, a status update
        // will be dispatched for that order
        Optional<Order> order = repository.findById(event.getOrderId());
        if (order.isEmpty()) {
            Order o = save(new Order(event.getOrderId()));
            stateMachine.updateOrderStatus(o, event);
            LOGGER.info("Created a new order: {}", o);
        } else {
            stateMachine.updateOrderStatus(order.get(), event);
        }
    }
}
