package com.machioni.mpvmovies.domain.usecase

interface UseCase<I, O> {
    val func: (I) -> O
    operator fun invoke(input: I) : O = func(input)
}