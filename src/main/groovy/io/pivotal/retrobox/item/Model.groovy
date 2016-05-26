package io.pivotal.retrobox.item

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.NotNull
import java.time.ZonedDateTime

import static javax.persistence.EnumType.STRING

enum ItemStatus {
    ACTIVE, ARCHIVED
}

enum ItemType {
    HAPPY, UNHAPPY, MEDIOCRE
}


class Board {
    public static final long BOARD_ID = 1L

    @JsonProperty('items')
    Item[] items
}


@Entity
@ToString
@EqualsAndHashCode
class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @JsonProperty('board_id')
    @Column(name = 'BOARD_ID')
    Long boardId

    @NotEmpty
    @NotNull
    @Column(name = 'MESSAGE', nullable = false)
    String message

    @Enumerated(STRING)
    @Column(name = 'STATUS', nullable = false)
    ItemStatus status

    @NotNull
    @Enumerated(STRING)
    @Column(name = 'TYPE', nullable = false)
    ItemType type

    @JsonProperty('creation_date')
    @Column(name = 'CREATION_DATE', nullable = false)
    ZonedDateTime creationDate

    @JsonProperty('last_modified_date')
    @Column(name = 'LAST_MODIFIED_DATE', nullable = false)
    ZonedDateTime lastModifiedDate

    @Column(name = 'LIKES')
    int likes
}
