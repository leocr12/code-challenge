package com.arctouch.codechallenge.detail

import com.arctouch.codechallenge.base.BaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DetailPresenterImpl(private var detailView: DetailView?): DetailPresenter {

    override fun callMovie(movieId: Long) {
        detailView!!.showProgress()
        BaseService.api.movie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    detailView!!.updateMovie(it)
                }
    }

    override fun onDestroy() {
        detailView = null
    }
}