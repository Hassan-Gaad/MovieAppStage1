package com.example.popmovieapp.Utils;

import android.content.Context;

import com.example.popmovieapp.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtlis {
    public static Movie[] getMovieInfo(String JsonResult) throws JSONException {

        final String RESULTS="results";

        final String RELEASE_DATE = "release_date";
        final String VOTE = "vote_average";
        final String TITLE = "title";
        final String OVERVIEW = "overview";
        final String POSTER_PATH = "poster_path";
        final String BASE_URL = "https://image.tmdb.org/t/p/";
        final String POSTER_SIZE = "w342";



        JSONObject moviesJSONObj=new JSONObject(JsonResult);
        JSONArray movieJSONArr=moviesJSONObj.getJSONArray(RESULTS);

        Movie[] allMoviesArr = new Movie[movieJSONArr.length()];


        for (int i = 0; i < movieJSONArr.length(); i++){
            String title ,poster_path,overview,vote_average,release_date;

            Movie movie = new Movie();
            title = movieJSONArr.getJSONObject(i).getString(TITLE);
            poster_path = movieJSONArr.getJSONObject(i).getString(POSTER_PATH);
            overview = movieJSONArr.getJSONObject(i).getString(OVERVIEW);
            release_date = movieJSONArr.getJSONObject(i).getString(RELEASE_DATE);
            vote_average = movieJSONArr.getJSONObject(i).getString(VOTE);

            movie.setTitle(title);
            //Combining these three parts gives us a final url of
            //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
            movie.setPoster(BASE_URL + POSTER_SIZE + poster_path);
            movie.setOverview(overview);
            movie.setRelease(release_date);
            movie.setRate(vote_average);

            allMoviesArr[i] = movie;
        }

        return allMoviesArr;
    }
}
