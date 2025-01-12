package com.example.kosci;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registerButton);
        AccountSystem accountSystem = new AccountSystem(this);

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String repeatPassword = repeatPasswordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(repeatPassword)) {
                Toast.makeText(this, "Hasła nie są identyczne!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (accountSystem.checkAccount(email)) {
                Toast.makeText(this, "Konto z takim adresem już istnieje!", Toast.LENGTH_SHORT).show();
                return;
            }

            accountSystem.addAccount(email, password);
            Toast.makeText(this, "Konto zostało pomyślnie utworzone!", Toast.LENGTH_SHORT).show();

            Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
            gameIntent.putExtra("email", email);
            startActivity(gameIntent);

            finish();
        });
    }
}
