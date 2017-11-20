package com.dfl.trivia.question;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.dfl.trivia.R;
import com.dfl.trivia.TriviaSharedPreferences;
import com.dfl.trivia.networking.RequestFactory;

public class QuestionActivity extends AppCompatActivity {

  public final static String CATEGORY_ID = "CATEGORY_ID";
  public final static String DIFFICULTY = "DIFFICULTY";
  public final static String GAME_TYPE = "GAME_TYPE";
  public final static String AMOUNT = "AMOUNT";

  private final static int DEFAULT_AMOUNT_OF_QUESTIONS = 10;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    QuestionActivityFragment mainActivityFragment =
        (QuestionActivityFragment) getFragmentManager().findFragmentById(R.id.content_question);

    if (mainActivityFragment == null) {
      mainActivityFragment = QuestionActivityFragment.newInstance();
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.add(R.id.content_question, mainActivityFragment);
      transaction.commit();
    }

    RequestFactory requestFactory = new RequestFactory();
    TriviaSharedPreferences triviaSharedPreferences = new TriviaSharedPreferences(
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
    int amount = getIntent().getIntExtra(AMOUNT, DEFAULT_AMOUNT_OF_QUESTIONS);
    String categoryId = getIntent().getStringExtra(CATEGORY_ID);
    String difficulty = getIntent().getStringExtra(DIFFICULTY);
    String questionType = getIntent().getStringExtra(GAME_TYPE);

    new QuestionPresenter(mainActivityFragment, requestFactory, triviaSharedPreferences, amount,
        categoryId, difficulty, questionType);
  }
}
