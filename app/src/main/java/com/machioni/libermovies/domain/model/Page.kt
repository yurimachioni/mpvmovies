package com.machioni.libermovies.domain.model

data class Page<T>(
        val items: List<T>,
        val totalResults: Long
)