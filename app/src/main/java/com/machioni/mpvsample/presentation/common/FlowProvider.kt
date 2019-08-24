package com.machioni.mpvsample.presentation.common

import ru.terrakok.cicerone.Router

interface FlowProvider {
    fun getRouter(): Router
}