@file:JvmName("ExtensionsUtils")
package vanderclay.comet.benson.franticsearch.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
/**
 * Created by Gage Vander Clay on 4/2/2017.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
