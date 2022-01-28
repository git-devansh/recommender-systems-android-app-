package com.urbanbunnyapps.movierecommendersystem.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.urbanbunnyapps.movierecommendersystem.R
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBClient
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBInterface
import com.urbanbunnyapps.movierecommendersystem.data.api.POSTER_BASE_URL
import com.urbanbunnyapps.movierecommendersystem.data.repository.NetworkState
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    lateinit var movieImageView: ImageView
    lateinit var movieNameTextView: TextView
    lateinit var movieYearDurationTextView: TextView
    lateinit var ratingTextView: TextView
    lateinit var overViewText: TextView
    lateinit var progressBarLayout: LinearLayout
    lateinit var connectionErrorTextView: TextView
    lateinit var favButton: ImageView
    var isFavTrue: Boolean = false

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Init views
        backButton = findViewById(R.id.backButton)
        movieImageView = findViewById(R.id.movieImageView)
        movieNameTextView = findViewById(R.id.movieNameTextView)
        movieYearDurationTextView = findViewById(R.id.movieYearDurationTextView)
        ratingTextView = findViewById(R.id.ratingTextView)
        overViewText = findViewById(R.id.overViewText)
        progressBarLayout = findViewById(R.id.progressBarLayout)
        connectionErrorTextView = findViewById(R.id.connectionErrorTextView)
        favButton = findViewById(R.id.favButton)

        favButton.setOnClickListener {
            isFavTrue = if (isFavTrue){
                favButton.setImageResource(R.drawable.ic_favorite_border)
                false
            } else{
                favButton.setImageResource(R.drawable.ic_favorite)
                true
            }
        }

        // onclick back button
        backButton.setOnClickListener {
            finish()
        }

        // data
        val movieId: Int  = intent.getIntExtra("movie_id",13430)
        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        Log.i("MovieDetails", viewModel.movieDetails.toString())

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
            Log.i("MovieDetails", it.toString())
        })

        viewModel.networkState.observe(this, Observer {
            progressBarLayout.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if (it == NetworkState.ERROR){
                connectionErrorTextView.visibility = View.VISIBLE
                progressBarLayout.visibility = View.VISIBLE
            } else{
                connectionErrorTextView.visibility = View.GONE
                progressBarLayout.visibility = View.GONE
            }
        })

    }

    private fun bindUI(movieDetails: MovieDetails) {
        movieNameTextView.text = movieDetails.title
        overViewText.text = movieDetails.overview
        ratingTextView.text = movieDetails.rating.toString()
        movieYearDurationTextView.text = "${movieDetails.releaseDate} • ${movieDetails.status}"

        val moviePosterURL: String = POSTER_BASE_URL + movieDetails.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(movieImageView)
    }

    private fun getViewModel(movieId:Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieDetailsRepository,movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

}