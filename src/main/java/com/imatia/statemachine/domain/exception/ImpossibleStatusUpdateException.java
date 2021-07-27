package com.imatia.statemachine.domain.exception;

/**
 * Thrown to indicate that a status transition is not valid.
 *
 * @author Jesús Iglesias
 */
public class ImpossibleStatusUpdateException extends RuntimeException {
    public ImpossibleStatusUpdateException(long orderId, int currentStatus, int nextStatus) {
        super(String.format("Order %d is currently on status %d. It is not possible to update to status %d",
                orderId, currentStatus, nextStatus));
    }
}
