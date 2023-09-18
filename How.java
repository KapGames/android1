package com.example.mathchampionsleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class How extends AppCompatActivity {
    Language language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how);

//        SharedPreferences sp = getSharedPreferences("sp" , MODE_PRIVATE);
//        boolean isHebrew = sp.getBoolean("isHebrew", false);

        Bundle extras = getIntent().getExtras();

        language = new Language(extras.getBoolean("isHebrew"));
        confBackButton();
        conLanguage();
    }

    void conLanguage() {
        TextView textv = (TextView) findViewById(R.id.textView);
        TextView textv4 = (TextView) findViewById(R.id.textView4);
        textv.setText(language.getHowToPlay());
        textv4.setText(language.getInstructions());
    }

    private void confBackButton(){
        Button back = (Button) findViewById(R.id.button_back);
        back.setText(language.getBack());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(How.this , Menu.class));
            }
        });
    }
}