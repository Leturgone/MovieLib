package com.example.cursework2_2;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;


public class MoviesListFragment extends Fragment {
    EditText editMovieTitle, editDirector, editYear, editDescription, editLength, editOldTitle, editOldYear;
    Button saveButton, bidAddButton;
    ImageView editImage;
    MyDatabaseHelper myDB;
    RecyclerView movieList;
    List<Movie> movies;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    public static MoviesListFragment newInstance(String param1, String param2) {
        MoviesListFragment fragment = new MoviesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Загрузка списка из бд
        movieList = view.findViewById(R.id.recyclerView);
        myDB = new MyDatabaseHelper(getActivity());
        movies = myDB.getAllMovies();
        MovieAdapter adapter = new MovieAdapter(movies);
        movieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieList.setAdapter(adapter);

        bidAddButton = view.findViewById(R.id.add_button);
        bidAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.add_movie_dialog);

                editMovieTitle = dialog.findViewById(R.id.editMovieTitle);
                editDirector = dialog.findViewById(R.id.editDirector);
                editYear = dialog.findViewById(R.id.editYear);
                editDescription = dialog.findViewById(R.id.editDescription);
                editLength = dialog.findViewById(R.id.editLength);
                editImage = dialog.findViewById(R.id.imgGallery);
                saveButton = dialog.findViewById(R.id.save_button);
            }
        });




    }
    private void refreshMoviesList(MyDatabaseHelper dbHelper,
                                   List<Movie> movies, MovieAdapter adapter, RecyclerView
                                           movieList) {
        movies = dbHelper.getAllMovies(); // Загружаем обновленный список;
        adapter = new MovieAdapter(movies);
        movieList.setAdapter(adapter);
    }

}