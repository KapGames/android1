package com.example.mathchampionsleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.Random;

public class Gameplay extends AppCompatActivity implements GameOverDialog.GameOverDialogListener {


    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    ValueAnimator answerPickAnimation, goalKeeperAnimation, timerAnimation;


    TextView question;
    TextView result;
    ImageView gk;
    TextView timebar;

    //data
    String username;
    String difficulty;
    int score;
    int highScore;
    int lastAnswer;
    String lastResult;
    Exercise exercise;
    boolean canShoot;
    Language language;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        sp = getSharedPreferences("sp" , MODE_PRIVATE);
        boolean isHebrew = sp.getBoolean("isHebrew", false);


        language = new Language(isHebrew);
        //buttons
        answer1 = (Button) findViewById(R.id.button_answer1);
        answer2 = (Button) findViewById(R.id.button_answer2);
        answer3 = (Button) findViewById(R.id.button_answer3);
        answer4 = (Button) findViewById(R.id.button_answer4);
        question = (TextView) findViewById(R.id.text_question);
        result = (TextView) findViewById(R.id.text_result);
        gk = (ImageView) findViewById(R.id.gk);
        timebar = (TextView) findViewById(R.id.timebar);

        Random r2 = new Random();

        confAnswerButtons();

        //data
        username = readUsername();
        difficulty = readDifficulty();
        score = 0;
        lastAnswer = 0;
        lastResult = "...";
        canShoot = true;

        //start game
        //timer!!
        exercise = new Exercise(difficulty);
        exercise.loadExerciseData();
        showExerciseInUI();
        updateTopUI();

