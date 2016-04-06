package io.pivotal.retrobox

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Converter
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

import static javax.persistence.EnumType.STRING

enum ItemStatus {
    ACTIVE, ARCHIVED
}

enum ItemType {
    HAPPY, SAD, MEDIOCRE
}


class Board {
    public static final int BOARD_ID = 1

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

@Converter(autoApply = true)
class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, java.sql.Timestamp> {
    @Override
    public java.sql.Timestamp convertToDatabaseColumn(ZonedDateTime entityValue) {
        return entityValue != null ? Timestamp.from(entityValue.toInstant()) : null
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
        if(!databaseValue){
            return null
        }

        LocalDateTime localDateTime = databaseValue.toLocalDateTime()
        return localDateTime.atZone(ZoneOffset.UTC);
    }
}