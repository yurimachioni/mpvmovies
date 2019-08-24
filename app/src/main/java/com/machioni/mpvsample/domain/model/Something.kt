package com.machioni.mpvsample.domain.model

data class Something(
        val id: Int,
        val text: String = "something$id",
        val imgUrl: String = "https://img.rankedboost.com/wp-content/plugins/pokemon-quest/pokemon-quest-pokemon-images/10$id.png"
)