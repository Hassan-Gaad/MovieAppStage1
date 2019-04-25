package com.example.popmovieapp.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    // https://api.themoviedb.org/3/movie/popular?api_key={.....}&language=en-US&page=1
    final static String BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String PARAM_API_KEY = "api_key";
    final static String apiKey = "WRITE_YOUR_OWN_KEY";
    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";

    public static URL buildUrl(String query){
        Uri buildUri=Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(query)
                .appendQueryParameter(PARAM_API_KEY,apiKey)
                .appendQueryParameter(PARAM_LANGUAGE,language)
                .build();

        URL url=null;
        try {
            url=new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            //an SSLException happen when running app in API 17 (4.2.2) , i have searched to solve
            //this issue but couldn't solve it .can you help me about that ?

            //java.lang.System.setProperty("https.protocols", "TLSv1");
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }
}
