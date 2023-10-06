package com.prop.salinelevelmonitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginpage extends Activity {

     EditText usernameEditText, passwordEditText;
     Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(loginpage.this, "Please enter username and password.", Toast.LENGTH_SHORT).show();
                } else if (username.equals("admin") && password.equals("admin")) {
                    Intent intent = new Intent(loginpage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(loginpage.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}