package com.machioni.mpvsample.presentation.common

import androidx.lifecycle.Lifecycle

val Lifecycle.Event.isActive
    get() = this == Lifecycle.Event.ON_START || this == Lifecycle.Event.ON_RESUME