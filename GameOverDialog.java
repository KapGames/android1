package com.example.mathchampionsleague;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

public class GameOverDialog extends AppCompatDialogFragment
{
    private GameOverDialogListener listener;
    TextView score;
    TextView username;
    TextView gameover_highscore;

    String u;
    String d;
    int s;
    int hs;
    Language language;
    boolean isHebrew;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_over , null);

        language = new Language(isHebrew);



        username = view.findViewById(R.id.gameover_username);
        score = view.findViewById(R.id.gameover_score);
        gameover_highscore = view.findViewById(R.id.gameover_highscore);

        if(getArguments() != null){
            u = getArguments().getString("username");
            s = getArguments().getInt("score");
            d = getArguments().getString("difficulty");
            isHebrew = getArguments().getBoolean("isHebrew");
            hs = getArguments().getInt("highscore");
        }

        score.setText( language.getScoreSummary(s , d));
        username.setText(language.getThanks(u));
        gameover_highscore.setText(language.getHighScore(hs));



        builder.setView(view).setTitle(language.getGameOver()).setNegativeButton(language.getExit(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
//                startActivity(new Intent(Gameplay.class, Menu.class));
                String result = "exit";
                listener.applyTexts(result);
            }
        }).setPositiveButton(language.getRestart(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
//                startActivity(new Intent(Gameplay.class, Menu.class));
                String result = "restart";
                listener.applyTexts(result);
            }
        });




        return builder.create();
    }

    public interface GameOverDialogListener{
        void applyTexts(String result);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (GameOverDialogListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

}
