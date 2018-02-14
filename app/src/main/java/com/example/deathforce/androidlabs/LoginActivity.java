package com.example.deathforce.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    SharedPreferences sharedPreferences;
    Button loginButton;

    protected static final String ACTIVITY_NAME = "LogInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        loginButton = findViewById(R.id.login_button);
        sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREF), MODE_PRIVATE);

        loginButton.setOnClickListener((View e) ->{
            SharedPreferences.Editor edit = sharedPreferences.edit();
            EditText loginName = findViewById(R.id.editText);
            edit.putString(getString(R.string.DEFAULT_EMAIL),loginName.getText().toString());
            edit.apply();

            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        EditText loginName = findViewById(R.id.editText);
        loginName.setText(
                sharedPreferences.getString(getString(R.string.DEFAULT_EMAIL), "email@domain.com")
            );

        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
