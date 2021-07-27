package com.imatia.statemachine.domain.repository.order;

import com.imatia.statemachine.domain.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD operations for {@link Order}.
 *
 * @author Jesús Iglesias
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
