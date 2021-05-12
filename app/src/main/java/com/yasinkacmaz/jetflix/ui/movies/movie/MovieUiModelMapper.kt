package com.yasinkacmaz.jetflix.ui.movies.movie

import com.yasinkacmaz.jetflix.data.MovieResponse
import com.yasinkacmaz.jetflix.util.Mapper
import com.yasinkacmaz.jetflix.util.toPosterUrl
import javax.inject.Inject

class MovieUiModelMapper @Inject constructor() : Mapper<MovieResponse, MovieUiModel> {
    override fun map(input: MovieResponse) = MovieUiModel(
        id = input.id,
        name = input.name,
        releaseDate = input.firstAirDate,
        posterPath = input.posterPath.orEmpty().toPosterUrl(),
        voteAverage = input.voteAverage,
        voteCount = input.voteCount
    )
}
