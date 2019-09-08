package com.machioni.mpvmovies.domain.model

data class Page<T>(
        val items: List<T>,
        val totalResults: Long
)