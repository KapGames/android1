package com.example.mathchampionsleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MyRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        Bundle extras = getIntent().getExtras();

        boolean isHebrew = extras.getBoolean("isHebrew");

        Language language = new Language(isHebrew);

        Button back = (Button)findViewById(R.id.button_back);

        back.setText(language.getBack());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyRecyclerViewActivity.this , Menu.class));
            }
        });


        ArrayList<Item> items = new ArrayList<Item>();

        String[] arr = {
                "“Do not worry about your difficulties in mathematics. I can assure you mine are still greater.” – Albert Einstein",
                "“The study of mathematics, like the Nile, begins in minuteness but ends in magnificence.” – Charles Caleb Colton",
                "“Without mathematics, there’s nothing you can do. Everything around you is mathematics. Everything around you is numbers.” – Shakuntala Devi",
                "“In mathematics the art of proposing a question must be held of higher value than solving it.” – Georg Ferdinand Ludwig Philipp Cantor",
                "“The only way to learn mathematics is to do mathematics.” – Paul Halmos",
                "“Obvious is the most dangerous word in mathematics.” – Eric Temple Bell",
                "“Mathematics is the music of reason.” – James Joseph Sylvester  ",
                "“You don’t have to be a mathematician to have a feel for numbers.” – John Forbes Nash, Jr.",
                "“Life is a math equation. In order to gain the most, you have to know how to convert negatives into positives.” – Anonymous",
                "“Arithmetic is being able to count up to twenty without taking off your shoes.” – Mickey Mouse",
                "“It is clear that the chief end of mathematical study must be to make the students think.” – John Wesley Young",
                "“One of the endlessly alluring aspects of mathematics is that its thorniest paradoxes have a way of blooming into beautiful theories.” – Philip J Davis",
                " “Go down deep enough into anything and you will find mathematics.” – Dean Schlicter",
                "“Just because we can’t find a solution it doesn’t mean that there isn’t one.” – Andrew Wiles",
                "“Mathematics is the gate and key to science.” – Roger Bacon",
                "“Mathematics is not about numbers, equations, computations, or algorithms: it is about understanding.” – William Paul Thurston",
                "“There should be no such thing as boring mathematics.” – Edsger Dijkstra",
                "“If you stop at general math, then you will only make general money.” – Snoop Dogg"
        };

        for (String x : arr){
            items.add(new Item(x));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext() , items));
    }
}