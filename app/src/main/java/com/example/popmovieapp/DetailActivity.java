package com.example.popmovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView movieRating,movieTitle,movieRelease,moviePlotSynopsis;
    ImageView moviePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePlotSynopsis=findViewById(R.id.tv_plot_synopsis);
        moviePoster=findViewById(R.id.iv_detail_movie_poster);
        movieRating=findViewById(R.id.tv_detail_rate);
        movieRelease=findViewById(R.id.tv_detail_release_date);
        movieTitle=findViewById(R.id.tv_detail_title);

        String poster = getIntent().getStringExtra("poster");
        String overview = getIntent().getStringExtra("overview");
        String rate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String title = getIntent().getStringExtra("title");


        movieRating.setText(rate);
        moviePlotSynopsis.setText(overview);
        movieTitle.setText(title);
        movieRelease.setText(release);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.loading)
                .into(moviePoster);

    }
}
