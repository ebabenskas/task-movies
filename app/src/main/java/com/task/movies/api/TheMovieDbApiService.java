package com.task.movies.api;
import android.text.TextUtils;

import com.task.movies.BuildConfig;
import com.task.movies.model.MoviesRequest;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbApiService {

    private static final String HTTPS_API_THEMOVIEDB_ORG_3_MOVIE = "https://api.themoviedb.org/3/movie/";
    private String mMovieDbApiKey;
    private static TheMovieDbApiService mApi = new TheMovieDbApiService();
    private TheMovieDbApi mService;

    public static TheMovieDbApiService getInstance() {
        return mApi;
    }

    private TheMovieDbApiService() {
        mMovieDbApiKey = BuildConfig.MOVIE_DB_API_KEY;
        if (TextUtils.isEmpty(mMovieDbApiKey) || mMovieDbApiKey.equals("missingKeyFile"))
            throw new RuntimeException("Missing api.themoviedb.org API key. Create app/config.properties file if he doesn't exist and there insert API key with movieDbKey properties name!");
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HTTPS_API_THEMOVIEDB_ORG_3_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(TheMovieDbApi.class);
    }

    public MoviesRequest getMovies(MovieDbRequestType requestType, int page) throws IOException {
        Call<MoviesRequest> request;
        switch (requestType){
            case TOP_RATED:
                request = mService.getTopRatedMovies(mMovieDbApiKey, page);
                break;
            case POPULAR:
                request = mService.getPopularMovies(mMovieDbApiKey, page);
                break;
            default:
                request = mService.getPopularMovies(mMovieDbApiKey, page);
        }
        Response<MoviesRequest> response= request.execute();
        return response.body();
    }

    public enum MovieDbRequestType {
        TOP_RATED,
        POPULAR;
    }
}
