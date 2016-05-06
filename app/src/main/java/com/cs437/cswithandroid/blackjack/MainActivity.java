package com.cs437.cswithandroid.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int playerScore;
    int dealerScore;
    Button hit = null;
    Button hold = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start = (Button) findViewById(R.id.start);
        hit = (Button) findViewById(R.id.hit);
        hold = (Button) findViewById(R.id.hold);

        hit.setEnabled(false);
        hold.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hit.setEnabled(true);
                hold.setEnabled(true);
                start.setText("Restart");
                startGame();
            }
        });

    }
    public void startGame(){

        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerDraw();
                checkStatus();
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hit.setEnabled(false);
                while (dealerScore < 18){
                    dealerDraw();
                    checkStatus();
                }
                checkWinner();
            }
        });

        playerScore = 0;
        dealerScore = 0;

        updateScore("dealer", dealerScore);
        updateScore("player", playerScore);

    }

    private void checkWinner() {
        String winner;

        if (dealerScore > 21){
            winner = "Dealer Busts! Player Wins!";
        } else if (playerScore > dealerScore){
            winner = "Player Wins!";
        } else if (playerScore == dealerScore) {
            winner = "It's a Tie!";
        } else {
            winner = "Dealer Wins!";
        }

        Toast winnerMsg = Toast.makeText(this, winner, Toast.LENGTH_SHORT);
        winnerMsg.show();
    }

    private void checkStatus() {
        if (playerScore > 21){
            hit.setEnabled(false);
            hold.setEnabled(false);
            Toast winnerMsg = Toast.makeText(this, "Bust! Dealer Wins!", Toast.LENGTH_SHORT);
            winnerMsg.show();
        }
    }

    private void playerDraw() {
        //for debugging
        Random test = new Random();
        int random = test.nextInt(13) + 1;
        playerScore += random;
        updateScore("player", playerScore);
        ImageView card = (ImageView) findViewById(R.id.playerCard);
        card.setImageResource(R.drawable.card1);
    }

    private void dealerDraw() {
        Random test = new Random();
        int random = test.nextInt(13) + 1;
        dealerScore += random;
        updateScore("dealer", dealerScore);
        ImageView card = (ImageView) findViewById(R.id.dealerCard);
        card.setImageResource(R.drawable.card6);
    }

    private void updateScore(String turn, int i) {
        TextView score;

        if (turn.equals("player")){
            score = (TextView) findViewById(R.id.playerScore);
        } else {
            score = (TextView) findViewById(R.id.dealerScore);
        }

        score.setText("Score: " + i);
    }
}
