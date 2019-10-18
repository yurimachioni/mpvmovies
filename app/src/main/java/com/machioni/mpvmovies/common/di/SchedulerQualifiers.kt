package com.machioni.mpvmovies.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BackgroundScheduler