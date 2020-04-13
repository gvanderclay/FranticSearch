package vanderclay.comet.benson.franticsearch.commons

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

val dateTimeFormat: DateTimeFormatter? = DateTimeFormat.forPattern("YYYY-MM-DD")

fun convertStringToDateTime(date: String?): DateTime?{
    return dateTimeFormat?.parseDateTime(date)
}
