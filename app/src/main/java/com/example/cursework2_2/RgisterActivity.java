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

public class RgisterActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextName;
    private Button regButton;
    private Button onLogButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister);
        mAuth = FirebaseAuth.getInstance();

        //Связь переменных с xml
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        editTextName = findViewById(R.id.username_input);
        regButton = findViewById(R.id.reg_button);
        onLogButton = findViewById(R.id.on_log_activity);
        progressBar = findViewById(R.id.regProgressBar);

        //Реализация кнопок
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Получение данных с полей ввода

                String email, password, name;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                name = editTextName.getText().toString();

                //Проверка на пустые поля

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(RgisterActivity.this,"Введите имя",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RgisterActivity.this,"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RgisterActivity.this,"Введите пароль",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(RgisterActivity.this, "Учетная запись создана.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RgisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RgisterActivity.this, "Ошибка в создании учетной записи.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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