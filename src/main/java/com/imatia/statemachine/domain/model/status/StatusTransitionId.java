package com.imatia.statemachine.domain.model.status;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * {@link StatusTransition} id class.
 *
 * @author Jesús Iglesias
 */
@Data
@Embeddable
public class StatusTransitionId implements Serializable {
    private Integer statusFrom;
    private Integer statusTo;
}
