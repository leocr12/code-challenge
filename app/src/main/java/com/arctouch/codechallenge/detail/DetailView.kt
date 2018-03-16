package com.arctouch.codechallenge.detail

import com.arctouch.codechallenge.model.Movie


interface DetailView {

    fun showProgress()

    fun hideProgress()

    fun updateMovie(movie: Movie)
}