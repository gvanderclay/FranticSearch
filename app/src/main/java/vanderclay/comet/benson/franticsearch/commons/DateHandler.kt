package vanderclay.comet.benson.franticsearch.commons

/**
 * Created by gclay on 4/7/17.
 */

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

val dateTimeFormat: DateTimeFormatter? = DateTimeFormat.forPattern("YYYY-MM-DD")

fun convertStringToDateTime(date: String?): DateTime?{
    return dateTimeFormat?.parseDateTime(date)
}
