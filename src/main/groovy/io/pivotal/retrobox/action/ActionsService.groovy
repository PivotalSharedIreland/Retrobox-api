package io.pivotal.retrobox.action

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.ZonedDateTime

@Service
class ActionsService {

    @Autowired
    ActionsRepository actionsRepository

    Action save(Action action) {
        setDates(action)
        actionsRepository.save(action)
    }

    private void setDates(Action action) {
        def currentDateTime = ZonedDateTime.now()
        action.setCreationDate(currentDateTime)
        action.setLastModifiedDate(currentDateTime)
    }
}
