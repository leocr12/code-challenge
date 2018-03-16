package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseService
import com.arctouch.codechallenge.data.Cache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomePresenterImpl(private var homeView: HomeView?): HomePresenter {

    override fun callGenres() {
        homeView!!.showProgress()
        BaseService.api.genres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cacheGenres(it.genres)
                    homeView!!.updateGenres()
                }
    }

    override fun callUpcoming(page: Long) {
        homeView!!.showProgress()
        BaseService.api.upcomingMovies(page, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cachePage(it.page)
                    Cache.cachePages(it.totalPages)
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    homeView!!.updateList(moviesWithGenres)
                    homeView!!.hideProgress()
                }
    }

    override fun onDestroy() {
        homeView = null
    }

}