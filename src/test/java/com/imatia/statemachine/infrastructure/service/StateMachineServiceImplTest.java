package com.imatia.statemachine.infrastructure.service;

import com.imatia.statemachine.config.StateMachineApplication;
import com.imatia.statemachine.domain.exception.ImpossibleStatusUpdateException;
import com.imatia.statemachine.domain.exception.StatusNotFoundException;
import com.imatia.statemachine.domain.model.event.Event;
import com.imatia.statemachine.domain.model.order.Order;
import com.imatia.statemachine.domain.model.status.Status;
import com.imatia.statemachine.domain.repository.order.OrderRepository;
import com.imatia.statemachine.domain.repository.order.OrderTrackingRepository;
import com.imatia.statemachine.domain.repository.status.StatusRepository;
import com.imatia.statemachine.domain.repository.status.StatusTransitionRepository;
import com.imatia.statemachine.domain.service.StateMachineService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.OffsetDateTime;

@DataJpaTest
@ContextConfiguration(classes = StateMachineApplication.class)
class StateMachineServiceImplTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrderTrackingRepository orderTrackingRepository;
    @Autowired
    private StatusTransitionRepository statusTransitionRepository;

    private StateMachineService service;

    @BeforeEach
    void setUp() {
        service = new StateMachineServiceImpl(orderRepository, statusRepository, orderTrackingRepository,
                statusTransitionRepository);
    }

    @Test
    void shouldUpdateStatelessOrder() {
        // new stateless Order
        Order order = orderRepository.save(new Order(1L));
        // calling StateMachineService#updateOrderStatus on a stateless Order should
        // update its status to match that on the Event
        service.updateOrderStatus(order, new Event(1L, 1, OffsetDateTime.now()));
        order = orderRepository.findById(1L).orElseThrow();
        Status status = statusRepository.findById(1).orElseThrow();
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getStatus()).isEqualTo(status);
    }

    @Test
    void shouldUpdateStatefulOrder() {
        Status initialStatus = statusRepository.findById(1).orElseThrow();
        // new Order on Status 1
        // only valid transitions are to Status 2
        Order order = orderRepository.save(new Order(1L, initialStatus));
        // calling StateMachineService#updateOrderStatus on a this Order should
        // update it only if the Event's status is a valid transition
        service.updateOrderStatus(order, new Event(1L, 2, OffsetDateTime.now()));
        order = orderRepository.findById(1L).orElseThrow();
        Status finalStatus = statusRepository.findById(2).orElseThrow();
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getStatus()).isEqualTo(finalStatus);
    }

    @Test
    void shouldThrowStatusNotFoundException() {
        // new Order on Status 1
        Status status = statusRepository.findById(1).orElseThrow();
        Order order = orderRepository.save(new Order(1L, status));
        // 0 is not an existing status, thus StatusNotFoundException must be thrown
        Assertions.assertThatThrownBy(() ->
                service.updateOrderStatus(order, new Event(1L, 0, OffsetDateTime.now())))
                .isInstanceOf(StatusNotFoundException.class);
    }

    @Test
    void shouldThrowImpossibleStatusUpdateException() {
        // new Order on Status 1
        // only valid transitions are to Status 2
        Status status = statusRepository.findById(1).orElseThrow();
        Order order = orderRepository.save(new Order(1L, status));
        // 4 is not a valid status to transition to, thus ImpossibleStatusUpdateException must be thrown
        Assertions.assertThatThrownBy(() ->
                service.updateOrderStatus(order, new Event(1L, 4, OffsetDateTime.now())))
                .isInstanceOf(ImpossibleStatusUpdateException.class);
    }
}