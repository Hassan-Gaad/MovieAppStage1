package com.example.popmovieapp;

public class Movie {
     private String title,release,poster,overview,rate;

    public Movie(String title,String release,String poster,String overview,String rate){
        this.overview=overview;
        this.poster=poster;
        this.rate=rate;
        this.release=release;
        this.title=title;
    }

    public Movie(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
