package com.machioni.mpvsample.common.di

import android.content.Context
import com.machioni.mpvsample.presentation.scene.somethingdetail.SomethingDetailFragment
import com.machioni.mpvsample.presentation.scene.somethingdetail.SomethingDetailView
import com.machioni.mpvsample.presentation.scene.somethingdetail.SomethingDetailUI
import com.machioni.mpvsample.presentation.scene.somethinglist.SomethingListFragment
import com.machioni.mpvsample.presentation.scene.somethinglist.SomethingListView
import com.machioni.mpvsample.presentation.scene.somethinglist.SomethingListUI
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @Singleton
    @ApplicationContext
    fun context() = context

    @Provides
    @BackgroundScheduler
    fun backgroundScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun mainScheduler() = AndroidSchedulers.mainThread()

    @Provides
    fun somethingDetailView(somethingDetailUI: SomethingDetailUI) : SomethingDetailView{
        return somethingDetailUI
    }

    @Provides
    fun somethingListView(somethingListUI: SomethingListUI) : SomethingListView{
        return somethingListUI
    }
}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @ApplicationContext
    fun context(): Context

    @BackgroundScheduler
    fun backgroundScheduler(): Scheduler

    @MainScheduler
    fun mainScheduler(): Scheduler

    fun inject(somethingDetailFragment: SomethingDetailFragment)
    fun inject(somethingListFragment: SomethingListFragment)
}