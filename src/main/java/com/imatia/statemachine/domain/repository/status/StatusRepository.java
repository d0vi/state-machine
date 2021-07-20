package com.imatia.statemachine.domain.repository.status;

import com.imatia.statemachine.domain.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD operations for {@link Status}.
 *
 * @author Jesús Iglesias
 */
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
