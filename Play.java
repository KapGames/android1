package com.example.mathchampionsleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Play extends AppCompatActivity {
    Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        SharedPreferences sp = getSharedPreferences("sp" , MODE_PRIVATE);
        boolean isHebrew = sp.getBoolean("isHebrew", false);
        language = new Language(isHebrew);

        confBackButton();
        confDifficultyButtons();

    }

    private void confBackButton(){
        Button b = (Button) findViewById(R.id.button_back);
        b.setText(language.getBack());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Play.this , Menu.class));
            }
        });
    }
    private void confDifficultyButtons(){
        TextView text = findViewById(R.id.textView2);
        text.setText(language.getChooseDiff());
        MyAnimations.pop(text);

        Button easy = (Button) findViewById(R.id.button_easy);
        Button medium = (Button) findViewById(R.id.button_medium);
        Button hard = (Button) findViewById(R.id.button_hard);

        easy.setText(language.getEasy());
        medium.setText(language.getMedium());
        hard.setText(language.getHard());


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDifficulty("easy");
                moveToGameplay();
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDifficulty("medium");
                moveToGameplay();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDifficulty("hard");
                moveToGameplay();
            }
        });

    }

    private void saveDifficulty(String d){
            SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
            editor.putString("difficulty",d);
            editor.apply();
    }

    private void moveToGameplay(){
        startActivity(new Intent(Play.this , Gameplay.class));
    }
}