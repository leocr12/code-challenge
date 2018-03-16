package com.arctouch.codechallenge.home


interface HomePresenter {
    fun callGenres()

    fun callUpcoming(page: Long)

    fun onDestroy()
}