package com.imatia.statemachine.domain.model.status;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Represents a valid transition from one state to another.
 *
 * @author Jesús Iglesias
 */
@Data
@Entity
@IdClass(StatusTransitionId.class)
@Table(name = "STATUS_TRANSITIONS")
public class StatusTransition {
    @Id
    private Integer statusFrom;
    @Id
    private Integer statusTo;
}
