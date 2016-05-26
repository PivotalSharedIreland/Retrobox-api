package io.pivotal.retrobox.persistence

import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime


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
