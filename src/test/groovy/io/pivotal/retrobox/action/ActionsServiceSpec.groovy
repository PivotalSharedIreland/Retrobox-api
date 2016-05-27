package io.pivotal.retrobox.action

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class ActionsServiceSpec extends Specification {

    ActionsRepository actionsRepository
    ActionsService actionsService

    public setup() {
        def fixedDateTime = new ZonedDateTime(LocalDateTime.now(), ZoneOffset.MIN, ZoneId.of('Europe/Paris'))

        GroovyMock(ZonedDateTime, global: true)
        ZonedDateTime.now() >> fixedDateTime

        actionsRepository = Mock(ActionsRepository)
        actionsService = new ActionsService(actionsRepository: actionsRepository)
    }

    def 'action is saved'() {
        given: 'an action'
        Action newAction = new Action(description: 'My description', owner: 'someGuy')
        Action expectedAction = new Action(id: 1L, description: 'My description', owner: 'someGuy', creationDate: ZonedDateTime.now(), lastModifiedDate: ZonedDateTime.now(), status: ActionStatus.NEW)

        when: 'I save the action'
        1 * actionsRepository.save(_) >> { arguments ->
            Action serviceUpdatedAction = arguments[0]
            assert serviceUpdatedAction.creationDate == ZonedDateTime.now()
            assert serviceUpdatedAction.lastModifiedDate == ZonedDateTime.now()
            expectedAction
        }

        Action actualAction = actionsService.save(newAction)

        then: 'action is persisted with a unique id, dates and default status'
        assert actualAction.creationDate == expectedAction.creationDate
        assert actualAction.lastModifiedDate == expectedAction.lastModifiedDate
        assert actualAction.status == expectedAction.status
        assert actualAction.id == expectedAction.id
    }

}
