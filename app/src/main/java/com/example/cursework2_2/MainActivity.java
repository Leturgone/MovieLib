package com.example.cursework2_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button logOutButton;
    private TextView UserDataView;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

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
            actionBar.setTitle("Моя фильмотека");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


//        logOutButton = findViewById(R.id.logout_button);
//        UserDataView = findViewById(R.id.textView);
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
