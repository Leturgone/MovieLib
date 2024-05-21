package com.example.cursework2_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

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



        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Фильмотека домашняя");
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
                } else if (item.getItemId() == R.id.help_frag) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            new HelpFragment()).commit();

                } else if(item.getItemId() == R.id.logout_btn){
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("username", "");
                    myEdit.apply();
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        //Начальный фрагмент
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                new MoviesListFragment()).commit();

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
