package com.task.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.task.movies.databinding.ActivityMovieDetailBinding;
import com.task.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity  {
    private static final String HTTP_IMAGE_TMDB_ORG_T_P_W185 = "http://image.tmdb.org/t/p/w185/";
    private static final String HTTP_IMAGE_TMDB_ORG_T_P_W500 = "http://image.tmdb.org/t/p/w500/";
    private static final String STATE_MOVIES = "movie";
    public static final String MOVIE_KEY = "movie";
    private Movie mMovie;
    private ActivityMovieDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        setSupportActionBar(mDetailBinding.tTitleBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mMovie = getMovie(savedInstanceState);
        mDetailBinding.tvDetailMovieYear.setText(mMovie.getReleaseDate());
        mDetailBinding.tvDetailMovieLenght.setText(String.format(getCurrentLocale(), getString(R.string.rating), mMovie.getVoteAverage()));
        mDetailBinding.tvDetailMovieVotes.setText(String.format(getCurrentLocale(), getString(R.string.votes), mMovie.getVoteCount()));
        actionBar.setTitle(mMovie.getTitle());
        mDetailBinding.tvDetailMovieInfo.setText(mMovie.getOverview());
        Picasso.with(this).load(HTTP_IMAGE_TMDB_ORG_T_P_W185 + mMovie.getPosterPath()).into(mDetailBinding.ivDetailMovieImage);
        Picasso.with(this).load(HTTP_IMAGE_TMDB_ORG_T_P_W500 + mMovie.getBackdropPath()).into(mDetailBinding.ivDetailMovieTopImage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    }



    private Movie getMovie(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(MovieDetailActivity.STATE_MOVIES)) {
            return savedInstanceState.getParcelable(MovieDetailActivity.STATE_MOVIES);
        }
        return getIntent().getParcelableExtra(MovieDetailActivity.MOVIE_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This is needed, because while click home buton, navigation recreates parent activity instead onResume().
            // When click hardware back button, this work correctly
            //
            Intent h = NavUtils.getParentActivityIntent(this);
            h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, h);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(STATE_MOVIES, mMovie);
        super.onSaveInstanceState(savedInstanceState);
    }


    public Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            return getResources().getConfiguration().locale;
        }
    }
}