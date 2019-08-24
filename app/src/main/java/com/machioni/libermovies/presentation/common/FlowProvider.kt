package com.machioni.libermovies.presentation.common

import ru.terrakok.cicerone.Router

interface FlowProvider {
    fun getRouter(): Router
}