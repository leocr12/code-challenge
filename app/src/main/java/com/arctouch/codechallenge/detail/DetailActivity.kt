package com.arctouch.codechallenge.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity(), DetailView {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()
    private lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        presenter = DetailPresenterImpl(this)
    }

    override fun onResume() {
        super.onResume()

        val movieId = intent.extras.getInt("movieId").toLong()
        presenter.callMovie(movieId)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun updateMovie(movie: Movie) {
        hideProgress()
        Glide.with(this)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(posterImageView)

        Glide.with(this)
                .load(movie.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(backImage)

        toolbarTitle.text = movie.title
        originalTitle.text = movie.originalTitle
        genre.text = movie.genres?.joinToString(separator = ", ") { it.name }
        releaseDate.text = movie.releaseDate

        movie.budget.let {
            val res = resources
            if (it == 0 || it == null) {
                val text = String.format(res.getString(R.string.budget), "Unavailable")
                budget.text = text
            } else {
                val budgetValue = NumberFormat.getNumberInstance(Locale.US).format(it)
                val text = String.format(res.getString(R.string.budget), budgetValue.toString())
                budget.text = text
            }
        }

        movie.runtime.let {
            val res = resources
            if (it == 0 || it == null) {
                val text = String.format(res.getString(R.string.runtime), "Unavailable")
                runtime.text = text
            } else {
                val text = String.format(res.getString(R.string.runtime), it.toString())
                runtime.text = text
            }
        }
        overview.text = movie.overview
    }
}
