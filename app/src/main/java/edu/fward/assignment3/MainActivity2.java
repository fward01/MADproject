package edu.fward.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener{

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;

    Button bRed, bBlue, bYellow, bGreen, fb;
    int sequenceCount = 4, n = 0;
    int[] gameSequence;
    int[] playerSequence = new int[120];
    int arrayIndex = 0;
    public int score=0;


    CountDownTimer ct = new CountDownTimer(15000,  1500) {

        public void onTick(long millisUntilFinished) {
            TextView mTextField=findViewById(R.id.mTextField);
            bRed.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = RED;

                }
            });
            bBlue.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = BLUE;

                }
            });
            bYellow.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = YELLOW;

                }
            });
            bGreen.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = GREEN;

                }
            });
            if (playerSequence.length==gameSequence.length){
                //onFinish();
            }
        }
        public void onFinish() {

            //mTextField.setText("done!");
            // we now have the game sequence

            //for (int i = 0; i< arrayIndex; i++)
             //   Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra
            score+=gameSequence.length;
            if (Arrays.equals(playerSequence, gameSequence)){
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("increase", 2);
                intent.putExtra("Score", score);
                startActivity(intent);
            }
            else {
                String scores = Integer.toString(score);
                Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                i.putExtra("message", scores);
                startActivity(i);
            }
            // start the next activity
            //
            //int[] arrayB = extras.getIntArray("numbers");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bRed = findViewById(R.id.redSubmit);
        bBlue = findViewById(R.id.blueSubmit);
        bYellow = findViewById(R.id.yellowSubmit);
        bGreen = findViewById(R.id.greenSubmit);
        Bundle myBundle = getIntent().getExtras();
        gameSequence= (int[]) myBundle.get("gameSequence");
        Log.d("game sequence", String.valueOf(gameSequence));
        score= (int) myBundle.get("Score");
        ct.start();
    }

    public void doPlay(View view) {
       // ct.start();
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

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}