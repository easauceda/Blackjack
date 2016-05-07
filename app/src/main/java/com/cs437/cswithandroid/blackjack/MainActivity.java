package com.cs437.cswithandroid.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int playerScore;
    int dealerScore;
    int playerScoreAceLow;
    int playerScoreAceHigh;
    int dealerScoreAceLow;
    int dealerScoreAceHigh;

    Button hit = null;
    Button hold = null;
    Deck deck;

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

        deck = new Deck(this);
        deck.shuffle();

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
                while (dealerScore < 18){
                    dealerDraw();
                    checkStatus();
                }
                checkWinner();
            }
        });

        playerScore = 0;
        dealerScore = 0;
        playerScoreAceLow = 0;
        playerScoreAceHigh = 0;
        dealerScoreAceLow = 0;
        dealerScoreAceHigh = 0;

        updateScore("dealer", dealerScore);
        updateScore("player", playerScore);

        playerDraw();
        dealerDraw();
        if (dealerScore > 21 || playerScore > 21){
            checkWinner();
        }
    }

    private void animateDraw(String player) {
        int y;
        ImageView drawnCard;

        if (player.equals("player")){
            drawnCard = (ImageView) findViewById(R.id.playerCard);
            y = drawnCard.getHeight();
        } else {
            drawnCard = (ImageView) findViewById(R.id.dealerCard);
            y = drawnCard.getHeight() * -1;
        }

        Animation anim = new TranslateAnimation(0,0,y, 0);
        anim.setDuration(700);
        drawnCard.startAnimation(anim);
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
        animateDraw("player");
        Card card = deck.draw();

        if(card.isAce()) {
            playerScoreAceHigh += 11;
            playerScoreAceLow += card.getValue();
        }
        else{
            playerScoreAceHigh += card.getValue();
            playerScoreAceLow += card.getValue();
        }

        playerScore += card.getValue();
        ImageView cardView = (ImageView) findViewById(R.id.playerCard);
        cardView.setImageResource(card.getDrawableId());
        updateScore("player", playerScore);
        //for debugging
//        Random test = new Random();
//        int random = test.nextInt(13) + 1;
//        playerScore += random;
//        updateScore("player", playerScore);
//        ImageView card = (ImageView) findViewById(R.id.playerCard);
//        card.setImageResource(R.drawable.card1);


    }

    private void dealerDraw() {
        animateDraw("dealer");
        Card card = deck.draw();

        if(card.isAce()) {
            dealerScoreAceHigh += 11;
            dealerScoreAceLow += card.getValue();
        }
        else{
            dealerScoreAceHigh += card.getValue();
            dealerScoreAceLow += card.getValue();
        }

        dealerScore += card.getValue();
        ImageView cardView = (ImageView) findViewById(R.id.dealerCard);
        cardView.setImageResource(card.getDrawableId());
        updateScore("dealer", dealerScore);
//        Random test = new Random();
//        int random = test.nextInt(13) + 1;
//        dealerScore += random;
//        updateScore("dealer", dealerScore);
//        ImageView card = (ImageView) findViewById(R.id.dealerCard);
//        card.setImageResource(R.drawable.card6);
    }

    private void updateScore(String turn, int i) {
        TextView score;

        if (turn.equals("player")){
            score = (TextView) findViewById(R.id.playerScore);
        } else {
            score = (TextView) findViewById(R.id.dealerScore);
        }

        score.setText(turn + " score: " + i);

    }
}
