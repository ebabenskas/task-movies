package com.task.movies.api;

import com.task.movies.model.MoviesRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {
    @GET("popular")
    Call<MoviesRequest> getPopularMovies(@Query("api_key") String api, @Query("page") Integer page);

    @GET("top_rated")
    Call<MoviesRequest> getTopRatedMovies(@Query("api_key") String api, @Query("page") Integer page);
}
