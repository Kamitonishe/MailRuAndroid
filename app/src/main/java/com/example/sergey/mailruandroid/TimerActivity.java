package com.example.sergey.mailruandroid;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sergey on 10.10.17.
 */

public class TimerActivity extends AppCompatActivity implements OnClickListener {
    private CountDownTimer countDownTimer;
    private Button button;
    private TextView textView;
    private final long MAX_SECONDS = 1000;

    private long currentTime = 0;
    private String CURRENT_TIME;

    private boolean isClicked;
    private String IS_CLICKED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.timer_textView);
        isClicked = false;

        IS_CLICKED = this.getString(R.string.is_clicked);
        CURRENT_TIME = this.getString(R.string.current_time);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isClicked) {
            button.setText("Stop");
            countDownTimer = new CountDownTimer(MAX_SECONDS * 1000 - currentTime * 1000, 1_000) {

                public void onTick(long millisUntilFinished) {
                    currentTime = MAX_SECONDS - millisUntilFinished / 1000;
                    textView.setText(TimeToSring(currentTime));
                }

                public void onFinish() {
                    button.setText("Start");
                }
            }.start();
        }
    }

    public void onClick(View view) {
        if (!isClicked){
            button.setText("Stop");
            isClicked = true;

            countDownTimer = new CountDownTimer(MAX_SECONDS * 1000, 1_000) {

                public void onTick(long millisUntilFinished) {
                    currentTime = MAX_SECONDS - millisUntilFinished / 1000;
                    textView.setText(TimeToSring(currentTime));
                }

                public void onFinish() {
                    button.setText("Start");
                }
            }.start();


        } else {
            if (countDownTimer != null)
                countDownTimer.cancel();
            button.setText("Start");
            isClicked = false;
        }

    }

    private void action(boolean isClicked) {





    }

    private String TimeToSring(long time) {
        String[] stringSeconds = getResources().getStringArray(R.array.stringSeconds);
        String[] stringDecadeSeconds = getResources().getStringArray(R.array.stringDecadeSeconds);
        String[] stringHundredSeconds = getResources().getStringArray(R.array.stringHundredSeconds);

        StringBuilder answer = new StringBuilder();

        if (time >= 1000) {
            return getResources().getString(R.string.stringFinalSeconds);
        }

        long hundreds = time / 100;
        long decades = time - hundreds * 100;

        if (hundreds != 0) {
            answer.append(stringHundredSeconds[(int)hundreds - 1] + " ");
        }

        if (decades <= 19 & decades > 0) {
            answer.append(stringSeconds[(int)decades - 1]);
        } else if (decades % 10 == 0 & decades != 0){
            answer.append(stringDecadeSeconds[(int)decades / 10 - 2]);
        } else if (decades != 0) {
            answer.append(stringDecadeSeconds[(int)decades / 10 - 2] + " " + stringSeconds[(int)decades % 10 - 1]);
        }

        return new String(answer);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_CLICKED, isClicked);
        outState.putLong(CURRENT_TIME, currentTime);
        super.onSaveInstanceState(outState);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isClicked = savedInstanceState.getBoolean(IS_CLICKED);
        currentTime = savedInstanceState.getLong(CURRENT_TIME);
    }

}
