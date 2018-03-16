package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.model.Movie


interface HomeView {
    fun showProgress()

    fun hideProgress()

    fun updateGenres()

    fun updateList(moviesWithGenres: List<Movie>)
}