package com.imatia.statemachine.domain.model.status;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Status entity.
 *
 * @author Jesús Iglesias
 */
@Data
@Entity
public class Status {
    @Id
    @PositiveOrZero(message = "Status id must be positive")
    private Integer id;
    @Size(min = 4, max = 100, message = "Status name must be between 4 and 100 characters")
    private String name;
}
