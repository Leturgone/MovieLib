package com.example.cursework2_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button logOutButton;
    private TextView UserDataView;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if(TextUtils.isEmpty(username)){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        //Работа с тулбаром
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, R.string.drawer_open, R.string.drawer_close);
        if (drawer != null){
            drawer.addDrawerListener(toggle);
        }
        //синхронизирует текущее состояние
        // drawerLayout (открыт или закрыт) с позицией связанной
        // с ним кнопки на панели действий (ActionBar).

        //Получаем Базу данных
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getApplicationContext());
        databaseHelper.create_db();


        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Моя фильмотека");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Установка имени пользователся в выдвижное меню
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.login_view);
        menuItem.setTitle("Пользователь: " + username);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.my_movies){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            new MoviesListFragment()).commit();
                }
                else if(item.getItemId() == R.id.logout_btn){
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        //Начальный фрагмент
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                new MoviesListFragment()).commit();

//        UserDataView = findViewById(R.id.login_view);
//
//        user = auth.getCurrentUser();
//        if( user == null){
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else{
//            UserDataView.setText(user.getEmail());
//        }
//
//        logOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }
    // Обработка нажатия на иконку меню в ActionBar для открытия и закрытия Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
