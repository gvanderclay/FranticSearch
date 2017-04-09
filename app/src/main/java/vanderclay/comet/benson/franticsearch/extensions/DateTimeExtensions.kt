package vanderclay.comet.benson.franticsearch.extensions

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Created by gclay on 4/9/17.
 */

val dateTimeFormat: DateTimeFormatter by lazy { DateTimeFormat.forPattern("YYYY-MM-DD")}

fun DateTime.convertToDateTime(date: String?): DateTime {
    return dateTimeFormat.parseDateTime(date)
}