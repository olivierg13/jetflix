package com.yasinkacmaz.jetflix.ui.moviedetail

import com.yasinkacmaz.jetflix.data.MovieDetailResponse
import com.yasinkacmaz.jetflix.data.ProductionCompanyResponse
import com.yasinkacmaz.jetflix.util.parseJson
import com.yasinkacmaz.jetflix.util.toBackdropUrl
import com.yasinkacmaz.jetflix.util.toPosterUrl
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MovieDetailUiModelMapperTest {
    private val mapper = MovieDetailUiModelMapper()

    private val movieDetailResponse: MovieDetailResponse = parseJson("movie_detail.json")

    @Test
    fun map() {
        val uiModel = mapper.map(movieDetailResponse)

        expectThat(uiModel.id).isEqualTo(movieDetailResponse.id)
        expectThat(uiModel.title).isEqualTo(movieDetailResponse.title)
        expectThat(uiModel.originalTitle).isEqualTo(movieDetailResponse.originalTitle)
        expectThat(uiModel.overview).isEqualTo(movieDetailResponse.overview)
        expectThat(uiModel.backdropUrl).isEqualTo(movieDetailResponse.backdropPath.orEmpty().toBackdropUrl())
        expectThat(uiModel.posterUrl).isEqualTo(movieDetailResponse.posterPath.toPosterUrl())
        expectThat(uiModel.genres).isEqualTo(movieDetailResponse.genres)
        expectThat(uiModel.releaseDate).isEqualTo(movieDetailResponse.releaseDate)
        expectThat(uiModel.voteAverage).isEqualTo(movieDetailResponse.voteAverage)
        expectThat(uiModel.voteCount).isEqualTo(movieDetailResponse.voteCount)
        expectThat(uiModel.duration).isEqualTo(movieDetailResponse.runtime)
        expectThat(uiModel.homepage).isEqualTo(movieDetailResponse.homepage)
    }

    @Test
    fun `Map should remove last dots from tagline`() {
        val uiModel = mapper.map(movieDetailResponse.copy(tagline = "Tagline.."))

        expectThat(uiModel.tagline).isEqualTo("Tagline")
    }

    @Test
    fun `Map should set production companies with poster url`() {
        val productionCompanyResponse = ProductionCompanyResponse(1, "logoUrl", "Name", "Country")

        val uiModel = mapper.map(movieDetailResponse.copy(productionCompanies = listOf(productionCompanyResponse)))

        val expectedProductionCompany = ProductionCompany("Name", "logoUrl".toPosterUrl())
        expectThat(uiModel.productionCompanies.first()).isEqualTo(expectedProductionCompany)
    }
}
