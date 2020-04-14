package vanderclay.comet.benson.franticsearch.ui

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class App: Application() {
    companion object {
        var instance: App? = null
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        instance = this
    }
}
