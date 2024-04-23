package com.example.cursework2_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class MoviesListFragment extends Fragment {

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
        movieList = view.findViewById(R.id.recyclerView);
        myDB = new MyDatabaseHelper(getActivity());
        movies = myDB.getAllMovies();
        MovieAdapter adapter = new MovieAdapter(movies);
        movieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieList.setAdapter(adapter);
    }

}