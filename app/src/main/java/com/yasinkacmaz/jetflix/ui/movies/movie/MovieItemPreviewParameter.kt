package com.yasinkacmaz.jetflix.ui.movies.movie

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider

val fakeMovieUiModel = MovieUiModel(1337, "Movie Name", "01.03.1337", "Poster", 9.24, 1337)

class MovieProvider : PreviewParameterProvider<MovieUiModel> {
    override val values: Sequence<MovieUiModel>
        get() = sequenceOf(fakeMovieUiModel.copy(name = "Friends"), fakeMovieUiModel.copy(name = "Lost"))
}

@Composable
@Preview(group = "Series")
private fun Series(@PreviewParameter(MovieProvider::class) movieUiModel: MovieUiModel) {
    MovieItemPreview {
        MovieItem(movieUiModel)
    }
}

class MovieCollectionProvider : CollectionPreviewParameterProvider<MovieUiModel>(
    listOf(fakeMovieUiModel.copy(name = "Godfather"), fakeMovieUiModel.copy(name = "Harry Potter"))
)

@Composable
@Preview(group = "Films")
private fun Films(@PreviewParameter(MovieCollectionProvider::class) movieUiModel: MovieUiModel) {
    MovieItemPreview {
        MovieItem(movieUiModel)
    }
}
