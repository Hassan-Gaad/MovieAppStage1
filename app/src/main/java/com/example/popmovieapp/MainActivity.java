package com.example.popmovieapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.popmovieapp.Utils.JSONUtlis;
import com.example.popmovieapp.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements movieAdapter.itemClickListener {

    private movieAdapter movieAdapter;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    String defaultQuery="popular";
    Movie[] movies;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int span_count = spanCount(getApplicationContext());
        recyclerView=findViewById(R.id.rv_recycleView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,span_count);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter=new movieAdapter(movies,MainActivity.this) ;
        recyclerView.setAdapter(movieAdapter);
        progressBar=findViewById(R.id.progressBar3);
        getMovieData();


    }

    @Override
    public void onItemClick(int clickedItem) {
        Context context = this;
        Class destinationClass = DetailActivity.class;

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, clickedItem);
        intentToStartDetailActivity.putExtra("title", movies[clickedItem].getTitle());
        intentToStartDetailActivity.putExtra("poster", movies[clickedItem].getPoster());
        intentToStartDetailActivity.putExtra("rate", movies[clickedItem].getRate());
        intentToStartDetailActivity.putExtra("release", movies[clickedItem].getRelease());
        intentToStartDetailActivity.putExtra("overview", movies[clickedItem].getOverview());

        startActivity(intentToStartDetailActivity);

    }

    private void getMovieData() {
        String querySearch = defaultQuery;
        showRecyclerView();
        new MovieQueryTask().execute(querySearch);
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "something went wrong :(", Toast.LENGTH_SHORT).show();
    }
    //calculate number of column fit to screen
    public static int spanCount(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int columns = (int) (dpWidth / 180);
        return columns;
    }


    public class MovieQueryTask extends AsyncTask<String,Void,Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
           if (strings.length==0){
               return null;
           }
           String sortType=strings[0];
           URL movieUrl= NetworkUtils.buildUrl(sortType);
            Log.d(TAG,"url Query "+movieUrl.toString());
           try{
              String urlResponseStr=NetworkUtils.getResponseFromHttpUrl(movieUrl);
               Log.d(TAG,"Url response "+urlResponseStr);
               movies= JSONUtlis.getMovieInfo(urlResponseStr);
                return movies;
           } catch (IOException | JSONException e) {
               e.printStackTrace();
               return null;
           }

        }



        @Override
        protected void onPostExecute(Movie[] movies) {
            progressBar.setVisibility(View.INVISIBLE);
            if (movies !=null){
               movieAdapter=new movieAdapter(movies,MainActivity.this) ;
                recyclerView.setAdapter(movieAdapter);

            }else{
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.action_popular) {
            defaultQuery = "popular";
            getMovieData();
            return true;
        }

        if (menuItemSelected == R.id.top_rated) {
            defaultQuery = "top_rated";
            getMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