        showLastResult();
        timebar = (TextView) findViewById(R.id.timebar);
        timebar.setText(language.getTime());
        startTimer();

    }

    public void showLastResult() {
        result.setText(lastResult);
    }

    public void showExerciseInUI() {
        answer1.setText(String.valueOf(exercise.z));
        answer2.setText(String.valueOf(exercise.wrong1));
        answer3.setText(String.valueOf(exercise.wrong2));
        answer4.setText(String.valueOf(exercise.wrong3));
        question.setText(String.valueOf(exercise.x) + " + " + String.valueOf(exercise.y) + " = ?");
        shuffleAnswers();
    }

    public void answerPicked() {
        showLastResult();
    }

    void reloadExercise() {
        //reload exercise and UI
        exercise.loadExerciseData();
        showExerciseInUI();
    }


    private String readDifficulty() {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        return sp.getString("difficulty", "easy");
    }

    private String readUsername() {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        return sp.getString("username", "DefaultName");
    }

    private int readHighScoreEasy() {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        return sp.getInt("highscore_e", 0);
    }
    private int readHighScoreMedium() {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        return sp.getInt("highscore_m", 0);
    }
    private int readHighScoreHard() {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        return sp.getInt("highscore_h", 0);
    }

    private void updateTopUI() {
        TextView UIbar = (TextView) findViewById(R.id.UIbar);
        UIbar.setText(language.getTopData(username,exercise.difficulty.toUpperCase() , String.valueOf(score) ));
    }

    private void confAnswerButtons() {
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canShoot == false) return;
                lastAnswer = Integer.parseInt(answer1.getText().toString());
                BallAnimation(answer1);
                moveKeeper(answer1, gk, exercise.CheckAnswer(lastAnswer));
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canShoot == false) return;
                lastAnswer = Integer.parseInt(answer2.getText().toString());
                BallAnimation(answer2);
                moveKeeper(answer2, gk, exercise.CheckAnswer(lastAnswer));
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canShoot == false) return;
                lastAnswer = Integer.parseInt(answer3.getText().toString());
                BallAnimation(answer3);
                moveKeeper(answer3, gk, exercise.CheckAnswer(lastAnswer));
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canShoot == false) return;
                lastAnswer = Integer.parseInt(answer4.getText().toString());
                BallAnimation(answer4);
                moveKeeper(answer4, gk, exercise.CheckAnswer(lastAnswer));
            }
        });
    }




    boolean checkedAnswer = false;

    void BallAnimation(Button answer) {
        answerPickAnimation = ValueAnimator.ofInt(1, 600, 600, 0);
        answerPickAnimation.setDuration(2000);



        //shoot button up to goal
        answerPickAnimation.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        canShoot = false;
                        int value = (int) animation.getAnimatedValue();
                        answer.setTranslationY(-value);

                        float x = 1f - (value/1200f);
                        answer.setScaleX(x);
                        answer.setScaleY(x);

                        //finished
                        if (value == 600) {
                            ballUp();
                        }
                        if (value == 0) {

                            answer.setScaleX(1f);
                            answer.setScaleY(1f);
                            ballBackDown();
                            canShoot = true;
                            reloadExercise();
                        }
                    }
                }
        );


        answerPickAnimation.start();
    }

    public class Exercise {
        public int x, y, z, wrong1, wrong2, wrong3;
        String difficulty;

        public Exercise(String difficulty) {
            this.difficulty = difficulty;
        }

        public void loadExerciseData() {
            Random rand = new Random();
            if (difficulty == "easy") {
                int max = 10;
                x = rand.nextInt(max) + 1;
                y = rand.nextInt(max) + 1;
                z = x + y;

                wrong1 = x + 2;
                wrong2 = y - 4;
                wrong3 = y + 6;
            } else if (difficulty == "medium") {
                int max = 50;
                x = rand.nextInt(max) + 1;
                y = rand.nextInt(max) + 1;
                z = x + y;

                wrong1 = x + 20;
                wrong2 = y - 40;
                wrong3 = y + 60;
            } else if (difficulty == "hard") {
                int max = 500;
                x = rand.nextInt(max) + 1;
                y = rand.nextInt(max) + 1;
                z = x + y;

                wrong1 = x + 20;
                wrong2 = y - 40;
                wrong3 = y + 60;
            }
        }

        public boolean CheckAnswer(int answer) {

            return answer == z;
        }
    }

    void ballUp() {
        if (checkedAnswer) return;
        //check answer
        if (exercise.CheckAnswer(lastAnswer)) {
            //FilesManager.addExercise(String.valueOf( exercise.x) , String.valueOf( exercise.y) , String.valueOf( lastAnswer) , "RIGHT ANSWER! :)" ,getApplicationContext());
            score++;
            lastResult = language.getGoal();
        } else {
            //FilesManager.addExercise(String.valueOf( exercise.x) , String.valueOf( exercise.y) , String.valueOf( lastAnswer) , "WRONG ANSWER! :())" ,getApplicationContext());
            lastResult = language.getMiss();
        }
        updateTopUI();
        showLastResult();
        checkedAnswer = true;
    }

    void ballBackDown() {
        //load new exercise
        clearResultUI();
        exercise.loadExerciseData();
        MyAnimations.pop(question);
        showExerciseInUI();
        checkedAnswer = false;
    }

    void clearResultUI() {
        result.setText("...");
    }


    void moveKeeper(Button answer, ImageView gk, boolean rightAnswer) {

        int mid = (int) gk.getX();
        int screenWidth = mid * 2;
        int answerX = (int) answer.getX();
        if (rightAnswer) {
            if (answer == answer4) {
                answerX -= mid;
            } else {
                answerX += mid / 2;
            }
        }

        goalKeeperAnimation = ValueAnimator.ofInt(mid, answerX, answerX, mid);
        goalKeeperAnimation.setDuration(2000);
        //shoot button up to goal
        goalKeeperAnimation.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        gk.setX(value);
                        //finished

                    }
                }
        );


        goalKeeperAnimation.start();
    }


    void shuffleAnswers() {
        Random r = new Random();
        int x = r.nextInt(4);
        if (x == 0) {
        }
        if (x == 1) {
            swapAnswers(answer1, answer2);
        }
        if (x == 2) {
            swapAnswers(answer1, answer3);
        }
        if (x == 3) {
            swapAnswers(answer1, answer4);
        }

    }

    void swapAnswers(Button a, Button b) {
        String aval = (String) a.getText();
        String bval = (String) b.getText();
        a.setText(bval);
        b.setText(aval);
    }

    void startTimer() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;

        ViewGroup.LayoutParams params = timebar.getLayoutParams();
        timerAnimation = ValueAnimator.ofInt(w - 32, 0);
        timerAnimation.setDuration(30 * 1000);

        timerAnimation.addUpdateListener(

                new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        params.width = value;
                        timebar.setLayoutParams(params);
                        if (value == 0) {
                            timerEnd();
                        }
                    }
                });

        timerAnimation.start();
    }

    void timerEnd() {
        updateHighScore();
        OpenDialog();
        canShoot = false;
    }

    void OpenDialog() {
        Bundle b = new Bundle();
        b.putInt("score" , score);
        b.putString("username" , username);
        b.putString("difficulty" , difficulty);
        b.putBoolean("isHebrew" , language.heb);
        b.putInt("highscore" , highScore);

        //fragment + bundle
        GameOverDialog gameOverDialog = new GameOverDialog();
        gameOverDialog.setArguments(b);
        gameOverDialog.show(getSupportFragmentManager(), "Game Over Dialog" );
    }

    @Override
    public void applyTexts(String result) {
        if(result == "restart") startActivity(new Intent(Gameplay.this , Gameplay.class));
        else if(result == "exit") startActivity(new Intent(Gameplay.this , Menu.class));
    }

    void updateHighScore(){
        int hs;

        if(difficulty == "hard"){
            hs = readHighScoreHard();
            if(hs < score){
                highScore = score;
                setHardHighScore(score);
            }else highScore = hs;
        }else if(difficulty == "medium"){
            hs = readHighScoreMedium();
            if(hs < score){
                highScore = score;
                setMediumHighScore(score);
            }else highScore = hs;
        }else if(difficulty == "easy"){
            hs = readHighScoreEasy();
            if(hs < score) {
                highScore = score;
             setEasyHighScore(score);
            }else highScore = hs;
        }

    }

    void setHardHighScore(int x ){

        SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
        editor.putInt("highscore_h", x);
        editor.apply();
    }

    void setMediumHighScore(int x){
        SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
        editor.putInt("highscore_m", x);
        editor.apply();
    }

    void setEasyHighScore(int x){
        SharedPreferences.Editor editor = getSharedPreferences("sp" , MODE_PRIVATE).edit();
        editor.putInt("highscore_e", x);
        editor.apply();
    }


}

