package edu.fward.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    Button bRed, bBlue, bYellow, bGreen, fb;
    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;
    int score;



    CountDownTimer ct = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {

            //textView2.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            //mTextField.setText("done!");
            // we now have the game sequence

            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra

            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
            i.putExtra("gameSequence", gameSequence);
            i.putExtra("Score",score);
            startActivity(i);

            // start the next activity
            //
            //int[] arrayB = extras.getIntArray("numbers");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bRed = findViewById(R.id.redButton);
        bBlue = findViewById(R.id.blueButton);
        bYellow = findViewById(R.id.yellowButton);
        bGreen = findViewById(R.id.greenButton);
        Intent intent=getIntent();
        sequenceCount+=intent.getIntExtra("increase",2);
        score=intent.getIntExtra("Score",0);
        ct.start();
        //Button playButton = findViewById(R.id.playButton);
       // playButton.setOnClickListener(new View.OnClickListener() {

        //    public void onClick(View v)
        //    {
       //         Intent intent = new Intent(MainActivity.this, MainActivity2.class);
       //         startActivity(intent);
       //     }
      //  });
    }

    public void doPlay(View view) {
        ct.start();
    }

    private void oneButton() {
        n = getRandom(sequenceCount);
        Log.d(" sequence count", String.valueOf(sequenceCount));
        Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

        switch (n) {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    //
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        maxValue=4;
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }

    public void doTest(View view) {
        for (int i = 0; i < sequenceCount; i++) {
            int x = getRandom(sequenceCount);

            Toast.makeText(this, "Number = " + x, Toast.LENGTH_SHORT).show();

            if (x == 1)
                flashButton(bBlue);
            else if (x == 2)
                flashButton(bRed);
            else if (x == 3)
                flashButton(bYellow);
            else if (x == 4)
                flashButton(bGreen);
        }

    }
}






