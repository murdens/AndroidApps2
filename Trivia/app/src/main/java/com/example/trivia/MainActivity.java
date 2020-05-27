package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.example.trivia.util.SharedPrefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private TextView scoreTextView;
    private TextView highScore;
    private Button trueButton;
    private Button falseButton;
    private Button resetButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex =0;
    private List<Question> questionList;
    private Score score;
    private int currentScore = 0;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score();
        sharedPrefs = new SharedPrefs(MainActivity.this);

        nextButton = findViewById(R.id.next_view);
        prevButton = findViewById(R.id.prev_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        resetButton = findViewById(R.id.reset);
        questionCounterTextView = findViewById(R.id.counter_text);
        questionTextView = findViewById(R.id.question_textview);
        scoreTextView = findViewById(R.id.score_counter);
        highScore = findViewById(R.id.high_score);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        //get previous state
        currentQuestionIndex = sharedPrefs.getState();

        //setting scores
        scoreTextView.setText(MessageFormat.format("Current Score : {0}",String.valueOf(score.getScore())));

        sharedPrefs.saveHighScore(currentScore);
        highScore.setText(MessageFormat.format(" High Score: {0}", String.valueOf(sharedPrefs.getHighScore())));

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextView.setText(currentQuestionIndex + "/" + questionList.size());
            }
        });


        //new QuestionBank().getQuestions();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.prev_view:
                if(currentQuestionIndex>0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case  R.id.next_view:
                goToNextQ();
                break;
            case  R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
            case R.id.reset:
                currentQuestionIndex = 0;
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userChoiceCorrect){
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        if (userChoiceCorrect == answerIsTrue){
            fadeView();
            incrementPoints();
         } else {
            shakeAnimation();
            decrementPoints();
        }
   }

   private void goToNextQ(){
       currentQuestionIndex =  (currentQuestionIndex +1) % questionList.size();
       updateQuestion();
   }

    private void updateQuestion(){
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(MessageFormat.format("{0} / {1}",currentQuestionIndex, questionList.size()));
    }

    private void incrementPoints(){
        currentScore += 1;
        score.setScore(currentScore);
        scoreTextView.setText(MessageFormat.format("Current Score : {0}",String.valueOf(score.getScore())));
    }


    private void decrementPoints() {
        currentScore -= 1;
        if (currentScore > 0) {
            score.setScore(currentScore);
            scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        } else {
            currentScore = 0;
            score.setScore(currentScore);
            scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        }
    }


    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(400);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goToNextQ();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goToNextQ();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        sharedPrefs.saveHighScore(score.getScore());
        sharedPrefs.setState(currentQuestionIndex);
        super.onPause();
    }

}
