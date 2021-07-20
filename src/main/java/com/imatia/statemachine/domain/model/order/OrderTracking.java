package com.imatia.statemachine.domain.model.order;

import com.imatia.statemachine.domain.model.status.Status;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.OffsetDateTime;

/**
 * A status change event.
 *
 * @author Jesús Iglesias
 */
@Data
@Entity
@Table(name = "ORDERS_TRACKING")
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "PREVIOUS_STATUS")
    private Status previousStatus;
    @ManyToOne
    @JoinColumn(name = "CURRENT_STATUS")
    private Status currentStatus;
    @Past(message = "An event can not be in a future instant")
    private OffsetDateTime eventTimestamp;
    @Past(message = "Creation date can not be in a future instant")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public OrderTracking() {
    }

    public OrderTracking(final Order order, final Status previousStatus, final Status currentStatus,
                         final OffsetDateTime eventTimestamp) {
        this.order = order;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
        this.eventTimestamp = eventTimestamp;
    }
}
