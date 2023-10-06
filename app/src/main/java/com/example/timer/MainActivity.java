package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.os.CountDownTimer;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME = 1000000;
    private TextView textView;
    private Button button_1;
    private Button button_2;
    private CountDownTimer count;
    private boolean timeRunning;
    private long timeLeft = START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener( v -> {
            if (timeRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
        button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener( v -> {
            resetTimer();
        });
        updateCountDownText();
    }
    private void startTimer() {
        count = new CountDownTimer(timeLeft,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timeRunning = false;
                button_1.setText("スタート");
                button_2.setVisibility(View.VISIBLE);
                timeLeft = START_TIME;
                updateCountDownText();
            }
        }.start();

        timeRunning = true;
        button_1.setText("一時停止");
        button_2.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        System.out.println("一時停止処理前:"+ timeRunning);
        count.cancel();
        timeRunning = false;
        System.out.println("一時停止処理後:"+ timeRunning);
        button_2.setVisibility(View.VISIBLE);
        button_1.setText("再開");
    }
    private void resetTimer() {
        timeLeft = START_TIME;
        updateCountDownText();
        button_1.setText("スタート");
        button_1.setVisibility(View.VISIBLE);
        button_2.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(){
        int minutes = (int)(timeLeft/1000)/60;
        int seconds = (int)(timeLeft/1000)%60;
        int millis = (int)(timeLeft%100);
        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d:%02d",minutes,seconds,millis);
        textView.setText(timeFormat);
    }
}