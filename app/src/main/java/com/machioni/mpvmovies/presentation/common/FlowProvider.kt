package com.machioni.mpvmovies.presentation.common

import ru.terrakok.cicerone.Router

interface FlowProvider {
    fun getRouter(): Router
}