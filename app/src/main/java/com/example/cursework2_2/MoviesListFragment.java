package com.example.cursework2_2;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MoviesListFragment extends Fragment {
    EditText editMovieTitle, editDirector, editYear, editDescription, editLength, editOldTitle, editOldYear;
    Button saveButton;
    FloatingActionButton bidAddButton;
    ImageView editImage;
    MyDatabaseHelper myDB;
    RecyclerView movieList;
    List<Movie> movies;
    private final  int GALLERY_REQUEST_CODE = 1000;

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

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = editMovieTitle.getText().toString();
                        String director = editDirector.getText().toString();
                        String year = editYear.getText().toString();
                        String description = editDescription.getText().toString();
                        String length = editLength.getText().toString();
                        try {
                            BitmapDrawable drawable = (BitmapDrawable) editImage.getDrawable();
                            Movie movie = new Movie(0,title,director,year,description,drawable.getBitmap(),length);
                            if (myDB.addMovie(movie)){
                                movies.add(movie);
                                adapter.notifyItemInserted(movies.size() - 1);
                                refreshMoviesList(myDB,movies,adapter,movieList);
                                Toast.makeText(getActivity(), "Фильм сохранен", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else{
                                Toast.makeText(getActivity(), "Ошибка при сохранении", Toast.LENGTH_SHORT).show();

                            }
                        }catch (ClassCastException e){
                            // Обработка исключения
                            e.printStackTrace();

                            // Показать уведомление пользователю
                            Toast.makeText(getActivity(), "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                editImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iGallery = new Intent(Intent.ACTION_PICK);
                        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
                    }
                });

                dialog.show();
            }
        });

        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.refactor_movie_dialog);
                Movie selected_movie  = movies.get(position);

                EditText MovieTitle = dialog.findViewById(R.id.REFeditMovieTitle);
                EditText Director = dialog.findViewById(R.id.REFeditDirector);
                EditText Year = dialog.findViewById(R.id.REFeditYear);
                EditText Description = dialog.findViewById(R.id.REFeditDescription);
                EditText Length = dialog.findViewById(R.id.REFeditLength);
                ImageView Poster = dialog.findViewById(R.id.REFimgGallery);

                Button updateButton = dialog.findViewById(R.id.update_button);
                Button deleteButton = dialog.findViewById(R.id.delete_button);

                //Установка полей
                MovieTitle.setHint(selected_movie.getMovie_title());
                Director.setHint(selected_movie.getMovie_director());
                Year.setHint(selected_movie.getMovie_year());
                Description.setHint(selected_movie.getMovie_description());
                Length.setHint(selected_movie.getMovie_length());
                Poster.setImageBitmap(selected_movie.getMovie_poster());
                Poster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iGallery = new Intent(Intent.ACTION_PICK);
                        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
                    }
                });

                dialog.show();
            }
        });




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode == GALLERY_REQUEST_CODE){
                //Для галлереи
                editImage.setImageURI(data.getData());
            }
        }
    }
    private void refreshMoviesList(MyDatabaseHelper dbHelper,
                                   List<Movie> movies, MovieAdapter adapter, RecyclerView
                                           movieList) {
        movies = dbHelper.getAllMovies(); // Загружаем обновленный список;
        adapter = new MovieAdapter(movies);
        movieList.setAdapter(adapter);
    }

}