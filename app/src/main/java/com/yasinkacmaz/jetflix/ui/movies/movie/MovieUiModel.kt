package com.yasinkacmaz.jetflix.ui.movies.movie

data class MovieUiModel(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int
)
