package vanderclay.comet.benson.franticsearch.extensions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

/**
 * Created by gclay on 4/9/17.
 */

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

