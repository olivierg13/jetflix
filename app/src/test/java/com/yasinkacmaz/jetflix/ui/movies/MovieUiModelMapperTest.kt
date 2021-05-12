package com.yasinkacmaz.jetflix.ui.movies

import com.yasinkacmaz.jetflix.data.MovieResponse
import com.yasinkacmaz.jetflix.ui.movies.movie.MovieUiModelMapper
import com.yasinkacmaz.jetflix.util.toPosterUrl
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MovieUiModelMapperTest {
    @Test
    fun map() {
        val movieResponse = MovieResponse(1, "Date", "Name", "Title", "Language", "Overview", "Poster", 1.1, 1)

        val movieUiModel = MovieUiModelMapper().map(movieResponse)

        expectThat(movieUiModel.id).isEqualTo(movieResponse.id)
        expectThat(movieUiModel.name).isEqualTo(movieResponse.name)
        expectThat(movieUiModel.releaseDate).isEqualTo(movieResponse.firstAirDate)
        expectThat(movieUiModel.posterPath).isEqualTo(movieResponse.posterPath.orEmpty().toPosterUrl())
        expectThat(movieUiModel.voteAverage).isEqualTo(movieResponse.voteAverage)
        expectThat(movieUiModel.voteCount).isEqualTo(movieResponse.voteCount)
    }
}
