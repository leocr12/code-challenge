package com.arctouch.codechallenge.detail


interface DetailPresenter {

    fun callMovie(movieId: Long)

    fun onDestroy()
}