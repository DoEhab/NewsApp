package test.com.newsApp

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import test.com.newsApp.utils.PreferenceHelper
import javax.inject.Inject


class NewsApp: Application() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
        val config = baseContext.resources.configuration


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
    fun getComponent(): AppComponent = component

}