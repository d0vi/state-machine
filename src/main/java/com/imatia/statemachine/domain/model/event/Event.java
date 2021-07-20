package com.imatia.statemachine.domain.model.event;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * This entity is the representation of an order event.
 *
 * @author Jesús Iglesias
 */
@Data
public class Event {
    private final Long orderId;
    private final Integer statusId;
    private final OffsetDateTime date;
}
