package com.machioni.libermovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class SearchRM(
        @SerializedName("Search")
        val search: List<MovieRM>
)