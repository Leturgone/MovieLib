package com.example.cursework2_2;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MoviesListFragment extends Fragment {
    EditText editMovieTitle, editDirector, editYear, editDescription, editLength, editGenre, editActors;
    Button saveButton;
    FloatingActionButton bidAddButton;
    ImageView editImage;
    MyDatabaseHelper myDB;
    MovieAdapter adapter;
    RecyclerView movieList;
    List<Movie> movies;
    androidx.appcompat.widget.SearchView searchView;
    private final  int GALLERY_REQUEST_CODE = 1000;

    public MoviesListFragment() {
    }

    public static MoviesListFragment newInstance() {
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
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //Загрузка списка из бд
        movieList = view.findViewById(R.id.recyclerView);
        myDB = new MyDatabaseHelper(getActivity());
        movies = myDB.getAllMovies();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPref", MODE_PRIVATE);
        //Получаем имя пользователя
        String username = sharedPreferences.getString("username", "");

        adapter = new MovieAdapter(movies);
        movieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieList.setAdapter(adapter);

        bidAddButton = view.findViewById(R.id.add_button);
        if(myDB.getRole(username).equals("viewer")){
            bidAddButton.setVisibility(View.GONE);
        }

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus(); //убирает курсор с компонента
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Movie> filterMovies = new ArrayList<>();
                for (Movie movie: movies){
                    if (movie.getMovie_title().toLowerCase().contains(query.toLowerCase())){
                        filterMovies.add(movie);
                    }
                }
                if (filterMovies.isEmpty()){
                    Toast.makeText(getActivity(), "Фильм не найден", Toast.LENGTH_SHORT).show();
                }
                else{
                    adapter.setFilteredMovies(filterMovies);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    refreshMoviesList(myDB);
                }
                return false;
            }
        });

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
                editGenre = dialog.findViewById(R.id.editGenre);
                editActors = dialog.findViewById(R.id.editActors);
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
                        String genre = editGenre.getText().toString();
                        String actors = editActors.getText().toString();
                        if (title.trim().isEmpty()| director.trim().isEmpty() | year.trim().isEmpty() | year.trim().isEmpty()|
                                description.trim().isEmpty()| length.trim().isEmpty()| genre.trim().isEmpty()| actors.trim().isEmpty()){
                            Toast.makeText(getActivity(), "Поля заполнены не полностью", Toast.LENGTH_SHORT).show();
                        }
                        else  if(!length.contains(" мин")){
                            Toast.makeText(getActivity(), "Хронометраж указывается в минутах", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                BitmapDrawable drawable = (BitmapDrawable) editImage.getDrawable();
                                Movie movie = new Movie(0, title, director, year, description, drawable.getBitmap(), length, genre, actors);
                                if (myDB.addMovie(movie)) {
                                    movies.add(movie);
                                    adapter.notifyItemInserted(movies.size() - 1);
                                    refreshMoviesList(myDB);
                                    Toast.makeText(getActivity(), "Фильм сохранен", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Такой фильм уже существует", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ClassCastException e) {
                                // Обработка исключения
                                e.printStackTrace();

                                // Показать уведомление пользователю
                                Toast.makeText(getActivity(), "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show();
                            }

                        }                    }
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
                if(!myDB.getRole(username).equals("viewer")) {
                    v.vibrate(200);
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.refactor_movie_dialog);
                    movies = adapter.getMovies();
                    Movie selected_movie = movies.get(position);

                    EditText MovieTitle = dialog.findViewById(R.id.REFeditMovieTitle);
                    EditText Director = dialog.findViewById(R.id.REFeditDirector);
                    EditText Year = dialog.findViewById(R.id.REFeditYear);
                    EditText Description = dialog.findViewById(R.id.REFeditDescription);
                    EditText Length = dialog.findViewById(R.id.REFeditLength);
                    ImageView Poster = dialog.findViewById(R.id.REFimgGallery);
                    EditText Genre = dialog.findViewById(R.id.REFeditGenre);
                    EditText Actors = dialog.findViewById(R.id.REFeditActors);

                    Button updateButton = dialog.findViewById(R.id.update_button);
                    Button deleteButton = dialog.findViewById(R.id.delete_button);

                    //Установка полей
                    MovieTitle.setHint(selected_movie.getMovie_title());
                    Director.setHint(selected_movie.getMovie_director());
                    Year.setHint(selected_movie.getMovie_year());
                    Description.setHint(selected_movie.getMovie_description());
                    Length.setHint(selected_movie.getMovie_length());
                    Poster.setImageBitmap(selected_movie.getMovie_poster());
                    Genre.setHint(selected_movie.getMovie_genre());
                    Actors.setHint(selected_movie.getMovie_actors());

                    Poster.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iGallery = new Intent(Intent.ACTION_PICK);
                            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
                        }
                    });

                    //Кнопка для обновления информации о фильме
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String old_title = selected_movie.getMovie_title();
                            String old_year = selected_movie.getMovie_year();

                            String new_title = MovieTitle.getText().toString();
                            String new_director = Director.getText().toString();
                            String new_year = Year.getText().toString();
                            String new_description = Description.getText().toString();
                            String new_length = Length.getText().toString();
                            String new_genre = Genre.getText().toString();
                            String new_actors = Actors.getText().toString();
                            if (new_title.trim().isEmpty()) {
                                new_title = old_title;
                            }
                            if (new_director.trim().isEmpty()) {
                                new_director = selected_movie.getMovie_director();
                            }
                            if (new_year.trim().isEmpty()) {
                                new_year = selected_movie.getMovie_year();
                            }
                            if (new_description.trim().isEmpty()) {
                                new_description = selected_movie.getMovie_description();
                            }
                            if (new_length.trim().isEmpty()) {
                                new_length = selected_movie.getMovie_length();
                            }
                            if (new_genre.trim().isEmpty()) {
                                new_genre = selected_movie.getMovie_genre();
                            }
                            if (new_actors.trim().isEmpty()) {
                                new_actors = selected_movie.getMovie_actors();
                            }

                            try {
                                BitmapDrawable drawable = (BitmapDrawable) Poster.getDrawable();
                                if (myDB.updateMovie(

                                        old_title, old_year, new_title, new_director, new_year,
                                        new_description, drawable.getBitmap(), new_length, new_genre,new_actors
                                )) {
                                    Toast.makeText(getActivity(), "Данные фильма обновлены", Toast.LENGTH_SHORT).show();
                                    refreshMoviesList(myDB);
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Ошибка при обновлении", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ClassCastException e) {
                                // Обработка исключения
                                e.printStackTrace();

                                // Показать уведомление пользователю
                                Toast.makeText(getActivity(), "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                    //Кнопка для удаления информации о фильме
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String title = selected_movie.getMovie_title();
                            String year = selected_movie.getMovie_year();
                            if (myDB.deleteMovie(title, year)) {
                                Toast.makeText(getActivity(), "Фильм удален", Toast.LENGTH_SHORT).show();
                                refreshMoviesList(myDB);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    dialog.show();
                }
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
    private void refreshMoviesList(MyDatabaseHelper dbHelper) {
        movies = dbHelper.getAllMovies(); // Загружаем обновленный список;
        adapter.setFilteredMovies(movies);
        adapter.notifyDataSetChanged();
        //adapter = new MovieAdapter(movies);
        //movieList.setAdapter(adapter);
    }

}