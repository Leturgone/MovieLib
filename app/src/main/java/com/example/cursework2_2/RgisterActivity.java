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

public class RgisterActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button regButton;
    private Button onLogButton;
    private User user;
    private MyDatabaseHelper myDB;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister);
        myDB = new MyDatabaseHelper(RgisterActivity.this);

        //Связь переменных с xml
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        regButton = findViewById(R.id.reg_button);
        onLogButton = findViewById(R.id.on_log_activity);
        progressBar = findViewById(R.id.regProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        //Реализация кнопок
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Получение данных с полей ввода

                String email, password;
                email = editTextEmail.getText().toString().toLowerCase();
                password = editTextPassword.getText().toString();

                //Проверка на пустые поля

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RgisterActivity.this,"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!email.matches("^[A-Za-z0-9]+$") | email.matches("\\s")){
                    Toast.makeText(RgisterActivity.this,"Логин должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RgisterActivity.this,"Введите пароль",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!password.matches("^[A-Za-z0-9]+$") | password.matches("\\s")){
                    Toast.makeText(RgisterActivity.this,"Пароль должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }

                user = new User(0,email, password,"viewer");
                if(myDB.chekUsername(user)){
                    Toast.makeText(RgisterActivity.this,"Имя занято",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if(myDB.addUser(user)){
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(RgisterActivity.this, "Учетная запись создана.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RgisterActivity.this,MainActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("username", user.getUser_login());
                        myEdit.apply();
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(RgisterActivity.this, "Ошибка в создании учетной записи.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        onLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RgisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}