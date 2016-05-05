package com.cs437.cswithandroid.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView test = (ImageView) findViewById(R.id.dealerCard);
        test.setImageResource(R.drawable.card1);
        ImageView test2 = (ImageView) findViewById(R.id.playerCard);
        test2.setImageResource(R.drawable.card2);
    }
}
