package com.example.searchmaterialdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MaterialSearchView searchView ;

    Toolbar toolbar;

    LinearLayout linearLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    JSONArray moviesArray;
    ArrayList<Movies> moviesList;
    MoviesAdapter moviesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerSearch);
        searchView = findViewById(R.id.search_view);
        toolbar=findViewById(R.id.toolbar);
        linearLayout=findViewById(R.id.llSearch);
        setSupportActionBar(toolbar);
        moviesList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        moviesAdapter=new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic

                Log.e("Query CHanged  111 ",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Query CHanged 1111 ",newText);

                try {
                    moviesArray = new JSONArray(readJSONFromAsset());
                    moviesList = new ArrayList<>();
                    for (int i = 0; i < moviesArray.length()-1; i++) {
                        JSONObject data=moviesArray.getJSONObject(i);

                        if (data.getString("title").toLowerCase().contains(newText.toLowerCase())) {
                            Movies movies = new Movies();
                            movies.setActors(converJsonArrayToArrayList(data.getJSONArray("actors")));
                            movies.setGenres(converJsonArrayToArrayList(data.getJSONArray("genres")));
                            movies.setImdbRating(data.getInt("imdbRating"));
                            movies.setPosterurl(data.getString("posterurl"));
                            movies.setTitle(data.getString("title"));
                            movies.setReleaseDate(data.getString("releaseDate"));
                            moviesList.add(movies);
                        }
                    }
                    moviesAdapter.setData(moviesList);
                } catch (JSONException e) {
                    Log.e("Exception is coming  "," "+e.getLocalizedMessage());
                    e.printStackTrace();
                }
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                Animation bottomUp = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.view_in);
                Log.e("Query CHanged ","Opened ");
                linearLayout.startAnimation(bottomUp);
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    moviesArray = new JSONArray(readJSONFromAsset());
                    moviesList=new ArrayList<>();
                    for (int i = 0; i < moviesArray.length()-1; i++) {
                        JSONObject data=moviesArray.getJSONObject(i);
                        Movies movies=new Movies();
                        movies.setActors(converJsonArrayToArrayList(data.getJSONArray("actors")));
                        movies.setGenres(converJsonArrayToArrayList(data.getJSONArray("genres")));
                        movies.setImdbRating(data.getInt("imdbRating"));
                        movies.setPosterurl(data.getString("posterurl"));
                        movies.setTitle(data.getString("title"));
                        movies.setReleaseDate(data.getString("releaseDate"));
                        moviesList.add(movies);
                    }
                    moviesAdapter.setData(moviesList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSearchViewClosed() {

                Animation bottomDown = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.view_out);
                linearLayout.startAnimation(bottomDown);
                linearLayout.setVisibility(View.GONE);

                Log.e("Query CHanged ","Closed  ");
                //Do some magic
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }



    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("Movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {

            Log.e("Exception ",ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> converJsonArrayToArrayList(JSONArray jsonArray)
    {
        ArrayList<String> listdata = new ArrayList<String>();

        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    listdata.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return  listdata;
    }
}
