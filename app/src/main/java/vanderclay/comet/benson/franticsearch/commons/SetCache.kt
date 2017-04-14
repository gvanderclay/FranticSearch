package vanderclay.comet.benson.franticsearch.commons

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import io.magicthegathering.javasdk.api.SetAPI
import io.magicthegathering.javasdk.resource.MtgSet
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable

/**
 * This class will cache all set data from the
 * MTG API
 * Created by Gage Vander Clay on 4/13/2017.
 */

class SetCache {


    companion object {
        private var loaded = false
        private var setMap: Map<String, MtgSet>? = null

        fun getSets(): Map<String, MtgSet>? {
            if(!loaded) {
                saveSets()
            }
            while(!loaded);
            return setMap
        }

        private fun saveSets() {
            doAsync {
                val sets = SetAPI.getAllSets()
                setMap = sets.associateBy {
                    it.code
                }
                loaded = true
            }
        }
    }
}