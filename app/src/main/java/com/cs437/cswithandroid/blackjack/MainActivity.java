package com.cs437.cswithandroid.blackjack;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int playerScore, dealerScore;
    int playerTranslate = 0;
    int dealerTranslate = 0;
    Button hit = null;
    Button hold = null;
    Deck deck;
    FrameLayout playerFrame;
    FrameLayout dealerFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        playerTranslate = 0;
        dealerTranslate = 0;
        playerScore = 0;
        dealerScore = 0;
        playerFrame = (FrameLayout) findViewById(R.id.playerCards);
        dealerFrame = (FrameLayout) findViewById(R.id.dealerCards);
        deck = new Deck(this);

        deck.shuffle();

        if (playerFrame.getChildCount() > 2){
            playerFrame.removeViews(1, playerFrame.getChildCount() - 1);
            dealerFrame.removeViews(1, dealerFrame.getChildCount() - 1);
        }

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
                hold.setEnabled(false);
                dealersTurn();
            }
        });


        dealerDraw();
        playerDraw();
        playerDraw();
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

        Toast winnerMsg = Toast.makeText(this, winner, Toast.LENGTH_LONG);
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
        Card card = deck.draw();
        ImageView cardImage = new ImageView(this);

        cardImage.setImageResource(card.getDrawableId());
        cardImage.setTranslationX(playerTranslate);
        playerTranslate += 75;

        playerFrame.addView(cardImage);

        Animation anim = new TranslateAnimation(playerFrame.getWidth(),0,0, 0);
        anim.setDuration(700);
        cardImage.startAnimation(anim);

        playerScore += card.getValue();
        updateScore("Player", playerScore);
        checkStatus();


    }

    private void dealersTurn() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dealerScore < 18){
                    dealerDraw();
                    dealersTurn();
                } else {
                    checkWinner();
                }
            }
        }, 500);
    }

    private void dealerDraw(){
        ImageView cardImage = new ImageView(this);
        Card card = deck.draw();
        cardImage.setImageResource(card.getDrawableId());
        cardImage.setTranslationX(dealerTranslate);

        dealerTranslate += 75;

        dealerFrame.addView(cardImage);

        Animation anim = new TranslateAnimation(dealerFrame.getWidth(), 0,0,0);
        anim.setDuration(700);
        cardImage.startAnimation(anim);

        dealerScore += card.getValue();
        updateScore("Dealer", dealerScore);
        checkStatus();
    }

    private void updateScore(String turn, int i) {
        TextView score;

        if (turn.equals("Player")){
            score = (TextView) findViewById(R.id.playerScore);
        } else {
            score = (TextView) findViewById(R.id.dealerScore);
        }

        score.setText(turn + " score: " + i);

    }
}
