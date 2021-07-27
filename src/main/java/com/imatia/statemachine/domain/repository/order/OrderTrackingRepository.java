package com.imatia.statemachine.domain.repository.order;

import com.imatia.statemachine.domain.model.order.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD operations for {@link OrderTracking}.
 *
 * @author Jesús Iglesias
 */
public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {
}
