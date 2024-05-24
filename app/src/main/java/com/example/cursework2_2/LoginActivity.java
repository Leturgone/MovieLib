package com.example.cursework2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button logButton;
    private Button onRegButton;
    private  User user;
    private MyDatabaseHelper myDB;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        // Проверка вошел ли пользователь в систему
        if(!TextUtils.isEmpty(sharedPreferences.getString("username", ""))){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new MyDatabaseHelper(LoginActivity.this);
        myDB.create_db();

        //Связь переменных с xml
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        logButton = findViewById(R.id.login_button);
        onRegButton = findViewById(R.id.on_reg_activity);
        progressBar = findViewById(R.id.logProgressBar);
        progressBar.setVisibility(View.GONE);
        //Реализация кнопок
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Получение данных с полей ввода

                String email, password;
                email = editTextEmail.getText().toString().toLowerCase();
                password = editTextPassword.getText().toString();

                //Проверка на пустые поля

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!email.matches("^[A-Za-z0-9]+$") | email.matches("\\s")){
                    Toast.makeText(LoginActivity.this,"Логин должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Введите пароль",Toast.LENGTH_SHORT).show();
                }

                else if (!password.matches("^[A-Za-z0-9]+$") | password.matches("\\s")){
                    Toast.makeText(LoginActivity.this,"Пароль должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                user = new User(0,email, password,"viewer");

                try {
                    if (myDB.chekUser(user)){
                        progressBar.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("username", user.getUser_login());
                        myEdit.apply();
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this,"Вход выполнен успешно",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Неверный логин или пароль",Toast.LENGTH_SHORT).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    Toast.makeText(LoginActivity.this,"Неверный логин или пароль",Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }


            }
        });


        onRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RgisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}