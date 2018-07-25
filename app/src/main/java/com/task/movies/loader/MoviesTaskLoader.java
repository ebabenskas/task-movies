package com.task.movies.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.task.movies.model.MoviesRequest;
import com.task.movies.api.TheMovieDbApiService;

import java.io.IOException;

public class MoviesTaskLoader extends AsyncTaskLoader<MoviesRequest> {
    private MoviesRequest mMoviesRequest;
    private Bundle mArgs;
    public static final String PAGE_SIZE = "page";
    public static final String DB_REQUEST_TYPE = "type";

    public MoviesTaskLoader(Context context, Bundle args) {
        super(context);
        this.mArgs = args;

    }

    @Override
    protected void onStartLoading() {
        if (mArgs == null) {
            return;
        }
        if (mMoviesRequest != null) {
            deliverResult(mMoviesRequest);
        } else {
            forceLoad();
        }
    }

    @Override
    public MoviesRequest loadInBackground() {
        int page = mArgs.getInt(PAGE_SIZE);
        TheMovieDbApiService.MovieDbRequestType movieDbRequestType = TheMovieDbApiService.MovieDbRequestType.valueOf(mArgs.getString(DB_REQUEST_TYPE));
        try {
            mMoviesRequest = TheMovieDbApiService.getInstance().getMovies(movieDbRequestType, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mMoviesRequest;

    }
}
