package com.example.mathchampionsleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.security.keystore.UserNotAuthenticatedException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Language language;

    Button startButton ;
    EditText editText;
    boolean isHebrew;
    SharedPreferences sp;
    Button switchLanguage;

    TextView alert_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sp = getSharedPreferences("sp" , MODE_PRIVATE);
        isHebrew = sp.getBoolean("isHebrew" , false);


        Random r = new Random();
        startButton = (Button) findViewById(R.id.button_start);
        switchLanguage = (Button) findViewById(R.id.language);
        editText = (EditText) findViewById(R.id.username);

        confMainButton();
        confLanguage();
        confLanguageButton();



        alert_message = (TextView) findViewById(R.id.server_mes);
        AsyncClass asyncClass = new AsyncClass(alert_message , language);
        Thread t = new Thread(asyncClass);
        t.start();


        MyAnimations.pop(editText);


    }

    private void confMainButton(){
        //click listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get username
                String username = editText.getText().toString();

                //save username in shared preferences
                SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
                editor.putString("username",username);
                editor.apply();
                //move to menu
                startActivity(new Intent(MainActivity.this , Menu.class));

            }
        });

        //animation
        MyAnimations.shake(startButton);



    }

    void confLanguage(){
        language = new Language(isHebrew);

        editText.setHint(language.getUsernameHint());
        startButton.setText(language.getPlay());

        TextView title = (TextView) findViewById(R.id.Title);
        title.setText(language.getTitle());
    }

    void confLanguageButton(){
        switchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch lang
                SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
                editor.putBoolean("isHebrew", !isHebrew);
                editor.apply();
                //restart scene
                startActivity(new Intent(MainActivity.this , MainActivity.class));
            }
        });
    }


}