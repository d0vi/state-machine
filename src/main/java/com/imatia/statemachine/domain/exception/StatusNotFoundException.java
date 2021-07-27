package com.imatia.statemachine.domain.exception;

import com.imatia.statemachine.domain.model.status.Status;

/**
 * Thrown to indicate that a specific {@link Status} could not be found.
 *
 * @author Jesús Iglesias
 */
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(int id) {
        super(String.format("Status id %d isn't part of the available status. All the changes have been reverted", id));
    }
}
