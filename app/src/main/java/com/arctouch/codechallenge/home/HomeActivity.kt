package com.arctouch.codechallenge.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.detail.DetailActivity
import com.arctouch.codechallenge.model.Movie
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity(), HomeView, HomeAdapter.OnItemListener, HomeEndlessScrollListener.DataLoader {

    private var page: Long = 1
    private val movies: MutableList<Movie> = ArrayList()
    private lateinit var presenter: HomePresenterImpl
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        presenter = HomePresenterImpl(this)
    }

    override fun onResume() {
        super.onResume()

        presenter.callGenres()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onItemClick(movie: com.arctouch.codechallenge.model.Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movieId", movie.id)
        startActivity(intent)
    }

    override fun onLoadMore(): Boolean {
        page = (com.arctouch.codechallenge.data.Cache.page + 1).toLong()
        val totalPages = Cache.totalPages
        return if (page.toInt() > totalPages) {
            false
        } else {
            presenter.callUpcoming(page)
            true
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun updateGenres() {
        hideProgress()
        if (Cache.movies.isEmpty()) {
            presenter.callUpcoming(page)
        } else {
            updateList(Cache.movies)
        }
    }

    override fun updateList(moviesWithGenres: List<Movie>) {
        if (movies.isNotEmpty()) {
            movies += moviesWithGenres
            recyclerView.adapter.notifyDataSetChanged()
        } else {
            movies += moviesWithGenres
            val layoutManager = GridLayoutManager(this, 2)
            recyclerView.layoutManager = layoutManager
            recyclerView.addOnScrollListener(HomeEndlessScrollListener(layoutManager, this))
            adapter = HomeAdapter(movies, this)
            recyclerView.adapter = adapter
        }
        Cache.cacheMovies(movies)
    }
}
