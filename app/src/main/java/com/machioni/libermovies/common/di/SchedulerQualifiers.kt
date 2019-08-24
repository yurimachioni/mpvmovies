package com.machioni.libermovies.common.di

import javax.inject.Qualifier

@Qualifier
annotation class MainScheduler

@Qualifier
annotation class BackgroundScheduler