package com.example.searchmaterialdesign;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    Context context;
    ArrayList<Movies> data;
    public MoviesAdapter(Context context) {
        this.context = context;
        data=new ArrayList<>();
    }

    public void  setData(ArrayList<Movies> data)
    {

        Log.e("Data restriveal "," **************  "+data.size());
        this.data=data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movis_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movies movies=data.get(position);
        Glide.with(context).load(movies.getPosterurl()).into(holder.poster);
        holder.textViewmoviesName.setText(movies.getTitle());
        holder.textViewreleaseYear.setText(movies.getReleaseDate());
        holder.textViewgenres.setText(movies.getGenres().toString());
        holder.textViewactor.setText(movies.getActors().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView textViewmoviesName,textViewreleaseYear,textViewgenres,textViewactor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.poster);
            textViewmoviesName=itemView.findViewById(R.id.moviesName);
            textViewreleaseYear=itemView.findViewById(R.id.releaseYear);
            textViewgenres=itemView.findViewById(R.id.genres);
            textViewactor=itemView.findViewById(R.id.actor);
        }
    }
}
