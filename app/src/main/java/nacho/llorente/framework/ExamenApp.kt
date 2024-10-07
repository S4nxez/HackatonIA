package nacho.llorente.framework
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExamenApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}