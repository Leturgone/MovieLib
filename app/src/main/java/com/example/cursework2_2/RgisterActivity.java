package com.example.cursework2_2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

                String email, password, name;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                //Проверка на пустые поля

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RgisterActivity.this,"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RgisterActivity.this,"Введите пароль",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.matches("^[A-Za-z0-9]+$") | email.matches("\\s")){
                    Toast.makeText(RgisterActivity.this,"Логин должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.matches("^[A-Za-z0-9]+$") | password.matches("\\s")){
                    Toast.makeText(RgisterActivity.this,"Пароль должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                user = new User(0,email, password,"viewer");
                if(myDB.chekUsername(user)){
                    Toast.makeText(RgisterActivity.this,"Имя занято",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if(myDB.addUser(user)){
                        Toast.makeText(RgisterActivity.this, "Учетная запись создана.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RgisterActivity.this,MainActivity.class);
                        intent.putExtra("username",user.getUser_login());
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