package com.example.myles.mathtrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;

import static com.example.myles.mathtrainer.R.id.playAgainButton;
import static com.example.myles.mathtrainer.R.id.timerTextView;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<Integer>();
    GridLayout multiGridLayout;
    TextView scoreTextView;
    TextView questionTextView;
    TextView timerTextView;
    TextView gameOverTextView;
    int correctTagLocation;
    int score;
    int numberOfQuestions;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgainButton;



    public void playAgain(View view){

        score = 0;
        numberOfQuestions = 0;
        questionTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        gameOverTextView.setVisibility(View.INVISIBLE);




        generateQuestion();


        multiGridLayout.setVisibility(View.VISIBLE);

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {

                timerTextView.setText(String.valueOf(l / 1000) + "s" );

            }

            @Override
            public void onFinish() {


                //take answers away
                multiGridLayout.setVisibility(View.GONE);
                questionTextView.setVisibility(View.INVISIBLE);

                //show score and the end of the game
                gameOverTextView.setText("Your score: " + score + " / " + numberOfQuestions);
                gameOverTextView.setVisibility(View.VISIBLE);

                //show play again button

                playAgainButton.setVisibility(View.VISIBLE);






            }
        }.start();


    }

    public void generateQuestion(){

        Random random = new Random();
        int a = random.nextInt(101);
        int b = random.nextInt(101);
        int incorrectAnswer;

        //clear the array list so the correct answer is in there
        answers.clear();

        questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        correctTagLocation = random.nextInt(4);

        for(int i = 0; i <= 3; i++){

            if(correctTagLocation == i)
            {
                answers.add(a+b);

            }else{

                incorrectAnswer = random.nextInt(201);

                while (incorrectAnswer == a + b) {

                    incorrectAnswer = random.nextInt(201);
                }

                answers.add(incorrectAnswer);

            }
        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));


    }


    public void shortToast(int i){

        String message;
        if(i == 1)
        {
            message = "Correct!";
        }else{
            message = "Wrong!";
        }

        //work around to all shorter or longer Toasts. must import android.os.Handler

        final Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                toast.cancel();

            }
        }, 600);

    }


    public void chosenAnswer(View view){

        //NOTE: Toast.LENGTH_LONG = 3.5 sec
        //NOTE: Toast.LENGTH_SHORT = 2 sec



        if(view.getTag().toString().equals(Integer.toString(correctTagLocation))){

            shortToast(1);
            score++;

        }else{

            shortToast(0);
        }

        numberOfQuestions++;

        scoreTextView.setText(score + " / " + numberOfQuestions);


        //generate new quesion
        generateQuestion();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        gameOverTextView = (TextView) findViewById(R.id.gameOverTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        multiGridLayout = (GridLayout)findViewById(R.id.multiGridLayout);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        playAgain(findViewById(R.id.playAgainButton));



    }
}
