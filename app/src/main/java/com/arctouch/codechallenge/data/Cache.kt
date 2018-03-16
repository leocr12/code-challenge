package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie

object Cache {

    var genres = listOf<Genre>()
    var movies = listOf<Movie>()
    var totalPages = 0
    var page = 0

    fun cacheGenres(genres: List<Genre>) {
        this.genres = genres
    }

    fun cacheMovies(movies: List<Movie>) {
        this.movies = movies
    }

    fun cachePages(totalPages: Int) {
        this.totalPages = totalPages
    }

    fun cachePage(page: Int) {
        this.page = page
    }
}
