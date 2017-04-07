package vanderclay.comet.benson.franticsearch.ui

import android.app.Application

/**
 * Created by gclay on 4/5/17.
 */

class App: Application() {
    companion object {
        var instance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
