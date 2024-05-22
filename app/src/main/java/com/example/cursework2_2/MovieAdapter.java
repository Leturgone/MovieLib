package com.example.cursework2_2;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView titleTextview;
        private final TextView directorTextview;

        private final TextView yearTextview;
        private final ImageView moviPosterImageview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextview = itemView.findViewById(R.id.movie_title_txt);
            directorTextview = itemView.findViewById(R.id.movie_director_txt);
            yearTextview = itemView.findViewById(R.id.movie_year_txt);
            moviPosterImageview = itemView.findViewById(R.id.movie_poster_img);

        }
        public  void blind(Movie movie){
            titleTextview.setText(movie.getMovie_title());
            directorTextview.setText(movie.getMovie_director());
            yearTextview.setText(movie.getMovie_year());
            moviPosterImageview.setImageBitmap(movie.getMovie_poster());
        }
    }
    public List<Movie> getMovies(){
        return this.movies;
    }

    public void setFilteredMovies(List<Movie> filteredMovies){
        this.movies = filteredMovies;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.blind(movies.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.movie_inf_dialog);

                Movie selected_movie = movies.get(position);

                TextView MovieTitle = dialog.findViewById(R.id.infTitleTextView);
                TextView Director = dialog.findViewById(R.id.infDirectorTextView);
                TextView Year = dialog.findViewById(R.id.infDateTextView);
                TextView Description = dialog.findViewById(R.id.infDescriptTextView);
                TextView Length = dialog.findViewById(R.id.infLengthtextView);
                ImageView Poster = dialog.findViewById(R.id.infPosterImageView);
                TextView Genre = dialog.findViewById(R.id.infGenretextView);
                TextView Actors = dialog.findViewById(R.id.infActorstextView);

                MovieTitle.setText(selected_movie.getMovie_title());
                Director.setText(selected_movie.getMovie_director());
                Year.setText(selected_movie.getMovie_year());
                Description.setText(selected_movie.getMovie_description());
                Length.setText(selected_movie.getMovie_length());
                Poster.setImageBitmap(selected_movie.getMovie_poster());
                Genre.setText(selected_movie.getMovie_genre());
                Actors.setText(selected_movie.getMovie_actors());

                dialog.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
