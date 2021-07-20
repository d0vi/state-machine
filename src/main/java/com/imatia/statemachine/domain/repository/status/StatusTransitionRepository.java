package com.imatia.statemachine.domain.repository.status;

import com.imatia.statemachine.domain.model.status.Status;
import com.imatia.statemachine.domain.model.status.StatusTransition;
import com.imatia.statemachine.domain.model.status.StatusTransitionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CRUD operations for {@link StatusTransition}.
 *
 * @author Jesús Iglesias
 */
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, StatusTransitionId> {
    /**
     * Returns the available status transitions for a given {@link Status} id.
     *
     * @param statusFrom the id of the {@link Status} which we want to know what valid transitions has available
     * @return all available status transitions for a {@code statusFrom}
     */
    List<StatusTransition> findAllByStatusFrom(Integer statusFrom);
}
