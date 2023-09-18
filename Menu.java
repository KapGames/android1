package com.example.mathchampionsleague;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.AttachedSurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    String username;
    Button howbutton;
    Button playButton;
    Button smsShare;
    Button smsdevs;
    Button emailShare;
    Button quotesButton;
    Button devShare;


    Language language;

    boolean isHebrew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sp = getSharedPreferences("sp" , MODE_PRIVATE);
        username = sp.getString("username" , null);
        isHebrew = sp.getBoolean("isHebrew" , false);

        smsShare = (Button) findViewById(R.id.smsButton);
        emailShare = (Button) findViewById(R.id.emailButton);
        playButton = (Button) findViewById(R.id.button_play);
        quotesButton = (Button) findViewById(R.id.quotes);
        devShare =  (Button) findViewById(R.id.email_devs);
        smsdevs =  (Button) findViewById(R.id.smsdevs);

        confPlayButton();
        confHowButton();
        confLanguage();
        confDialog();
        confShareButtons();
        confQuotes();

        MyAnimations.pop(playButton);
    }

    private void confShareButtons() {
        emailShare.setText(language.getShareByEmail());
        emailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("Game Recommendation ","MATH-CHAMPIONS LEAGUE IS SO MUCH FUN!!!" );
            }
        });

        smsShare.setText(language.getShareBySMS());
        smsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeMmsMessage("MATH-CHAMPIONS-LEAGUE IS SO MUCH FUN!!!");
            }
        });

        smsdevs.setText(language.getShareBySMStoDev());
        smsdevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeMmsMessageToDeveloper();
            }
        });

        devShare.setText(language.getEmailtoDev());
        devShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmailtoDev("I like your game MATH-CHAMPIONS-LEAGUE!", "MATH-CHAMPIONS-LEAGUE IS SO MUCH FUN!!! Thanks gor making this game :)");
            }
        });
    }


    private void confPlayButton(){
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this , Play.class));
            }
        });
    }

    private void confQuotes(){
        quotesButton.setText(language.getQuotes());
        quotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Menu.this , MyRecyclerViewActivity.class);
                i.putExtra("isHebrew" , language.heb);
                startActivity(i);
            }
        });
    }
    private void confHowButton(){
        howbutton = (Button) findViewById(R.id.button_how_to_play);
        howbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Menu.this , How.class);
                i.putExtra("isHebrew" , language.heb);
                startActivity(i);

                //startActivity(new Intent(Menu.this , How.class));
            }
        });
    }

    void confDialog() {
        TextView dialog = (TextView) findViewById(R.id.dialog);
        username = readUsername();
        Log.d("success" , "MINE : " + "LOADED! : " + username);
        if(username.isEmpty()){
            dialog.setText(language.getHelloNoName());
        }else{
            dialog.setText(language.getHelloName(username));
        }

    }

    private String readUsername(){
        SharedPreferences sp = getSharedPreferences("sp" , MODE_PRIVATE);
        return sp.getString("username" , "");
    }

    void confLanguage(){
        language = new Language(isHebrew);

        TextView def = (TextView) findViewById(R.id.dialog2);
        def.setText(language.getDefinition());

        howbutton.setText(language.getHowToPlay());

        playButton.setText(language.getPlay());

        TextView title2 = (TextView) findViewById(R.id.title2);
        title2.setText(language.getTitle());
    }

    public void composeMmsMessage(String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:"));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    public void composeMmsMessageToDeveloper() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:+972543520330"));
        startActivity(intent);
    }
    public void composeEmail(String subject , String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent , "choose email client..."));
    }

    public void composeEmailtoDev(String subject , String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_EMAIL , "eliy5550@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent , "choose email client..."));
    }

    void pop(View v){
        ValueAnimator popanimation;
        //animation
        int intensity = 10;
        popanimation = ValueAnimator.ofInt(100 , 120,100,120,100);
        popanimation.setDuration(2000);
        popanimation.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        v.setScaleY(value / 100f);
                        v.setScaleX(value / 100f);

                    }
                }
        );
        popanimation.start();

    }


}