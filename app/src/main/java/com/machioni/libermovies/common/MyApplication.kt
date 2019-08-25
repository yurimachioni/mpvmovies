package com.machioni.libermovies.common

import android.app.Application
import android.os.Looper
import com.evernote.android.state.StateSaver
import com.machioni.libermovies.common.di.ApplicationComponent
import com.machioni.libermovies.common.di.ApplicationModule
import com.machioni.libermovies.common.di.DaggerApplicationComponent
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers

class MyApplication : Application() {
    companion object {
        lateinit var daggerComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        setupRx()

        RxPaperBook.init(this)

        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)

        daggerComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

    fun setupRx(){

        //https://medium.com/@sweers/rxandroids-new-async-api-4ab5b3ad3e93
        //TL;DR melhora performance da UI

        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }
    }
}