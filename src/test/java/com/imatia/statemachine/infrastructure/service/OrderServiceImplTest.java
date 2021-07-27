package com.imatia.statemachine.infrastructure.service;

import com.imatia.statemachine.config.StateMachineApplication;
import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;
import com.imatia.statemachine.domain.model.status.Status;
import com.imatia.statemachine.domain.repository.order.OrderRepository;
import com.imatia.statemachine.domain.repository.status.StatusRepository;
import com.imatia.statemachine.domain.service.OrderService;
import com.imatia.statemachine.domain.service.StateMachineService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.OffsetDateTime;

@DataJpaTest
@ContextConfiguration(classes = {StateMachineApplication.class, StateMachineServiceImpl.class})
class OrderServiceImplTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StateMachineService stateMachine;

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderServiceImpl(orderRepository, stateMachine);
    }

    @Test
    void shouldSave() {
        Assertions.assertThat(service.save(new Order(1L))).isNotNull();
    }

    @Test
    void shouldDispatchExistingOrder() {
        // new Order on Status 1
        // only valid transitions are to Status 2
        Status initialStatus = statusRepository.findById(1).orElseThrow();
        Order order = orderRepository.save(new Order(1L, initialStatus));
        // this is a valid transition
        service.dispatch(new Event(1L, 2, OffsetDateTime.now()));
        Status finalStatus = statusRepository.findById(2).orElseThrow();
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getStatus()).isEqualTo(finalStatus);
    }

    @Test
    void shouldDispatchNewOrder() {
        // state transition on an Order that is not registered yet
        service.dispatch(new Event(1L, 1, OffsetDateTime.now()));
        Status status = statusRepository.findById(1).orElseThrow();
        Order order = orderRepository.findById(1L).orElseThrow();
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getStatus()).isEqualTo(status);
    }
}