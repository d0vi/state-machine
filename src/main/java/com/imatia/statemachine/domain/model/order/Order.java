package com.imatia.statemachine.domain.model.order;

import com.imatia.statemachine.domain.model.status.Status;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

/**
 * This entity represents a real world order.
 *
 * @author Jesús Iglesias
 */
@Data
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @Positive(message = "Order id must be positive")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private Status status;
    @Past(message = "An order creation date can not be in a future instant")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Order() {
    }

    public Order(Long id) {
        this.id = id;
    }

    public Order(Long id, Status status) {
        this.id = id;
        this.status = status;
    }
}
