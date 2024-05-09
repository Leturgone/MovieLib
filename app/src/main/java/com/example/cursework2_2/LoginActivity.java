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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button logButton;
    private Button onRegButton;
    private  User user;
    MyDatabaseHelper myDB;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
//        // Проверка вошел ли пользователь в систему
//        if(currentUser != null){
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new MyDatabaseHelper(LoginActivity.this);


        //Связь переменных с xml
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        logButton = findViewById(R.id.login_button);
        onRegButton = findViewById(R.id.on_reg_activity);
        progressBar = findViewById(R.id.logProgressBar);
        progressBar.setVisibility(View.GONE);
        Button loveButton = findViewById(R.id.love_button);
        //Реализация кнопок
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Получение данных с полей ввода

                String email, password, name;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                //Проверка на пустые поля

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Введите пароль",Toast.LENGTH_SHORT).show();
                }
                if (!email.matches("^[A-Za-z0-9]+$") | email.matches("\\s")){
                    Toast.makeText(LoginActivity.this,"Логин должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.matches("^[A-Za-z0-9]+$") | password.matches("\\s")){
                    Toast.makeText(LoginActivity.this,"Пароль должен содержать только буквы и цифры, без пробелов",Toast.LENGTH_LONG).show();
                    return;
                }
                user = new User(0,email, password,"viewer");

                if (myDB.chekUsername(user)){
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("username",user.getUser_login());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"Регистрация выполнена успешно",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Пользователь не найден",Toast.LENGTH_SHORT).show();
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
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}