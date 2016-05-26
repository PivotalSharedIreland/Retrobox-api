package io.pivotal.retrobox.action

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.NotNull
import java.time.ZonedDateTime

import static javax.persistence.EnumType.STRING

enum ActionStatus {
    NEW, IN_PROGRESS, COMPLETE
}

@Entity
@ToString
@EqualsAndHashCode
class Action {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @NotEmpty
    @Column(name = 'DESCRIPTION', nullable = false)
    String description

    @NotEmpty
    @Column(name = 'OWNER', nullable = false)
    String owner

    @Column(name = 'CREATION_DATE', nullable = false)
    ZonedDateTime creationDate

    @Column(name = 'LAST_MODIFIED_DATE', nullable = false)
    ZonedDateTime lastModifiedDate

    @NotNull
    @Enumerated(STRING)
    @Column(name = 'STATUS', nullable = false)
    ActionStatus status = ActionStatus.NEW
}
