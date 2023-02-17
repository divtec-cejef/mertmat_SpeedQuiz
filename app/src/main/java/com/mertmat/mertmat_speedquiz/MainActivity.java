package com.mertmat.mertmat_speedquiz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private Button bouton;
    private EditText editTextOne;
    private EditText editTextTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bouton = findViewById(R.id.bt_ok);
        bouton.setOnClickListener(this::onClick);

        editTextOne = findViewById(R.id.main_joueur1_et);
        editTextTwo = findViewById(R.id.main_joueur2_et);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void onClick(View view) {
        Intent resultActivity = new Intent(MainActivity.this, GameActivity.class);
        resultActivity.putExtra("namePlayerOne", editTextOne.getText().toString());
        resultActivity.putExtra("namePlayerTwo", editTextTwo.getText().toString());
        startActivity(resultActivity);
    }
}